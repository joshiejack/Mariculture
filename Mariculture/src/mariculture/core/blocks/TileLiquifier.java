package mariculture.core.blocks;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.Core;
import mariculture.core.gui.ContainerLiquifier;
import mariculture.core.handlers.LiquifierHandler;
import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.FluidInventoryHelper;
import mariculture.core.helpers.HeatHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.UtilMeta;
import mariculture.core.util.PacketIntegerUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileLiquifier extends TileDoubleHeightTank implements ISidedInventory {

	private static class FuelHandler {
		private ItemStack fuel;
		private int burnTime;
		private int used;
		private int maxPer;
		private int maxTemp;
		
		public int update(int temp, int max, int lava, boolean redstone) {
			if(fuel != null) {
				if(burnTime < maxPer) {					
					if(temp < maxTemp) {
						if(used < maxPer) {
							if(temp + lava < maxTemp) {
								used+= lava;
								temp+= lava;
							} else {
								used+= (maxTemp - temp);
								temp = maxTemp;
							}
						}
					}
					
					burnTime = (redstone)? burnTime + lava: burnTime + 1;
					
					return temp;
				}
				
				fuel = null;
				maxPer = 0;
				maxTemp = 0;
			}
		
			return temp;
		}
		
		public void read(NBTTagCompound nbt) {
			int id = nbt.getInteger("FuelID");
			int meta = nbt.getInteger("FuelMeta");
			fuel = new ItemStack(id, 1, meta);
			burnTime = nbt.getInteger("FuelBurnTime");
			used = nbt.getInteger("FuelUsed");
			maxPer = nbt.getInteger("FuelPer");
			maxTemp = nbt.getInteger("FuelMax");
		}
		
		public void write(NBTTagCompound nbt) {
			int id = 0;
			int meta = 0;
			if(fuel != null) {
				id = fuel.itemID;
				meta = fuel.getItemDamage();
			}
			
			nbt.setInteger("FuelID", id);
			nbt.setInteger("FuelMeta", meta);
			nbt.setInteger("FuelBurnTime", burnTime);
			nbt.setInteger("FuelUsed", used);
			nbt.setInteger("FuelPer", maxPer);
			nbt.setInteger("FuelMax", maxTemp);
		}

		public boolean setFuel(ItemStack stack) {
			if(MaricultureHandlers.smelter.getBurnTemp(stack, false) > 0) {
				ItemStack copy = stack.copy();
				copy.stackSize = 1;
				this.fuel = copy;
				this.burnTime = 0;
				this.used = 0;
				this.maxPer = convertFromReal(MaricultureHandlers.smelter.getBurnTemp(stack, false));
				this.maxTemp = convertFromReal(MaricultureHandlers.smelter.getBurnTemp(stack, true));				
				return true;
			}
			
			return false;
		}
	}
	
	public static final int MAX_TEMP = 25000;
	private int TIME_TAKEN = MachineSpeeds.getLiquifierSpeed();
	private int furnaceCookTime = 0;
	private int temperature = 0;
	private int bonus = 0;
	private FuelHandler fuelHandler;

	private int tick = 0;

	Random rand = new Random();

	public TileLiquifier() {
		fuelHandler = new FuelHandler();
		super.setInventorySize(9);
	}
	
	@Override
	public boolean isThis(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == Core.utilBlocks.blockID
				&& worldObj.getBlockMetadata(x, y, z) == UtilMeta.LIQUIFIER;
	}
	
	private static int convertFromReal(int real) {
		return (real * MAX_TEMP)/ 2000;
	}

	@Override
	public void updateEntity() {
		tick++;

		if (isBuilt() && tick % 10 == 0) {
			if (!this.worldObj.isRemote) {
				moveLiquidNextDoor();
			}
		}

		if (isBuilt() && master() == this) {
			super.updateEntity();
			if (!this.worldObj.isRemote) {
				if (tick % 20 == 0) {

					if (Extra.DEBUG_ON) {
						this.temperature = MAX_TEMP;
					}

					processContainers();
				}

			}
			
			if(tick %2 == 0) {
				boolean updated = false;

				if(!this.worldObj.isRemote) {
					if(fuelHandler == null) {
						fuelHandler = new FuelHandler();
					}
					
					if(inventory[2] != null) {
						if(fuelHandler.fuel == null) {
							if(fuelHandler.setFuel(inventory[2])) {
								inventory[2].stackSize--;
								if (this.inventory[2].stackSize == 0) {
									this.inventory[2] = this.inventory[2].getItem().getContainerItemStack(inventory[2]);
								}
								
								updated = true;
							}
						}
					}
					
					if(tick %4 == 0) {
						boolean powered = (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) ||
								this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord + 1, this.zCoord));
						int lava = (int) (1 + HeatHelper.getWorldHeat(worldObj, this.xCoord, this.yCoord, this.zCoord)/1.8);
						this.temperature = fuelHandler.update(this.temperature, MAX_TEMP, lava, powered);
					}
					
					if(melt()) {
						updated = true;
					}
					
					if (!this.isBurning()) {
						this.temperature = this.temperature - getBiomeDropRate();
					}

					if (this.temperature < 0) {
						this.temperature = 0;
					}
				}
				
				if(updated) {
					this.onInventoryChanged();
				}
			}
		}
	}

	private boolean melt() {
		if (this.canMelt(0) || this.canMelt(1)) {
			this.furnaceCookTime = this.furnaceCookTime + getHeatSpeed() + ((this.temperature * 20) / MAX_TEMP);

			if (this.furnaceCookTime >= TIME_TAKEN) {
				this.furnaceCookTime = 0;
				this.meltItem(0);
				this.meltItem(1);
				return true;
			}
		} else {
			this.furnaceCookTime = 0;
		}
		
		return false;
	}

	private int getHeatSpeed() {
		int heat = HeatHelper.getTileTemperature(worldObj, xCoord, yCoord, zCoord, getUpgrades());
				
		return (heat > 0) ? heat : 1;
	}

	private int getBiomeDropRate() {
		EnumBiomeType biomeType = MaricultureHandlers.biomeType.getBiomeType(worldObj.getBiomeGenForCoords(xCoord, zCoord));
		int heat = HeatHelper.getTileTemperature(worldObj, xCoord, yCoord, zCoord, getUpgrades());

		if (biomeType == EnumBiomeType.HELL) {
			if (MaricultureHandlers.upgrades.hasUpgrade("ethereal", this) && MaricultureHandlers.upgrades.getData("temp", this) >= 28) {
				return 0;
			}
		}

		int baseDrop = biomeType.getCoolingSpeed();

		if (heat < 0) {
			baseDrop *= ((-heat) / 3);
		}

		int dropChance = ((200 - heat) >= 1) ? 200 - heat : 1;

		if (heat <= 0 || tick % heat == 0) {
			return baseDrop;
		}

		return 0;
	}

	private void processContainers() {
		ItemStack result = FluidHelper.getFluidResult(this, inventory[4], inventory[5]);
		if (result != null) {
			decrStackSize(4, 1);
			if (this.inventory[5] == null) {
				this.inventory[5] = result.copy();
			} else if (this.inventory[5].itemID == result.itemID) {
				++this.inventory[5].stackSize;
			}
		}
	}

	// NEW CAN MELT
	private boolean canMelt(int slot) {
		if (inventory[slot] == null) {
			return false;
		}

		SmelterOutput result = MaricultureHandlers.smelter.getResult(inventory[slot], getTemperatureScaled(2000));
		if (result == null) {
			return false;
		}

		if (result.fluid.fluidID != tank.getFluidID() && tank.getFluidID() > 0) {
			return false;
		}

		if (tank.getFluidAmount() + result.fluid.amount > tank.getCapacity()) {
			return false;
		}

		if (inventory[3] == null) {
			return true;
		}

		if (result.output != null) {
			if (!inventory[3].isItemEqual(result.output)) {
				return false;
			}
		}

		if (inventory[3].stackSize < getInventoryStackLimit()
				&& inventory[3].stackSize < inventory[3].getMaxStackSize()) {
			return true;
		}

		return false;
	}

	private void meltItem(int slot) {
		if (this.canMelt(slot)) {
			SmelterOutput result = MaricultureHandlers.smelter.getResult(inventory[slot], getTemperatureScaled(2000));
			if (result == null) {
				return;
			}

			ItemStack output = (result.chance > 0 && rand.nextInt(result.chance) == 0) ? result.output : null;
			FluidStack fluid = result.fluid.copy();
			if (output != null) {
				if (this.inventory[3] == null) {
					this.inventory[3] = output.copy();
				} else if (this.inventory[3].itemID == output.itemID) {
					++this.inventory[3].stackSize;
				}
			}

			--this.inventory[slot].stackSize;

			if (fluid.fluidID == tank.getFluidID() || tank.getFluidID() == 0) {
				fluid.amount = getFluidAmount(inventory[slot], fluid.amount);
				this.fill(ForgeDirection.UP, fluid, true);
			}

			if (this.inventory[slot].stackSize == 0) {
				this.inventory[slot] = null;
			}
		}
	}

	public TileEntity getNextTank() {
		if (this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) != null) {
			return this.worldObj.getBlockTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
		}

		if (this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) != null) {
			return this.worldObj.getBlockTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
		}

		if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) != null) {
			return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
		}

		if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) != null) {
			return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
		}

		if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) != null) {
			return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
		}

		if (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord - 1, this.zCoord) != null) {
			return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		}

		return null;
	}

	private void moveLiquidNextDoor() {
		TileEntity tile = getNextTank();

		if (tile == null || master() == null) {
			return;
		}
		
		/** If redstone signal stop sending liquid **/
		if (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
			return;
		}

		if (tile instanceof IFluidHandler && !(tile instanceof TileLiquifier)) {
			IFluidHandler tank = (IFluidHandler) tile;
				int purity = 1 + MaricultureHandlers.upgrades.getData("purity", master());
				purity = (purity < 1) ? 1 : purity;

				int drainAmount = purity * 100;

				if (Extra.DEBUG_ON) {
					drainAmount = 900;
				}
				
				if(master().getFluid() != null) {
					FluidStack fluid = master().getFluid().copy();
					if(!FluidInventoryHelper.attemptTransfer(tank, drainAmount, master())) {
						if(!FluidInventoryHelper.attemptTransfer(tank, 100, master())) {
							if(!FluidInventoryHelper.attemptTransfer(tank, 20, master())) {
								FluidInventoryHelper.attemptTransfer(tank, 1, master());
							}
						}
					}
				}
		}
	}
	
	public int getBurnTimeRemainingScaled(int par1) {
		return 11 - (fuelHandler.burnTime * par1) / fuelHandler.maxPer;
	}

	public int getCookProgressScaled(int par1) {
		return (furnaceCookTime * par1) / TIME_TAKEN;
	}

	public int getTemperatureScaled(int i) {
		return (temperature * i) / MAX_TEMP;
	}

	public boolean isBurning() {
		return fuelHandler.maxPer > 0;
	}
	
	public int getFluidAmount(ItemStack stack, int amount) {
		if(DictionaryHelper.isInDictionary(stack)) {
			String name = DictionaryHelper.getDictionaryName(stack);
			if(name.startsWith("ore")){
				amount+= bonus;
			}
		}
		
		return amount; 
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		furnaceCookTime = tagCompound.getShort("CookTime");
		temperature = tagCompound.getInteger("Temperature");
		bonus = tagCompound.getInteger("PurityBonus");
		if(fuelHandler == null) {
			fuelHandler = new FuelHandler();
		}
		
		fuelHandler.read(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("CookTime", (short) this.furnaceCookTime);
		tagCompound.setInteger("Temperature", this.temperature);
		tagCompound.setInteger("PurityBonus", this.bonus);
		if(fuelHandler == null) {
			fuelHandler = new FuelHandler();
		}
		
		fuelHandler.write(tagCompound);
	}

	public void getGUINetworkData(int i, int j) {
		super.getGUINetworkData(i, j);
		switch (i) {
		case 3:
			this.furnaceCookTime = j;
			break;
		case 4:
			this.fuelHandler.maxPer = j;
			break;
		case 5:
			this.fuelHandler.burnTime = j;
			break;
		case 6:
			this.temperature = j;
			break;
		case 7:
			this.TIME_TAKEN = j;
			break;
		case 8:
			this.bonus = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerLiquifier liquifier, EntityPlayer player) {
		super.sendGUINetworkData(liquifier, player);
		PacketIntegerUpdate.send(liquifier, 3, this.furnaceCookTime, player);
		PacketIntegerUpdate.send(liquifier, 4, this.fuelHandler.maxPer, player);
		PacketIntegerUpdate.send(liquifier, 5, this.fuelHandler.burnTime, player);
		PacketIntegerUpdate.send(liquifier, 6, this.temperature, player);
		PacketIntegerUpdate.send(liquifier, 7, this.TIME_TAKEN, player);
		PacketIntegerUpdate.send(liquifier, 8, this.bonus, player);
	}

	public String getRealTemperature() {
		return "" + (this.temperature * 2000) / MAX_TEMP;
	}
	
	@Override
	protected void updateUpgrades() {
		super.updateUpgrades();
		
		int purity = MaricultureHandlers.upgrades.getData("purity", this);
		bonus = purity * (MetalRates.NUGGET);
	}

	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[6], inventory[7], inventory[8] };
	}

	private static final int[] slots_top = new int[] { 0, 1, 4 };
	private static final int[] slots_bottom = new int[] { 2, 3, 5 };
	private static final int[] slots_sides = new int[] { 2, 3, 5 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if (master() != null) {
			return master().isItemValidForSlot(slot, stack);
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		if (master() != null) {
			if (slot == 3 || slot == 5) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		if (MaricultureHandlers.smelter.getResult(stack, -1) != null && slot < 2) {
			return true;
		}

		if (MaricultureHandlers.smelter.getBurnTemp(stack, false) > 0 && slot == 2) {
			return true;
		}

		if (FluidContainerRegistry.isEmptyContainer(stack) && slot == 4) {
			return true;
		}

		return false;
	}
}

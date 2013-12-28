package mariculture.core.blocks;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeFreezer;
import mariculture.core.blocks.base.TileMachineTank;
import mariculture.core.gui.ContainerSettler;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.HeatHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;

public class TileSettler extends TileMachineTank implements ISidedInventory {
	private int FREEZE_LENGTH = MachineSpeeds.getSettlerSpeed();

	private int freezeTime = 0;
	public TileSettler() {
		this.inventory = new ItemStack[8];
	}

	@Override
	public void updateMachine() {
		if (!this.worldObj.isRemote) {
			if(onTick(20)) {
				processContainers();
			}
		}

		if (stopInHeat()) {
			return;
		}

		boolean updated = false;

		if (!this.worldObj.isRemote) {
			if (this.canFreeze()) {
				this.freezeTime = this.freezeTime + getFreezeSpeed();

				if (this.freezeTime >= FREEZE_LENGTH) {
					this.freezeTime = 0;
					this.freezeItem();
					updated = true;
				}
			} else {
				this.freezeTime = 0;
			}
		}

		if (updated) {
			this.onInventoryChanged();
		}
	}

	private int getFreezeSpeed() {
		EnumBiomeType biomeType = MaricultureHandlers.biomeType.getBiomeType(worldObj.getBiomeGenForCoords(xCoord, zCoord));
		int heat = -HeatHelper.getTileTemperature(worldObj, xCoord, yCoord, zCoord, getUpgrades()) + biomeType.getCoolingSpeed();
		heat*=10;
		heat-=30;
		
		return (heat >= 1)? heat: 0;
	}

	private boolean stopInHeat() {
		EnumBiomeType biomeType = MaricultureHandlers.biomeType.getBiomeType(this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord));

		if (biomeType != null) {
			if (biomeType.equals(EnumBiomeType.HELL)) {
				if (MaricultureHandlers.upgrades.hasUpgrade("ethereal", this) && MaricultureHandlers.upgrades.getData("temp", this) <= -28) {
					return false;
				}
				
				return true;
			}
		}

		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	public int getFreezeProgressScaled(int par1) {
		return (freezeTime * par1) / FREEZE_LENGTH;
	}
	
	private boolean canFreeze() {
		if(tank.getFluidID() == 0 || tank.getFluidAmount() == 0) {
			return false;
		}
		
		RecipeFreezer result = MaricultureHandlers.freezer.getResult(inventory[2], tank.getFluid());
		if(result == null) {
			return false;
		}

		if(tank.getFluidAmount() < result.fluid.amount) {
			return false;
		}
		
		if(result.catalyst != null) {
			if(inventory[2].stackSize < result.catalyst.stackSize) {
				return false;
			}
		}
		
		if (inventory[0] == null || inventory[1] == null) {
			return true;
		}
		
		if (!inventory[0].isItemEqual(result.output) && !inventory[1].isItemEqual(result.output)) {
			return false;
		}
		
		if ((inventory[0].stackSize + result.output.stackSize <= getInventoryStackLimit() && inventory[0].stackSize + result.output.stackSize <= inventory[0]
				.getMaxStackSize())
				|| (inventory[1].stackSize + result.output.stackSize <= getInventoryStackLimit() && inventory[1].stackSize + result.output.stackSize <= inventory[1]
						.getMaxStackSize())) {
			return true;
		}
		
		return false;
	}

	private static ItemStack getEmptyContainerForFilledItem(final ItemStack filledContainer) {
		final FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData();

		for (int i = 0; i < data.length; i++) {
			if (data[i].filledContainer.itemID == filledContainer.itemID
					&& data[i].filledContainer.getItemDamage() == filledContainer.getItemDamage()) {
				return data[i].emptyContainer;
			}
		}

		return null;
	}

	private void processContainers() {
		ItemStack result = FluidHelper.getFluidResult(this, inventory[3], inventory[4]);
		if (result != null) {
			decrStackSize(3, 1);
			if (this.inventory[4] == null) {
				this.inventory[4] = result.copy();
			} else if (this.inventory[4].itemID == result.itemID) {
				++this.inventory[4].stackSize;
			}
		}
	}

	private int getNextSlot(final ItemStack stack) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null
					|| (inventory[i].itemID == stack.itemID && inventory[i].getItemDamage() == stack.getItemDamage() && inventory[i].stackSize < inventory[i]
							.getMaxStackSize())) {
				return i;
			}
		}

		return -1;
	}
	
	private void freezeItem() {
		if(this.canFreeze()) {
			RecipeFreezer result = MaricultureHandlers.freezer.getResult(inventory[2], tank.getFluid());
			if(result == null) {
				return;
			}
			
			if(result.catalyst != null) {
				boolean done = false;
				ItemStack output = inventory[2].copy();
				if(!done) {
					done = output.attemptDamageItem(1, new Random());
					if(!done) {
						output.stackSize-=result.catalyst.stackSize;
						if(output.stackSize <= 0) {
							output = null;
						}
					}
				}
				
				this.setInventorySlotContents(2, output);
			}
			
			int slot = getNextSlot(result.output);

			if (slot > -1) {
				if (this.inventory[slot] == null) {
					this.inventory[slot] = result.output.copy();
				} else if (this.inventory[slot].isItemEqual(result.output)) {
					inventory[slot].stackSize += result.output.stackSize;
				}

				this.drain(ForgeDirection.UNKNOWN, result.fluid.amount, true);
			}
		}
	}

	public void getGUINetworkData(int i, int j) {
		super.getGUINetworkData(i, j);
		switch (i) {
		case 3:
			freezeTime = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerSettler container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
		Packets.updateGUI(player, container, 3, this.freezeTime);
	}

	@Override
	public void readFromNBT(final NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.freezeTime = par1NBTTagCompound.getShort("CookTime");
	}

	@Override
	public void writeToNBT(final NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("CookTime", (short) this.freezeTime);
	}

	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[5], inventory[6], inventory[7] };
	}

	private static final int[] slots_top = new int[] { 2, 3 };
	private static final int[] slots_bottom = new int[] { 0, 1, 4, 2, 3 };
	private static final int[] slots_sides = new int[] { 0, 1, 4 };

	@Override
	public int[] getAccessibleSlotsFromSide(final int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot < 2 || slot == 4;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(MaricultureHandlers.freezer.getResult(stack, null) != null && slot == 2) {
			return true;
		}

		if (FluidContainerRegistry.isFilledContainer(stack) && slot == 3) {
			return true;
		}

		return false;
	}

	@Override
	public EjectSetting getEjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canWork() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getProcess() {
		// TODO Auto-generated method stub
		return null;
	}
}
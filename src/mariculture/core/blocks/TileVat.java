package mariculture.core.blocks;

import java.util.ArrayList;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeVat;
import mariculture.api.core.RecipeVat.RecipeVatAlloy;
import mariculture.api.core.RecipeVat.RecipeVatItem;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.blocks.base.TileMultiStorageTank;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.network.Packet113MultiInit;
import mariculture.core.network.Packet118FluidUpdate;
import mariculture.core.network.Packets;
import mariculture.core.util.EntityFakeItem;
import mariculture.factory.blocks.Tank;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileVat extends TileMultiStorageTank implements ISidedInventory {
	public static int max_lrg = 24000;
	public static int max_sml = 6000;
	public Tank tank2;
	public Tank tank3;
	
	public int entityID;
	public int entityOutputID;
	public int timeRemaining;
	public boolean canWork;
	private int machineTick;
	
	public TileVat() {
		tank = new Tank(max_sml);
		tank2 = new Tank(max_sml);
		tank3 = new Tank(max_sml);
		inventory = new ItemStack[2];
		needsInit = true;
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(master == null)
			updateSingle();
	}
		
	//Updating the Multi-Block form
	@Override
	public void updateMaster() {
		if(tank.getCapacity() != max_lrg)
				tank.setCapacity(max_lrg);
		if(tank2.getCapacity() != max_lrg)
			tank2.setCapacity(max_lrg);
		if(tank3.getCapacity() != max_lrg)
			tank3.setCapacity(max_lrg);
		
		machineTick++;
		if(!isInit() && !worldObj.isRemote) {
			//Init Master
			Packets.updateTile(this, 32, new Packet113MultiInit(xCoord, yCoord, zCoord, master.xCoord, master.yCoord, master.zCoord, facing).build());
			for(MultiPart slave: slaves) {
				TileEntity te = worldObj.getBlockTileEntity(slave.xCoord, slave.yCoord, slave.zCoord);
				if(te != null && te instanceof TileVat) {
					Packets.updateTile(te, 32, new Packet113MultiInit(te.xCoord, te.yCoord, te.zCoord, 
							master.xCoord, master.yCoord, master.zCoord, ((TileMultiBlock)te).facing).build());
				}
			}

			this.setInit(true);
		}
		
		
		if(!worldObj.isRemote) {
			if(canWork && onTick(1))
				timeRemaining--;
			updateAll();
			
			if(onTick(20)) {
				addInputEntity();
				addOutputEntity();
			}
		}
	}
	
	public void addInputEntity() {
		if(entityID == 0) {
			ItemStack initial = inventory[0];
			if(initial == null)
				initial = new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR);
			EntityFakeItem entity = new EntityFakeItem(worldObj, xCoord + 1D, yCoord + 0.6, zCoord + 1D, initial);
			entityID = entity.entityId;
			worldObj.spawnEntityInWorld(entity);
		} else {
			if(worldObj.getEntityByID(entityID) instanceof EntityFakeItem) {
				EntityFakeItem item = (EntityFakeItem) worldObj.getEntityByID(entityID);
				if(item != null) {
					if(inventory[0] != null)
						item.setEntityItemStack(inventory[0]);
					else
						item.setEntityItemStack(new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR));
				}
			}
		}
	}
	
	public void addOutputEntity() {
		if(entityOutputID == 0) {
			ItemStack initial = inventory[1];
			if(initial == null)
				initial = new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR);
			EntityFakeItem entity = new EntityFakeItem(worldObj, xCoord + 1D, yCoord + 0.6, zCoord + 1D, initial);
			entityOutputID = entity.entityId;
			worldObj.spawnEntityInWorld(entity);
		} else {
			if(worldObj.getEntityByID(entityOutputID) instanceof EntityFakeItem) {
				EntityFakeItem item = (EntityFakeItem) worldObj.getEntityByID(entityOutputID);
				if(item != null) {
					if(inventory[1] != null)
						item.setEntityItemStack(inventory[1]);
					else
						item.setEntityItemStack(new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR));
				}
			}
		}
	}
	
	//The Code for when we are updating a single block!
	public void updateSingle() {
		machineTick++;
		if(!worldObj.isRemote) {
			if(canWork && onTick(30))
				timeRemaining--;
			updateAll();
		}
	}
	
	public void updateAll() {
		if(onTick(20))
			canWork = canWork();
		
		if(canWork) {
			if(timeRemaining <= 0 && timeRemaining > -9999) {
				Object[] result = getResult();
				RecipeVat recipe = (RecipeVat) result[0];
				byte tankNum = (Byte) result[1];
				if(recipe instanceof RecipeVatAlloy) {
					createAlloy((RecipeVatAlloy) recipe, tankNum);
				} else if (recipe instanceof RecipeVatItem) {
					createItem((RecipeVatItem) recipe, tankNum);
				}
				
				timeRemaining = -9999;
			}
		}
	}
	
	public boolean canWork() {
		if(tank.getFluidAmount() <= 0 && tank2.getFluidAmount() <= 0)
			return false;
		RecipeVat res = (RecipeVat) getResult()[0];
		if(res == null)
			return false;
		if(timeRemaining < 0)
			timeRemaining = res.cookTime;
		return true;
	}
	
	public Object[] getResult() {
		byte tankNum = 1;
		RecipeVat result = MaricultureHandlers.vat.getAlloyResult(tank.getFluid(), tank2.getFluid());
		result = (result== null || !hasRoom(((RecipeVatAlloy)result).output))? null: result;
		if(result == null) {
			tankNum = 2;
			result = MaricultureHandlers.vat.getAlloyResult(tank2.getFluid(), tank.getFluid());
			result = (result== null || !hasRoom(((RecipeVatAlloy)result).output))? null: result;
			if(result == null) {
				tankNum = 1;
				result = MaricultureHandlers.vat.getItemResult(inventory[0], tank.getFluid());
				result = (result== null || !hasRoom(((RecipeVatItem)result).output))? null: result;
				if(result == null) {
					tankNum = 2;
					result = MaricultureHandlers.vat.getItemResult(inventory[0], tank2.getFluid());
					result = (result== null || !hasRoom(((RecipeVatItem)result).output))? null: result;
				}
			}
		}
		
		return new Object[] { result, tankNum };
	}
	
	private boolean hasRoom(ItemStack stack) {
		return stack == null || inventory[1] == null || (ItemHelper.areItemStackEqualNoNull(stack, inventory[1]) 
										&& inventory[1].stackSize + stack.stackSize <= inventory[1].getMaxStackSize());
	}
	
	private void createAlloy(RecipeVatAlloy recipe, byte tankNum) {
		if(recipe.newFluid == null || tank3.fill(recipe.newFluid, false) >= recipe.newFluid.amount) {
			ItemStack output = recipe.output;
			
			if(output != null) {
				if(inventory[1] != null) {
					output.stackSize+=inventory[1].stackSize;
				}
				
				setInventorySlotContents(1, output);
			}
			
			if(tankNum == 1) {
				tank.drain(recipe.fluid.amount, true);
				tank2.drain(recipe.fluid2.amount, true);
			} else {
				tank.drain(recipe.fluid2.amount, true);
				tank2.drain(recipe.fluid.amount, true);
			}
			
			if(recipe.newFluid != null)
				tank3.fill(recipe.newFluid, true);
			
			Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid((byte)1), (byte) 1).build());
			Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid((byte)2), (byte) 2).build());
			Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid((byte)3), (byte) 3).build());
		}
	}
	
	private void createItem(RecipeVatItem recipe, byte tankNum) {
		ItemStack output = recipe.output;
		if(output != null) {
			if(inventory[1] != null) {
				output.stackSize+=inventory[1].stackSize;
			}
			
			setInventorySlotContents(1, output);
		}
		
		decrStackSize(0, 1);
		
		if(tankNum == 1)
			tank.drain(recipe.fluid.amount, true);
		else
			tank2.drain(recipe.fluid.amount, true);
		
		Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid((byte)1), (byte) 1).build());
		Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid((byte)2), (byte) 2).build());
		Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid((byte)3), (byte) 3).build());
	}

	@Override
	public Class getTEClass() {
		return this.getClass();
	}
	
//Inventory Logic
	@Override
	public ItemStack getStackInSlot(int slot) {
		TileVat vat = (master != null)? (TileVat)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord): this;
		return vat.inventory[slot];
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		TileVat vat = (master != null)? (TileVat)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord): this;
		
		vat.inventory[slot] = stack;
        if (stack != null && stack.stackSize > vat.getInventoryStackLimit()) {
        	stack.stackSize = vat.getInventoryStackLimit();
        }

        vat.onInventoryChanged();
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		TileVat vat = (master != null)? (TileVat)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord): this;
		if (vat.inventory[slot] != null) {
            ItemStack stack;

            if (vat.inventory[slot].stackSize <= amount) {
                stack = vat.inventory[slot];
                vat.inventory[slot] = null;
                vat.onInventoryChanged();
                return stack;
            } else {
                stack = vat.inventory[slot].splitStack(amount);

                if (vat.inventory[slot].stackSize == 0) {
                	vat.inventory[slot] = null;
                }

                vat.onInventoryChanged();
                return stack;
            }
        }

		return null;
	}
	
//Tank Logic		
	@Override
	public FluidStack getFluid(byte tank) {
		TileVat vat = (master != null)? (TileVat)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord): this;
		if(tank == 1)
			return vat.tank.getFluid();
		else if(tank == 2)
			return vat.tank2.getFluid();
		else if(tank == 3)
			return vat.tank3.getFluid();
		return null;
	}

	@Override
	public void setFluid(FluidStack fluid, byte tank) {
		TileVat vat = (master != null)? (TileVat)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord): this;
		if(tank == (byte)1)
			vat.tank.setFluid(fluid);
		else if(tank == (byte)2)
			vat.tank2.setFluid(fluid);
		else if(tank == (byte)3)
			vat.tank3.setFluid(fluid);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		TileVat vat = (master != null)? (TileVat)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord): this;
		//If the draining from tank3 didn't fail, send packet update, otherwise try for tank2, then tank1
		FluidStack ret = vat.tank3.drain(maxDrain, doDrain);
		if(ret != null) {
			if(doDrain)
				Packets.updateTile(vat, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, vat.getFluid((byte)3), (byte) 3).build());
		} else {
			ret = vat.tank2.drain(maxDrain, doDrain);
			if(ret != null) {
				if(doDrain)
					Packets.updateTile(vat, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, vat.getFluid((byte)2), (byte) 2).build());
			} else {
				ret = vat.tank.drain(maxDrain, doDrain);
				if(ret != null) {
					if(doDrain)
						Packets.updateTile(vat, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, vat.getFluid((byte)1), (byte) 1).build());
				}
			}
		}
		
		return ret;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		TileVat vat = (master != null)? (TileVat)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord): this;
		int ret = vat.tank.fill(resource, doFill);
		if(ret > 0) {
			if(doFill) {
				Packets.updateTile(vat, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, vat.getFluid((byte)1), (byte) 1).build());
			}
		} else {
			ret = vat.tank2.fill(resource, doFill);
			if(ret > 0) {
				if(doFill)
					Packets.updateTile(vat, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, vat.getFluid((byte)2), (byte) 2).build());
			}
		}
		
		return ret;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank2.readFromNBT(nbt);
		tank3.readFromNBT(nbt);
		timeRemaining = nbt.getInteger("TimeRemaning");
		canWork = nbt.getBoolean("CanWork");
		entityID = nbt.getInteger("EntityID");
		entityOutputID = nbt.getInteger("EntityOutputID");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank2.writeToNBT(nbt);
		tank3.writeToNBT(nbt);
		nbt.setInteger("TimeRemaining", timeRemaining);
		nbt.setBoolean("CanWork", canWork);
		nbt.setInteger("EntityID", entityID);
		nbt.setInteger("EntityOutputID", entityOutputID);
	}
	
//Master Logic	
	@Override
	public void onBlockBreak() {
		if(master != null) {
			TileVat mstr = (TileVat) worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord);
			if(mstr != null) {
				mstr.tank.setCapacity(max_sml);
				if(mstr.tank.getFluidAmount() > max_sml)
					mstr.tank.setFluidAmount(max_sml);
				
				mstr.tank2.setCapacity(max_sml);
				if(mstr.tank2.getFluidAmount() > max_sml)
					mstr.tank2.setFluidAmount(max_sml);
				
				mstr.tank2.setCapacity(max_sml);
				if(mstr.tank2.getFluidAmount() > max_sml)
					mstr.tank2.setFluidAmount(max_sml);
				
				if(worldObj.getEntityByID(mstr.entityID) != null)
					worldObj.getEntityByID(mstr.entityID).setDead();
				if(worldObj.getEntityByID(mstr.entityOutputID) != null)
					worldObj.getEntityByID(mstr.entityOutputID).setDead();
				mstr.entityID = 0;
				mstr.entityOutputID = 0;
			}
		}
		
		if(worldObj.getEntityByID(entityID) != null)
			worldObj.getEntityByID(entityID).setDead();
		entityID = 0;
		if(worldObj.getEntityByID(entityOutputID) != null)
			worldObj.getEntityByID(entityOutputID).setDead();
		entityOutputID = 0;

		super.onBlockBreak();
	}
	
	@Override
	public boolean isPartnered(int x, int y, int z) {
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		return tile instanceof TileVat?  ((TileVat)tile).master != null : false;
	}
	
	@Override
	public boolean isPart(int x, int y, int z) {
		return worldObj.getBlockTileEntity(x, y, z) instanceof TileVat && !isPartnered(x, y, z);
	}
	
	@Override
	public void onBlockPlaced() {
		onBlockPlaced(xCoord, yCoord, zCoord);
		Packets.updateTile(this, 32, getDescriptionPacket());
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public void onBlockPlaced(int x, int y, int z) {
		if(isPart(x, y, z) && isPart(x + 1, y, z) && isPart(x, y, z + 1) && isPart(x + 1, y, z + 1)) {
			MultiPart mstr = new MultiPart(x, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x + 1, y, z + 1, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.EAST));
			setAsMaster(mstr, parts, ForgeDirection.SOUTH);
		}
		
		if(isPart(x - 1, y, z) && isPart(x, y, z) && isPart(x, y, z + 1) && isPart(x - 1, y, z + 1)) {
			MultiPart mstr = new MultiPart(x - 1, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x - 1, y, z + 1, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.NORTH));
			setAsMaster(mstr, parts, ForgeDirection.SOUTH);
		}
		
		if(isPart(x, y, z) && isPart(x - 1, y, z) && isPart(x - 1, y, z - 1) && isPart(x, y, z - 1)) {
			MultiPart mstr = new MultiPart(x - 1, y, z - 1);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x - 1, y, z, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x, y, z - 1, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.SOUTH);
		}
		
		if(isPart(x, y, z) && isPart(x + 1, y, z) && isPart(x, y, z - 1) && isPart(x + 1, y, z - 1)) {
			MultiPart mstr = new MultiPart(x, y, z - 1);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x + 1, y, z - 1, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.SOUTH);
		}
		
		Packets.updateTile(this, 32, getDescriptionPacket());
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot == 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 1;
	}
}

package mariculture.factory.blocks;

import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.blocks.base.TileMultiMachineTank;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.network.Packets;
import mariculture.core.util.FluidDictionary;
import mariculture.factory.FactoryEvents;
import mariculture.factory.items.ItemArmorFLUDD;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class TilePressureVessel extends TileMultiMachineTank {
	public TilePressureVessel() {
		inventory = new ItemStack[6];
		setting = EjectSetting.NONE;
		mode = RedstoneMode.DISABLED;
	}
	
	@Override
	public int getTankCapacity(int storage) {
		int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
		return ((100 * tankRate) + (storage * 5 * tankRate)) * (slaves.size() + 1);
	}
	
	public static final int in = 3;
	public static final int out = 4;
	public static final int fludd = 5;
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 3, 4, 5 };
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if(slot == in)
			return FluidHelper.isFluidOrEmpty(stack);
		return slot == fludd;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot >= out;
	}
	
	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.FLUID;
	}
	
	@Override
	public void updateMasterMachine() {
		if(onTick(30) && RedstoneMode.canWork(this, mode) && EjectSetting.canEject(setting, EjectSetting.FLUID))
			helper.ejectFluid(new int[] { speed * 100, 100, 50, 25, 10, 5, 1 });
		if(onTick(20))
			fillFLUDD();
	}
	
	@Override
	public void updateSlaveMachine() {
		if(onTick(30)) {
			TilePressureVessel mstr = (TilePressureVessel) getMaster();
			if(mstr != null && RedstoneMode.canWork(this, mstr.mode) && EjectSetting.canEject(mstr.setting, EjectSetting.FLUID))
				helper.ejectFluid(new int[] { ((TilePressureVessel)getMaster()).speed * 100, 100, 50, 25, 10, 5, 1 });
		}
	}
	
	private void fillFLUDD() {
		if(inventory[fludd] != null && inventory[fludd].getItem() instanceof ItemArmorFLUDD) {
			if(tank.getFluidID() == FluidRegistry.getFluidID(FluidDictionary.hp_water) && tank.getFluidAmount() > 0) {
				ItemStack stack = inventory[fludd].copy();
				int water = 0;
				if (stack.hasTagCompound()) {
					water = stack.stackTagCompound.getInteger("water");
				}
				
				if(water + (speed * 100) < ItemArmorFLUDD.STORAGE && tank.getFluidAmount() >= (speed * 100)) {
					stack.stackTagCompound.setInteger("water", water + (speed * 100));
					drain(ForgeDirection.UNKNOWN, (speed * 100), true);
				} else if(water + 100 < ItemArmorFLUDD.STORAGE && tank.getFluidAmount() >= 100) {
					stack.stackTagCompound.setInteger("water", water + 100);
					drain(ForgeDirection.UNKNOWN, 100, true);
				} else if(water + 50 < ItemArmorFLUDD.STORAGE && tank.getFluidAmount() >= 50) {
					stack.stackTagCompound.setInteger("water", water + 50);
					drain(ForgeDirection.UNKNOWN, 50, true);
				} else if(water + 10 < ItemArmorFLUDD.STORAGE && tank.getFluidAmount() >= 100) {
					stack.stackTagCompound.setInteger("water", water + 10);
					drain(ForgeDirection.UNKNOWN, 10, true);
				} else if(water + 1 < ItemArmorFLUDD.STORAGE && tank.getFluidAmount() >= 1) {
					stack.stackTagCompound.setInteger("water", water + 1);
					drain(ForgeDirection.UNKNOWN, 1, true);
				}
				
				inventory[fludd] = stack;
			}
		}
	}
	
	@Override
	public boolean canWork() {
		return false;
	}
	
//MultiBlock Stuffs	
	public TilePressureVessel isSameBlock(int x, int y, int z) {
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		return tile != null && tile instanceof TilePressureVessel? (TilePressureVessel)tile: null;
	}
	
	public boolean tryToAdd(int x, int y, int z) {
		TilePressureVessel neighbour = isSameBlock(x, y, z);
		if(neighbour != null) {
			master = neighbour.master;
			TilePressureVessel mstr = (TilePressureVessel) getMaster();
			mstr.addSlave(new MultiPart(xCoord, yCoord, zCoord));
			
			return true;
		}
		
		return false;
	}

	//MasterStuff
	public void onBlockPlaced() {
		if(tryToAdd(xCoord + 1, yCoord, zCoord))
			return;
		if(tryToAdd(xCoord - 1, yCoord, zCoord))
			return;
		if(tryToAdd(xCoord, yCoord, zCoord + 1))
			return;
		if(tryToAdd(xCoord, yCoord, zCoord - 1))
			return;
		if(tryToAdd(xCoord, yCoord + 1, zCoord))
			return;
		if(tryToAdd(xCoord, yCoord - 1, zCoord))
			return;
		master = new MultiPart(xCoord, yCoord, zCoord);
	}
	
	public void onBlockBreak() {
		if(getMaster() != null) {
			TileMultiBlock master = getMaster();
			if(master.equals(this)) {
				if(slaves.size() > 0) {
					//Set the new master to the first slot
					MultiPart coords = slaves.get(0);
					//Remove the first index
					slaves.remove(0);
					//Update all Existing slaves so they know who their new master is
					for(MultiPart slave: slaves) {
						TilePressureVessel vessel = (TilePressureVessel) worldObj.getBlockTileEntity(slave.xCoord, slave.yCoord, slave.zCoord);
						if(vessel != null) {	
							vessel.setMaster(new MultiPart(coords.xCoord, coords.yCoord, coords.zCoord));
						}
					}
					
					this.master = coords;
					
					//now that the tile knows all about it's stuff, lets pass on the new NBT
					NBTTagCompound contents = new NBTTagCompound();
					writeToNBT(contents);
					contents.setInteger("x", coords.xCoord);
					contents.setInteger("y", coords.yCoord);
					contents.setInteger("z", coords.zCoord);
					
					TilePressureVessel theMaster = (TilePressureVessel) worldObj.getBlockTileEntity(coords.xCoord, coords.yCoord, coords.zCoord);
					if(theMaster != null) {
						theMaster.readFromNBT(contents);
					}
				}
			} else {
				for(MultiPart part: master.getSlaves()){
					if(part.xCoord == xCoord && part.yCoord == yCoord && part.zCoord == zCoord) {
						master.removeSlave(part);
						break;
					}
				}
			}
		}
	}
}
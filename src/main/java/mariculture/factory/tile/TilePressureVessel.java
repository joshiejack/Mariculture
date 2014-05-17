package mariculture.factory.tile;

import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.tile.base.TileMultiBlock;
import mariculture.core.tile.base.TileMultiMachineTank;
import mariculture.core.util.Fluids;
import mariculture.factory.items.ItemArmorFLUDD;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
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
	public void updateMasterMachine() {
		return;
	}

	@Override
	public void updateSlaveMachine() {
		return;
	}
	
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
	public boolean canUpdate() {
		return false;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		TilePressureVessel mstr = getMaster() != null? ((TilePressureVessel)getMaster()): null;
		if(mstr == null) return;
		mstr.inventory[slot] = stack;

        if (stack != null && stack.stackSize > mstr.getInventoryStackLimit()) {
        	stack.stackSize = mstr.getInventoryStackLimit();
        }
        
        fillFLUDD();
        FluidHelper.process(this, 3, 4);
		updateUpgrades();

        mstr.markDirty();
	}
	
	@Override
	public Packet getDescriptionPacket()  {
		fillFLUDD();
        FluidHelper.process(this, 3, 4);
		updateUpgrades();
        return super.getDescriptionPacket();
    }
	
	@Override
	public void markDirty() {
		super.markDirty();
		
		
	}
	
	private void fillFLUDD() {
		if(inventory[fludd] != null && inventory[fludd].getItem() instanceof ItemArmorFLUDD) {
			if(tank.getFluidID() == FluidRegistry.getFluidID(Fluids.hp_water) && tank.getFluidAmount() > 0) {
				ItemStack stack = inventory[fludd].copy();
				int water = 0;
				if (stack.hasTagCompound()) {
					water = stack.stackTagCompound.getInteger("water");
				}
				
				int drain = ItemArmorFLUDD.STORAGE - water;
				if(drain > 0 && tank.getFluidAmount() == drain) {
					tank.drain(drain, true);
					stack.stackTagCompound.setInteger("water", ItemArmorFLUDD.STORAGE);
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
		TileEntity tile = worldObj.getTileEntity(x, y, z);
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
						TilePressureVessel vessel = (TilePressureVessel) worldObj.getTileEntity(slave.xCoord, slave.yCoord, slave.zCoord);
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
					
					TilePressureVessel theMaster = (TilePressureVessel) worldObj.getTileEntity(coords.xCoord, coords.yCoord, coords.zCoord);
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
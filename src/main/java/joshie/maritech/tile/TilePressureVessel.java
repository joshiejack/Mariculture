package joshie.maritech.tile;

import joshie.mariculture.api.core.IPVChargeable;
import joshie.mariculture.core.gui.feature.FeatureEject.EjectSetting;
import joshie.mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import joshie.mariculture.core.helpers.FluidHelper;
import joshie.mariculture.core.tile.base.TileMultiBlock;
import joshie.mariculture.core.tile.base.TileMultiMachineTank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class TilePressureVessel extends TileMultiMachineTank {
    public TilePressureVessel() {
        inventory = new ItemStack[6];
        setting = EjectSetting.NONE;
        mode = RedstoneMode.DISABLED;
    }

    @Override
    public int getTankCapacity(int storage) {
        int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
        return Math.max(1, (200 * tankRate + storage * 8 * tankRate) * (slaves.size() + 1));
    }

    private static final int in = 3;
    private static final int out = 4;
    private static final int special = 5;

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
        if (slot == in) return FluidHelper.isFluidOrEmpty(stack);
        return slot == special;
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
        TilePressureVessel mstr = getMaster() != null ? (TilePressureVessel) getMaster() : null;
        if (mstr == null) return;
        mstr.inventory[slot] = stack;

        if (stack != null && stack.stackSize > mstr.getInventoryStackLimit()) {
            stack.stackSize = mstr.getInventoryStackLimit();
        }

        fillSpecial();
        FluidHelper.process(tank, this, 3, 4);
        updateUpgrades();

        mstr.markDirty();
    }

    @Override
    public Packet getDescriptionPacket() {
        fillSpecial();
        FluidHelper.process(tank, this, 3, 4);
        updateUpgrades();
        return super.getDescriptionPacket();
    }

    @Override
    public void markDirty() {
        super.markDirty();

    }

    private void fillSpecial() {
        if (inventory[special] != null && inventory[special].getItem() instanceof IPVChargeable) {
            ((IPVChargeable) inventory[special].getItem()).fill(tank, inventory, special);
        }
    }

    @Override
    public boolean canWork() {
        return false;
    }

    //MultiBlock Stuffs	
    private TilePressureVessel isSameBlock(int x, int y, int z) {
        TileEntity tile = worldObj.getTileEntity(x, y, z);
        return tile != null && tile instanceof TilePressureVessel ? (TilePressureVessel) tile : null;
    }

    private boolean tryToAdd(int x, int y, int z) {
        TilePressureVessel neighbour = isSameBlock(x, y, z);
        if (neighbour != null) {
            master = neighbour.master;
            TilePressureVessel mstr = (TilePressureVessel) getMaster();
            mstr.addSlave(new MultiPart(xCoord, yCoord, zCoord));

            return true;
        }

        return false;
    }

    //MasterStuff
    @Override
    public void onBlockPlaced() {
        if (tryToAdd(xCoord + 1, yCoord, zCoord)) return;
        if (tryToAdd(xCoord - 1, yCoord, zCoord)) return;
        if (tryToAdd(xCoord, yCoord, zCoord + 1)) return;
        if (tryToAdd(xCoord, yCoord, zCoord - 1)) return;
        if (tryToAdd(xCoord, yCoord + 1, zCoord)) return;
        if (tryToAdd(xCoord, yCoord - 1, zCoord)) return;
        master = new MultiPart(xCoord, yCoord, zCoord);
    }

    @Override
    public void onBlockBreak() {
        if (getMaster() != null) {
            TileMultiBlock master = getMaster();
            if (master.equals(this)) {
                if (slaves.size() > 0) {
                    //Set the new master to the first slot
                    MultiPart coords = slaves.get(0);
                    //Remove the first index
                    slaves.remove(0);
                    //Update all Existing slaves so they know who their new master is
                    for (MultiPart slave : slaves) {
                        TilePressureVessel vessel = (TilePressureVessel) worldObj.getTileEntity(slave.xCoord, slave.yCoord, slave.zCoord);
                        if (vessel != null) {
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
                    if (theMaster != null) {
                        theMaster.readFromNBT(contents);
                    }
                }
            } else {
                for (MultiPart part : master.getSlaves())
                    if (part.xCoord == xCoord && part.yCoord == yCoord && part.zCoord == zCoord) {
                        master.removeSlave(part);
                        break;
                    }
            }
        }
    }
}
package mariculture.factory.tile;

import mariculture.api.util.CachedCoords;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/* This block does nothing, except animation */
public class TileRotor extends TileEntity {
    private boolean northSouth;
    public int energyStored;
    public double tier;
    public CachedCoords master;

    public TileRotor() {}

    public TileRotor(double d) {
        this.tier = (byte) d;
    }

    public void setMaster(CachedCoords master) {
        this.master = master;
    }

    public void addEnergy(int i) {
        this.energyStored += ((double) i * tier);
    }

    public int steal() {
        int ret = energyStored;
        energyStored = 0;
        return ret;
    }

    public TileEntity getMasterFromCoords(CachedCoords cord) {
        return master != null ? worldObj.getTileEntity(cord.x, cord.y, cord.z) : null;
    }

    //Called when this block is removed, or when it receives a block update, to reset the master
    public void recheck() {
        TileEntity tile = getMasterFromCoords(master);
        if (tile instanceof TileGenerator) {
            master = null; //Set the master to null, as this block may have been removed from the net
            ((TileGenerator) tile).reset();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.getBoolean("HasMaster")) {
            int x = nbt.getInteger("MasterX");
            int y = nbt.getInteger("MasterY");
            int z = nbt.getInteger("MasterZ");
            master = new CachedCoords(x, y, z);
        }

        northSouth = nbt.getBoolean("Orientation");
        energyStored = nbt.getInteger("Stored");
        tier = nbt.getDouble("Tier");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (master != null) {
            nbt.setBoolean("HasMaster", true);
            nbt.setInteger("MasterX", master.x);
            nbt.setInteger("MasterY", master.y);
            nbt.setInteger("MasterZ", master.z);
        } else nbt.setBoolean("HasMaster", false);

        nbt.setBoolean("Orientation", northSouth);
        nbt.setInteger("Stored", energyStored);
        nbt.setDouble("Tier", tier);
    }
}

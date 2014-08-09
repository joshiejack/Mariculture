package mariculture.factory.tile;

import mariculture.api.util.CachedCoords;
import mariculture.core.util.IFaceable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/* This block does nothing, except animation */
public abstract class TileRotor extends TileEntity implements IFaceable {
    public boolean northSouth;
    public int energyStored;
    public CachedCoords master;
    public float angle;
    public boolean doAnim;
    public int animTicker;

    //Set by the Rotor
    public final String block;
    public final double tier;

    public TileRotor() {
        block = getBlock();
        tier = getTier();
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().expand(1D, 1D, 1D);
    }

    private void animate(ForgeDirection dir) {
        if (dir == ForgeDirection.NORTH) {
            angle -= 3F;
        } else if (dir == ForgeDirection.SOUTH) {
            angle += 3F;
        } else if (dir == ForgeDirection.WEST) {
            angle -= 3F;
        } else if (dir == ForgeDirection.EAST) {
            angle += 3F;
        }
    }

    @Override
    public void updateEntity() {
        //if (doAnim) {
        if (animTicker < 360) {
            animate(getFacing());
            animTicker += 1;
        } else {
            doAnim = false;
            animTicker = 0;
        }
        // }
    }

    protected String getBlock() {
        return "plankWood";
    }

    protected double getTier() {
        return 0.5D;
    }

    public void setMaster(CachedCoords master) {
        this.master = master;
    }

    public void addEnergy(int i) {
        System.out.println("energy added");
        
        energyStored += ((double) i * tier);
        doAnim = true;
    }

    public int steal() {
        int ret = energyStored;
        energyStored = 0;
        return ret;
    }

    public boolean isBuilt() {
        return true;
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
    public boolean rotate() {
        northSouth = !northSouth;
        return true;
    }

    @Override
    public ForgeDirection getFacing() {
        return northSouth ? ForgeDirection.NORTH : ForgeDirection.WEST;
    }

    @Override
    public void setFacing(ForgeDirection dir) {
        northSouth = (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH);
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
    }
}

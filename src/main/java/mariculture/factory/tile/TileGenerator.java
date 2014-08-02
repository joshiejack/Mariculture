package mariculture.factory.tile;

import java.util.LinkedList;

import mariculture.api.util.CachedCoords;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.util.IFaceable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;

public class TileGenerator extends TileEntity implements IEnergyConnection, IFaceable {
    private static final int MAX_TRANSFER = 5000;
    private static final int MAX_STORED = 500000;

    private boolean isInit = false;
    private int energyStored;
    public ForgeDirection orientation = ForgeDirection.NORTH;

    public static LinkedList<CachedCoords> cords;

    public TileGenerator() {
        cords = new LinkedList();
    }

    public boolean onTick(int i) {
        return worldObj.getWorldTime() % i == 0;
    }

    public void reset() {
        cords = new LinkedList();
        if (orientation != null) {
            for (int i = 1; isRotor(worldObj, xCoord + (orientation.offsetX * i), yCoord, zCoord + (orientation.offsetZ * i)); i++) {
                CachedCoords inates = new CachedCoords(xCoord + (orientation.offsetX * i), yCoord, zCoord + (orientation.offsetZ * i));
                cords.add(inates);
                getRotorFromCoords(inates).setMaster(new CachedCoords(xCoord, yCoord, zCoord));
            }
        }
    }

    public boolean isRotor(World world, int x, int y, int z) {
        return world.getTileEntity(x, y, z) instanceof TileRotor;
    }

    public TileRotor getRotorFromCoords(CachedCoords cord) {
        return (TileRotor) worldObj.getTileEntity(cord.x, cord.y, cord.z);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public void updateEntity() {
        if (!isInit) {
            isInit = true;
            reset();
        }

        if (energyStored < MAX_STORED && onTick(20)) {
            //Grab Power Stored in the Rotor and add to the generators buffer
            for (CachedCoords cord : cords) {
                energyStored += getRotorFromCoords(cord).steal();
            }
        }

        if (energyStored >= 0) {
            //Take the power inside of me and pass it to all 'sides'
            for (Integer i : BlockTransferHelper.getSides()) {
                ForgeDirection dir = ForgeDirection.values()[i];
                //Don't output the front or back of the generator, any other side is valid
                if (orientation != null) {
                    if (dir != orientation && dir != orientation.getOpposite()) {
                        TileEntity tile = BlockHelper.getAdjacentTileEntity(worldObj, xCoord, yCoord, zCoord, dir);
                        if (tile instanceof IEnergyHandler && energyStored > 0) {
                            if (((IEnergyHandler) tile).canConnectEnergy(dir.getOpposite())) {
                                int extract = -((IEnergyHandler) tile).receiveEnergy(dir.getOpposite(), Math.min(energyStored, MAX_TRANSFER), false);
                                energyStored -= (Math.min(energyStored, MAX_TRANSFER));
                            }
                        }
                    }
                }
            }
        } else energyStored = 0;

    }

    @Override
    public boolean rotate() {
        setFacing(mariculture.core.helpers.BlockHelper.rotate(orientation));
        return true;
    }

    @Override
    public ForgeDirection getFacing() {
        return orientation;
    }

    @Override
    public void setFacing(ForgeDirection dir) {
        orientation = dir.getOpposite();
        if (!worldObj.isRemote) {
            PacketHandler.updateOrientation(this);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
        energyStored = nbt.getInteger("Stored");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Orientation", orientation.ordinal());
        nbt.setInteger("Stored", energyStored);
    }
}

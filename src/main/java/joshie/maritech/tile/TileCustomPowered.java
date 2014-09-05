package joshie.maritech.tile;

import joshie.lib.helpers.PowerHelper;
import joshie.mariculture.factory.tile.TileCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileCustomPowered extends TileCustom implements IEnergyHandler {
    private int tick;
    private ForgeDirection cameFrom;
    private EnergyStorage storage = new EnergyStorage(6000);

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        storage.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        storage.writeToNBT(tagCompound);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        cameFrom = from;
        if (storage.getEnergyStored() > 0) {
            IEnergyHandler handler = null;
            Object[] res = PowerHelper.getNextEnergyHandler(cameFrom, worldObj, xCoord, yCoord, zCoord);
            if (res[0] != null) {
                handler = (IEnergyHandler) res[0];
                cameFrom = (ForgeDirection) res[1];
            }

            if (handler != null) if (handler.canConnectEnergy(cameFrom)) {
                storage.modifyEnergyStored(-handler.receiveEnergy(cameFrom.getOpposite(), Math.min(storage.getMaxEnergyStored(), storage.getEnergyStored()), false));
            }
        }

        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }
}

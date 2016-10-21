package joshie.mariculture.core.util.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class MCEnergyStorage extends EnergyStorage {
    public MCEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public MCEnergyStorage readFromNBT(NBTTagCompound nbt) {
        energy = nbt.getInteger("Energy");
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("Energy", energy);
        return nbt;
    }
}

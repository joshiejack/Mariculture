package mariculture.factory.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileTurbineWater extends TileEntity {
    private static final int MAX_BUFFER = 20000;
    public int energy;

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        energy = nbt.getInteger("EnergyStored");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Energy", energy);
    }
}
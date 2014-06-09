package mariculture.api.core;

import net.minecraft.nbt.NBTTagCompound;

public class FuelInfo {
    //Maximum Temperature this fuel can take you up to
    public int maxTemp;
    //Maximum each 'unit' can burn up to (a unit for liquids is 16 mB)
    public int maxTempPer;
    //Number of ticks, this fuel will last per unit by default
    public int ticksPer;

    public FuelInfo() {}

    public FuelInfo(int temp, int maxPer, int ticksPer) {
        maxTemp = temp;
        maxTempPer = maxPer;
        this.ticksPer = ticksPer;
    }

    public void read(NBTTagCompound nbt) {
        maxTemp = nbt.getInteger("MaximumTemp");
        maxTempPer = nbt.getInteger("MaximumPer");
        ticksPer = nbt.getInteger("MaximumTicks");
    }

    public void write(NBTTagCompound nbt) {
        nbt.setInteger("MaximumTemp", maxTemp);
        nbt.setInteger("MaximumPer", maxTempPer);
        nbt.setInteger("MaximumTicks", ticksPer);
    }
}

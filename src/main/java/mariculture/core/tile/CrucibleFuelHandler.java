package mariculture.core.tile;

import mariculture.api.core.FuelInfo;
import net.minecraft.nbt.NBTTagCompound;

public class CrucibleFuelHandler {
    TileCrucible crucible;
    int usedHeat;
    int tick;
    FuelInfo info;
    
    public CrucibleFuelHandler() {}
    public CrucibleFuelHandler(TileCrucible crucible) {
        this.crucible = crucible;
    }

    void read(NBTTagCompound nbt) {
        if (nbt.getBoolean("HasHandler")) {
            info = new FuelInfo();
            info.read(nbt);
        }
    }

    void write(NBTTagCompound nbt) {
        if (info != null) {
            nbt.setBoolean("HasHandler", true);
            info.write(nbt);
        }
    }

    void set(FuelInfo info) {
        this.info = info;
        tick = 0;
        usedHeat = 0;
    }

    private int getMaxTempPer(int purity) {
        return info.maxTempPer * (purity + 1);
    }

    int tick(int temp, int purity) {
        int realUsed = usedHeat * 2000 / crucible.MAX_TEMP;
        int realTemp = temp * 2000 / crucible.MAX_TEMP;

        tick++;

        if (realUsed < getMaxTempPer(purity) && realTemp < info.maxTemp) {
            temp += crucible.heat / 3 + 1;
            usedHeat += crucible.heat / 3 + 1;
        }

        if (realUsed >= getMaxTempPer(purity) || tick >= info.ticksPer) {
            info = null;
            if (crucible.canFuel()) {
                crucible.fuelHandler.set(crucible.getInfo());
            } else {
                crucible.fuelHandler.set(null);
            }
        }

        return temp;
    }
}

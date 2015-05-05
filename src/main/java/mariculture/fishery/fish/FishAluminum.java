package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletAluminum;
import net.minecraft.world.World;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishAluminum extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 8;
    }

    @Override
    public int getTemperatureTolerance() {
        return 8;
    }

    @Override
    public Salinity getSalinityBase() {
        return Salinity.FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 1;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 5;
    }

    @Override
    public int getFertility() {
        return 500;
    }
    
    @Override
    public int getBaseProductivity() {
        return 0;
    }
    
    @Override
    public double getFishOilVolume() {
        return 0.0D;
    }
    
    @Override
    public int getFoodStat() {
        return -1;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletAluminum, 15D);
        addProduct(dropletAir, 10D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }
}

package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletSilver;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishSilver extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 20;
    }

    @Override
    public int getTemperatureTolerance() {
        return 5;
    }

    @Override
    public Salinity getSalinityBase() {
        return Salinity.FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 2;
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 6;
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
    public int getFishMealSize() {
        return 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletSilver, 15D);
        addProduct(dropletEarth, 10D);
        addProduct(dropletFlux, 7.5D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }
}

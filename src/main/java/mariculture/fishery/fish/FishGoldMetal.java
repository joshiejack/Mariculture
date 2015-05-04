package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletGold;
import static mariculture.core.lib.MCLib.dropletRegen;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishGoldMetal extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 20;
    }

    @Override
    public int getTemperatureTolerance() {
        return 20;
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
        return 3;
    }

    @Override
    public int getFertility() {
        return 300;
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
    public void addFishProducts() {
        addProduct(dropletGold, 15D);
        addProduct(dropletFlux, 10D);
        addProduct(dropletRegen, 5D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }
}

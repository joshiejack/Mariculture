package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletNether;
import static mariculture.core.lib.MCLib.dropletOsmium;
import static mariculture.core.lib.MCLib.dropletRegen;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishOsmium extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 10;
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
        return 0;
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
        return 5000;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletOsmium, 5D);
        addProduct(dropletNether, 5D);
        addProduct(dropletRegen, 1D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }
}

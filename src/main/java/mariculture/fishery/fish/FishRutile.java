package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletRutile;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishRutile extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 8;
    }

    @Override
    public int getTemperatureTolerance() {
        return 15;
    }

    @Override
    public Salinity getSalinityBase() {
        return Salinity.SALINE;
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
        return 20;
    }

    @Override
    public int getFertility() {
        return 1000;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletRutile, 5D);
        addProduct(dropletAqua, 7D);
        addProduct(dropletWater, 10D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }
}

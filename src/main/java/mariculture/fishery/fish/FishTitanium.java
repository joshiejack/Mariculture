package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletRutile;
import static mariculture.core.lib.MCLib.dropletTitanium;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishTitanium extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 20;
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
        return 2;
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 30;
    }

    @Override
    public int getFertility() {
        return 30;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletRutile, 10D);
        addProduct(dropletTitanium, 5D);
        addProduct(dropletEarth, 7D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }
}

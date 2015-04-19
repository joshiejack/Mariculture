package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletPlatinum;
import static mariculture.core.lib.MCLib.dropletRegen;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishPlatinum extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 15;
    }

    @Override
    public int getTemperatureTolerance() {
        return 10;
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
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 20;
    }

    @Override
    public int getFertility() {
        return 20;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletPlatinum, 13D);
        addProduct(dropletFlux, 20D);
        addProduct(dropletAir, 10D);
        addProduct(dropletRegen, 10D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }
}

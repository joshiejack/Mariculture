package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletFrozen;
import static mariculture.core.lib.MCLib.dropletIron;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishIron extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 25;
    }

    @Override
    public int getTemperatureTolerance() {
        return 15;
    }

    @Override
    public Salinity getSalinityBase() {
        return Salinity.BRACKISH;
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
        return 15;
    }

    @Override
    public int getFertility() {
        return 130;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletIron, 12.5D);
        addProduct(dropletWater, 8.5D);
        addProduct(dropletFlux, 5D);
        addProduct(dropletFrozen, 7D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }
}

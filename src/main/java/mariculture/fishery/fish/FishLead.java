package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletLead;
import static mariculture.core.lib.MCLib.dropletPoison;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

public class FishLead extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 5;
    }

    @Override
    public int getTemperatureTolerance() {
        return 7;
    }

    @Override
    public Salinity getSalinityBase() {
        return Salinity.BRACKISH;
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
        return 11;
    }

    @Override
    public int getFertility() {
        return 2000;
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
        addProduct(dropletLead, 15D);
        addProduct(dropletEarth, 10D);
        addProduct(dropletPoison, 10D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }
}

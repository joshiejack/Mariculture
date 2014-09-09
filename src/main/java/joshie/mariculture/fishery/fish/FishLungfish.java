package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletEarth;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;

public class FishLungfish extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 13;
    }

    @Override
    public int getTemperatureTolerance() {
        return 9;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
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
        return 100;
    }

    @Override
    public int getFertility() {
        return 300;
    }

    @Override
    public int getFoodConsumption() {
        return 1;
    }

    @Override
    public int getWaterRequired() {
        return 50;
    }
   
    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 4.3D;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 10D;
    }
}

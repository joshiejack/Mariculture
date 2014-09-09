package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletEarth;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;

public class FishPupfish extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 34;
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
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 1;
    }

    @Override
    public int getFertility() {
        return 800;
    }

    @Override
    public int getFoodConsumption() {
        return 1;
    }

    @Override
    public int getWaterRequired() {
        return 25;
    }
   
    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.066D;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 35D;
    }
}

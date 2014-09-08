package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletAqua;
import static joshie.mariculture.core.lib.MCLib.dropletEarth;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;

public class FishCatfish extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 32;
    }

    @Override
    public int getTemperatureTolerance() {
        return 13;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 2;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 22;
    }

    @Override
    public int getFertility() {
        return 4000;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 100;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 8.0D);
        addProduct(dropletAqua, 2.0D);
        addProduct(dropletEarth, 5.0D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.950D;
    }

    @Override
    public int getFishMealSize() {
        return 7;
    }

    @Override
    public float getFoodSaturation() {
        return 0.8F;
    }

    @Override
    public int getFoodDuration() {
        return 48;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isShallows(height) ? 25D : 0D;
    }
}

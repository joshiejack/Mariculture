package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;
public class FishMinnow extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { -1, 45 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH };
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
        return 1000;
    }

    @Override
    public int getBaseProductivity() {
        return 2;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 15D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.240D;
    }

    @Override
    public int getFishMealSize() {
        return 1;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        if (world.isDaytime()) return 33D;
        else return 5D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.isDaytime() && height > 70 ? 85D : 25D;
    }
}

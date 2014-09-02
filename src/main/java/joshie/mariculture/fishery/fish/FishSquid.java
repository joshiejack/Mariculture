package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.BRACKISH;
import static joshie.mariculture.api.core.Environment.Salinity.SALINE;
import static joshie.mariculture.core.lib.ItemLib.dropletWater;
import static joshie.mariculture.core.lib.ItemLib.ink;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;
public class FishSquid extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 3, 20 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { SALINE, BRACKISH };
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 5;
    }

    @Override
    public int getFertility() {
        return 2500;
    }

    @Override
    public boolean requiresFood() {
        return true;
    }

    @Override
    public int getWaterRequired() {
        return 20;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 1.5D);
        addProduct(ink, 15D);
    }

    @Override
    public double getFishOilVolume() {
        return 0D;
    }

    @Override
    public int getFishMealSize() {
        return 0;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 10D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.isDaytime() ? 85D : 55D;
    }
}

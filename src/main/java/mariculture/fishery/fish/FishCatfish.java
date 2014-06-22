package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletAqua;
import static mariculture.core.lib.ItemLib.dropletEarth;
import static mariculture.core.lib.ItemLib.dropletWater;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;

public class FishCatfish extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 20, 45 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH, BRACKISH, SALINE };
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

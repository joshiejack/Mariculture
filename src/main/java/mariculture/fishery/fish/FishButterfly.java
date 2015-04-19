package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletRegen;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.feather;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishButterfly extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 26;
    }

    @Override
    public int getTemperatureTolerance() {
        return 4;
    }

    @Override
    public Salinity getSalinityBase() {
        return SALINE;
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
        return 10;
    }

    @Override
    public int getFertility() {
        return 500;
    }

    @Override
    public int getWaterRequired() {
        return 30;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletAir, 7.5D);
        addProduct(dropletWater, 2.5D);
        addProduct(dropletRegen, 5D);
        addProduct(feather, 4D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.120D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(feather);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 5;
    }

    @Override
    public int getFishMealSize() {
        return 1;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 20D;
    }
}

package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.goldNugget;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishGold extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 8;
    }

    @Override
    public int getTemperatureTolerance() {
        return 3;
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
        return 6;
    }

    @Override
    public int getFertility() {
        return 500;
    }

    @Override
    public int getBaseProductivity() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 25;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 5D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.125D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(goldNugget);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 20;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 15D;
    }
    
    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.isDaytime() ? 75D : 15D;
    }
}

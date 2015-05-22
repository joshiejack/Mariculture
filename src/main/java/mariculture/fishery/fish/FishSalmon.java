package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletPlant;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishSalmon extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 12;
    }

    @Override
    public int getTemperatureTolerance() {
        return 5;
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
        return 8;
    }

    @Override
    public int getFertility() {
        return 2500;
    }

    @Override
    public int getWaterRequired() {
        return 40;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 5D);
        addProduct(dropletPlant, 6D);
        addProduct(new ItemStack(Items.leather), 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 4.500D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(Items.leather);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 4;
    }

    @Override
    public int getFishMealSize() {
        return 5;
    }

    @Override
    public int getFoodStat() {
        return 5;
    }

    @Override
    public float getFoodSaturation() {
        return 0.5F;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 25D;
    }
}

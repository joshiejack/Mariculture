package joshie.mariculture.fishery.fish;
import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.ItemLib.dropletEarth;
import static joshie.mariculture.core.lib.ItemLib.dropletWater;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishPerch extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 8, 15 };
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
        return 10;
    }

    @Override
    public int getFertility() {
        return 2300;
    }

    @Override
    public int getWaterRequired() {
        return 25;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 8D);
        addProduct(dropletEarth, 2.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.220D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(Items.leather);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 5;
    }

    @Override
    public int getFishMealSize() {
        return 3;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return world.isDaytime() ? 25D : 0D;
    }
}

package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import static joshie.mariculture.core.lib.MCLib.glowstone;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishPike extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 5;
    }

    @Override
    public int getTemperatureTolerance() {
        return 20;
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
        return 30;
    }

    @Override
    public int getFertility() {
        return 5000;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 95;
    }
    
    @Override
    public boolean isValidWater(Block block) {
        return super.isValidWater(block) || Fluids.isHalfway(block);
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 2.725D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(glowstone);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 1;
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
        return 0.8F;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 15D;
    }
}

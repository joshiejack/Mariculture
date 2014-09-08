package joshie.mariculture.fishery.fish;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import static joshie.mariculture.api.core.Environment.Salinity.*;
import static joshie.mariculture.core.lib.MCLib.*;

public class FishRedshroom extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 10;
    }

    @Override
    public int getTemperatureTolerance() {
        return 10;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 1;
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 15;
    }

    @Override
    public int getFertility() {
        return 500;
    }
    
    @Override
    public int getWaterRequired() {
        return 80;
    }
    
    @Override
    public double getFishOilVolume() {
        return 0.650D;
    }
    
    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(Blocks.red_mushroom);
    }
    
    @Override
    public int getFishMealSize() {
        return 3;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 10D);
        addProduct(dropletPlant, 10D);
        addProduct(Blocks.red_mushroom, 7.5D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }
}

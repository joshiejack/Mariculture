package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletPlant;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class FishBrownshroom extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 10;
    }

    @Override
    public int getTemperatureTolerance() {
        return 4;
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
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 10;
    }

    @Override
    public int getFertility() {
        return 250;
    }

    @Override
    public int getWaterRequired() {
        return 50;
    }

    @Override
    public double getFishOilVolume() {
        return 1.355D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(Blocks.brown_mushroom);
    }

    @Override
    public int getFishMealSize() {
        return 3;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 7D);
        addProduct(dropletPlant, 5D);
        addProduct(dropletAqua, 6D);
        addProduct(Blocks.brown_mushroom, 10D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }
}

package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletPlant;
import static mariculture.core.lib.MCLib.dropletPoison;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishRedshroom extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 10;
    }

    @Override
    public int getTemperatureTolerance() {
        return 2;
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
        return 55;
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
        addProduct(dropletEarth, 5D);
        addProduct(dropletPlant, 3D);
        addProduct(dropletPoison, 7D);
        addProduct(Blocks.red_mushroom, 10D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }
    
    @Override
    public double getCatchChance(World world, int height) {
        return 5D;
    }
}

package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletRutile;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class FishRutile extends FishSpecies {
    @Override
    public Block getWater2() {
        return Blocks.lava;
    }
    
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
        return Salinity.SALINE;
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
        return 20;
    }

    @Override
    public int getFertility() {
        return 1000;
    }
    
    @Override
    public int getBaseProductivity() {
        return 0;
    }
    
    @Override
    public double getFishOilVolume() {
        return 0.0D;
    }
    
    @Override
    public int getFoodStat() {
        return -1;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletRutile, 11.5D);
        addProduct(dropletAqua, 18D);
        addProduct(dropletWater, 9D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }
}

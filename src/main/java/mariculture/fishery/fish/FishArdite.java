package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletArdite;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletNether;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class FishArdite extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 70;
    }

    @Override
    public int getTemperatureTolerance() {
        return 50;
    }

    @Override
    public Salinity getSalinityBase() {
        return Salinity.FRESH;
    }

    @Override
    public boolean isLavaFish() {
        return true;
    }

    @Override
    public Block getWater1() {
        return Blocks.lava;
    }

    @Override
    public Block getWater2() {
        return Blocks.lava;
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
        return 4000;
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
        addProduct(dropletArdite, 13D);
        addProduct(dropletNether, 20D);
        addProduct(dropletDestroy, 10D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }
    
    @Override
    public int getFishMealSize() {
        return 0;
    }
}

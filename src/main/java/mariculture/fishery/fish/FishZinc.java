package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletZinc;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class FishZinc extends FishSpecies {
    @Override
    public Block getWater2() {
        return Blocks.lava;
    }
    
    @Override
    public int getTemperatureBase() {
        return 15;
    }

    @Override
    public int getTemperatureTolerance() {
        return 25;
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
        return 8;
    }

    @Override
    public int getFertility() {
        return 1200;
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
        addProduct(dropletZinc, 17.5D);
        addProduct(dropletAqua, 7D);
        addProduct(dropletEarth, 11.5D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }
}

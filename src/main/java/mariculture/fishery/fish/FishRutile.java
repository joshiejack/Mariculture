package mariculture.fishery.fish;

import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletRutile;
import static mariculture.core.lib.MCLib.dropletWater;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;

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
        return 20;
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
    public void addFishProducts() {
        addProduct(dropletRutile, 5D);
        addProduct(dropletAqua, 7D);
        addProduct(dropletWater, 10D);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }
}

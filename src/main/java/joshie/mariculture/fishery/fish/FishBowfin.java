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

public class FishBowfin extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 9;
    }

    @Override
    public int getTemperatureTolerance() {
        return 6;
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
        return 10;
    }

    @Override
    public int getFertility() {
        return 2000;
    }

    @Override
    public int getFoodConsumption() {
        return 1;
    }

    @Override
    public int getWaterRequired() {
        return 100;
    }
   
    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 3.750D;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 15D;
    }
}

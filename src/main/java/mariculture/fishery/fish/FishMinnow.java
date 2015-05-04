package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class FishMinnow extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 10;
    }

    @Override
    public int getTemperatureTolerance() {
        return 12;
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
        return 5;
    }

    @Override
    public int getFertility() {
        return 1000;
    }

    @Override
    public int getBaseProductivity() {
        return 2;
    }
    
    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("custard");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 15D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.240D;
    }

    @Override
    public int getFishMealSize() {
        return 1;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        if (world.isDaytime()) return 33D;
        else return 5D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.isDaytime() && height > 70 ? 85D : 25D;
    }
}

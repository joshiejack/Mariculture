package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletPoison;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;

public class FishStickleback extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 3;
    }

    @Override
    public int getTemperatureTolerance() {
        return 5;
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
        return 3;
    }

    @Override
    public int getFertility() {
        return 500;
    }

    @Override
    public int getFoodConsumption() {
        return 1;
    }

    @Override
    public int getWaterRequired() {
        return 20;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 10D);
        addProduct(dropletWater, 7D);
        addProduct(dropletPoison, 3D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.075D;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 35D;
    }
}

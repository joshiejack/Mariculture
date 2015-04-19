package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletFrozen;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;

public class FishCod extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 11;
    }

    @Override
    public int getTemperatureTolerance() {
        return 15;
    }

    @Override
    public Salinity getSalinityBase() {
        return SALINE;
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
        return 5000;
    }

    @Override
    public int getWaterRequired() {
        return 20;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 2.5D);
        addProduct(dropletFrozen, 5D);
    }

    @Override
    public double getFishOilVolume() {
        return 2.500D;
    }

    @Override
    public int getFishMealSize() {
        return 5;
    }

    @Override
    public int getFoodStat() {
        return 1;
    }

    @Override
    public float getFoodSaturation() {
        return 0.15F;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 25D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return 65D;
    }
}

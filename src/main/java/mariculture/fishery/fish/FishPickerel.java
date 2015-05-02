package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletFrozen;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;

public class FishPickerel extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return -5;
    }

    @Override
    public int getTemperatureTolerance() {
        return 11;
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
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 8;
    }

    @Override
    public int getFertility() {
        return 2000;
    }

    @Override
    public int getWaterRequired() {
        return 125;
    }
   
    @Override
    public void addFishProducts() {
        addProduct(dropletFrozen, 12.5D);
        addProduct(dropletDestroy, 8.5D);
        addProduct(dropletAqua, 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.8D;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 20D;
    }
}

package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletPlant;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.init.Items;
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
        addProduct(dropletEarth, 7.5D);
        addProduct(dropletPlant, 12.5D);
        addProduct(dropletWater, 5D);
        addProduct(dropletDestroy, 8.5D);
        addProduct(dropletAqua, 3D);
        addProduct(dropletAir, 4D);
        addProduct(new ItemStack(Items.arrow), 13D);
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

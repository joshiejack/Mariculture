package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletFrozen;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishPike extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 5;
    }

    @Override
    public int getTemperatureTolerance() {
        return 13;
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
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 30;
    }

    @Override
    public int getFertility() {
        return 5000;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 120;
    }
    
    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("custard");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 7.5D);
        addProduct(dropletFrozen, 11.5D);
        addProduct(dropletDestroy, 17.5D);
        addProduct(new ItemStack(Items.flint), 15D);
    }

    @Override
    public double getFishOilVolume() {
        return 5.125D;
    }

    @Override
    public int getFoodStat() {
        return 5;
    }

    @Override
    public float getFoodSaturation() {
        return 0.8F;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 15D;
    }
}

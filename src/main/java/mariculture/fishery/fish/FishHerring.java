package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletFrozen;
import static mariculture.core.lib.MCLib.dropletRegen;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.redstone;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishHerring extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 14;
    }

    @Override
    public int getTemperatureTolerance() {
        return 6;
    }

    @Override
    public Salinity getSalinityBase() {
        return SALINE;
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
        return 16;
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
        return 60;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3D);
        addProduct(dropletRegen, 8.5D);
        addProduct(dropletFrozen, 12.5D);
        addProduct(redstone, 5D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.050D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(redstone);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 5;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 7D;
    }
}

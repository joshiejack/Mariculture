package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.SALINE;
import static joshie.mariculture.core.lib.MCLib.dropletFrozen;
import static joshie.mariculture.core.lib.MCLib.dropletRegen;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import static joshie.mariculture.core.lib.MCLib.redstone;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public class FishHerring extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 10, 18 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { SALINE };
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
        addProduct(dropletRegen, 0.5D);
        addProduct(dropletFrozen, 3D);
        addProduct(redstone, 1D);
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

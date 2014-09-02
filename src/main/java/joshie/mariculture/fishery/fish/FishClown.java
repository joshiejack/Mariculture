package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.BRACKISH;
import static joshie.mariculture.api.core.Environment.Salinity.SALINE;
import static joshie.mariculture.core.lib.ItemLib.dropletAqua;
import static joshie.mariculture.core.lib.ItemLib.dropletMagic;
import static joshie.mariculture.core.lib.ItemLib.dropletRegen;
import static joshie.mariculture.core.lib.ItemLib.dropletWater;
import static joshie.mariculture.core.lib.ItemLib.orangeDye;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public class FishClown extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 24, 27 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { SALINE, BRACKISH };
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 7;
    }

    @Override
    public int getFertility() {
        return 2000;
    }

    @Override
    public int getBaseProductivity() {
        return 0;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 200;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3D);
        addProduct(dropletAqua, 2D);
        addProduct(dropletRegen, 2.5D);
        addProduct(dropletMagic, 1.0D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.800D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return orangeDye;
    }

    @Override
    public int getLiquifiedProductChance() {
        return 3;
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isShallows(height) ? 20D : 0D;
    }
}

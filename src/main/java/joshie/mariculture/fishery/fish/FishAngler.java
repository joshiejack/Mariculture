package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.SALINE;
import static joshie.mariculture.core.lib.MCLib.dropletDestroy;
import static joshie.mariculture.core.lib.MCLib.dropletFlux;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
public class FishAngler extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 0, 12 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { SALINE };
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
    public int getFoodConsumption() {
        return 3;
    }

    @Override
    public int getWaterRequired() {
        return 110;
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir == ForgeDirection.DOWN ? 1 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3D);
        addProduct(dropletDestroy, 0.5D);
        addProduct(dropletFlux, 1.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 4.555D;
    }

    @Override
    public int getFishMealSize() {
        return 4;
    }

    @Override
    public int getFoodStat() {
        return 2;
    }

    @Override
    public float getFoodSaturation() {
        return 0.2F;
    }

    @Override
    public int getLightValue() {
        return 3;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isDeep(height) ? 15D : Height.isCave(height) ? 10D : 0D;
    }

    @Override
    public boolean hasGenderIcons() {
        return true;
    }
}

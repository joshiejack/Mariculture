package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishAngler extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 6;
    }

    @Override
    public int getTemperatureTolerance() {
        return 12;
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
        addProduct(dropletEarth, 5D);
        addProduct(dropletWater, 8.5D);
        addProduct(dropletDestroy, 7D);
        addProduct(dropletFlux, 12.5D);
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

package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.SALINE;
import static joshie.mariculture.core.lib.MCLib.dropletEarth;
import static joshie.mariculture.core.lib.MCLib.dropletPoison;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import static joshie.mariculture.core.lib.MCLib.fishMeal;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishStargazer extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 12;
    }

    @Override
    public int getTemperatureTolerance() {
        return 14;
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
        return 15;
    }

    @Override
    public int getFertility() {
        return 750;
    }

    @Override
    public int getWaterRequired() {
        return 85;
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir == ForgeDirection.DOWN ? 7 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 2D);
        addProduct(dropletPoison, 7.5D);
        addProduct(dropletEarth, 1.5D);
        addProduct(fishMeal, 15D);
    }

    @Override
    public double getFishOilVolume() {
        return 4.725D;
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity.worldObj.rand.nextInt(100) == 0) {
            entity.attackEntityFrom(DamageSource.wither, 1);
        }
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return !isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isDeep(height) ? 10D : 0D;
    }
}

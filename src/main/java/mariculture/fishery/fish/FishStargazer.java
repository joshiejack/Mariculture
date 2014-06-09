package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletEarth;
import static mariculture.core.lib.ItemLib.dropletPoison;
import static mariculture.core.lib.ItemLib.dropletWater;
import static mariculture.core.lib.ItemLib.fishMeal;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;

public class FishStargazer extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { -1, 5 };
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
    public boolean canWork(int time) {
        return !Time.isNoon(time);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(int height, int time) {
        return Height.isDeep(height) ? 10D : 0D;
    }

    @Override
    public double getCaughtAliveChance(int height, int time) {
        return height < 8 && Time.isMidnight(time) ? 5D : 0D;
    }
}

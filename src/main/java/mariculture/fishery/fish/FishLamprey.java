package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletPoison;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishLamprey extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 3;
    }

    @Override
    public int getTemperatureTolerance() {
        return 5;
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
        return 17;
    }

    @Override
    public int getFertility() {
        return 3000;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 350;
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir == ForgeDirection.DOWN ? 5 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletAqua, 7.5D);
        addProduct(dropletPoison, 6.5D);
        addProduct(dropletDestroy, 4.5D);
        addProduct(dropletEarth, 2D);
    }

    @Override
    public double getFishOilVolume() {
        return 2.250D;
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity.worldObj.rand.nextInt(100) == 0) {
            entity.attackEntityFrom(DamageSource.wither, 1);
        }
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isDeep(height) ? 15D : 0D;
    }
}

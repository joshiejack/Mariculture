package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletPoison;
import static mariculture.core.lib.MCLib.fishMeal;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.fishery.FisheryEventHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
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
        return 2;
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
        addProduct(dropletDestroy, 5.5D);
        addProduct(dropletPoison, 7.5D);
        addProduct(dropletEarth, 3.5D);
        addProduct(fishMeal, 15D);
    }

    @Override
    public double getFishOilVolume() {
        return 4.725D;
    }
    
    @Override
    public boolean hasLivingEffect() {
        return true;
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity.worldObj.rand.nextInt(100) == 0) {
            entity.attackEntityFrom(DamageSource.wither, 1);
        }
    }
    
    /*@Override
    public void onFishAdded(ItemStack stack, World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        for (CachedCoords coord: coords) {
            for (int x2 = - 16; x2 <= 16; x2++) {
                for (int y2 = -8; y2 <= 8; y2++) {
                    for (int z2 = - 16; z2 <= 16; z2++) {                      
                        FisheryEventHandler.invalid_spawns.get(world.provider.dimensionId).add(new CachedCoords(coord.x + x2, coord.y + y2, coord.z + z2));
                    }
                }
            }
        }
    }
    
    @Override
    public void onFishRemoved(ItemStack stack, World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        for (CachedCoords coord: coords) {
            for (int x2 = - 16; x2 <= 16; x2++) {
                for (int y2 = -8; y2 <= 8; y2++) {
                    for (int z2 = - 16; z2 <= 16; z2++) {
                        FisheryEventHandler.invalid_spawns.get(world.provider.dimensionId).remove(new CachedCoords(coord.x + x2, coord.y + y2, coord.z + z2));
                    }
                }
            }
        }
    }*/

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

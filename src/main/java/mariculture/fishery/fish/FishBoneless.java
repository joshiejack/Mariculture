package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.bone;
import static mariculture.core.lib.ItemLib.bonemeal;
import static mariculture.core.lib.ItemLib.dropletEarth;
import static mariculture.core.lib.ItemLib.skull;
import static mariculture.core.lib.ItemLib.witherSkull;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.util.Rand;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishBoneless extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { -5, 20 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH, BRACKISH };
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
        return 50;
    }

    @Override
    public int getFoodConsumption() {
        return 0;
    }

    @Override
    public boolean requiresFood() {
        return false;
    }

    @Override
    public int getWaterRequired() {
        return 150;
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN ? 1 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 1D);
        addProduct(bonemeal, 5D);
        addProduct(bone, 1.5D);
        addProduct(skull, 1D);
        addProduct(witherSkull, 0.1D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.0D;
    }

    @Override
    public int getLiquifiedProductChance() {
        return 1;
    }

    @Override
    public int getFishMealSize() {
        return 0;
    }

    @Override
    public int getFoodStat() {
        return 0;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (Rand.nextInt(500)) {
            EntitySkeleton skeleton = new EntitySkeleton(world);
            skeleton.setPosition(x, y, z);
            if (Rand.nextInt(5000)) {
                skeleton.setSkeletonType(1);
                skeleton.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
                skeleton.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
            }

            world.spawnEntityInWorld(skeleton);
        }
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return !isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isCave(height) ? 10D : !world.isDaytime() ? 10D : 0D;
    }
}

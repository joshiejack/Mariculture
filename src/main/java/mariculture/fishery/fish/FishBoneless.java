package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.bone;
import static mariculture.core.lib.MCLib.bonemeal;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.skull;
import static mariculture.core.lib.MCLib.witherSkull;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishBoneless extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 8;
    }

    @Override
    public int getTemperatureTolerance() {
        return 50;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
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
    public Block getWater2() {
        return Fluids.getFluidBlock("ender");
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN ? 1 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 20D);
        addProduct(bonemeal, 13D);
        addProduct(bone, 5D);
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
        return -1;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(10) == 0) {
            if (coords.size() > 0) {
                int count = getCount(EntitySkeleton.class, world, coords);
                if (count < 10) {
                    int coordinate = world.rand.nextInt(coords.size());
                    CachedCoords pos = coords.get(coordinate);
                    EntitySkeleton skeleton = new EntitySkeleton(world);
                    skeleton.tasks.addTask(4, new EntityAIArrowAttack(skeleton, 1.0D, 20, 60, 15.0F));
                    skeleton.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
                    skeleton.setPosition(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D);
                    world.spawnEntityInWorld(skeleton);
                }
            }
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

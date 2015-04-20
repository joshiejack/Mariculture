package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletRegen;
import static mariculture.core.lib.MCLib.dropletWater;

import java.util.ArrayList;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.util.Fluids;
import mariculture.lib.helpers.ItemHelper;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.ForgeDirection;

public class FishKoi extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 17;
    }

    @Override
    public int getTemperatureTolerance() {
        return 13;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 0;
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 50;
    }

    @Override
    public int getFertility() {
        return 350;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 300;
    }
    
    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("custard");
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN ? 2 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 5.5D);
        addProduct(dropletAqua, 11.5D);
        addProduct(dropletRegen, 17.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 5.450D;
    }

    @Override
    public int getFishMealSize() {
        return 7;
    }

    @Override
    public int getFoodStat() {
        return 4;
    }

    @Override
    public float getFoodSaturation() {
        return 0.6F;
    }

    @Override
    public int getFoodDuration() {
        return 48;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 2));
    }
    
    private static final String loots[] = new String[] { ChestGenHooks.BONUS_CHEST, ChestGenHooks.DUNGEON_CHEST, ChestGenHooks.MINESHAFT_CORRIDOR, ChestGenHooks.PYRAMID_DESERT_CHEST, ChestGenHooks.PYRAMID_JUNGLE_CHEST, ChestGenHooks.PYRAMID_JUNGLE_DISPENSER, ChestGenHooks.STRONGHOLD_CORRIDOR, ChestGenHooks.STRONGHOLD_CROSSING, ChestGenHooks.STRONGHOLD_LIBRARY, ChestGenHooks.VILLAGE_BLACKSMITH, WorldPlus.OCEAN_CHEST };

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(25) == 0) {
            if (coords.size() > 0) {
                int coordinate = world.rand.nextInt(coords.size());
                CachedCoords pos = coords.get(coordinate);
                try {
                    ItemStack loot = null;
                    WeightedRandomChestContent chest = (WeightedRandomChestContent) WeightedRandom.getRandomItem(world.rand, ChestGenHooks.getItems(loots[world.rand.nextInt(loots.length)], world.rand));
                    while (loot == null) {
                        ItemStack[] stacks = ChestGenHooks.generateStacks(world.rand, chest.theItemId, chest.theMinimumChanceToGenerateItem, chest.theMaximumChanceToGenerateItem);
                        if (stacks != null && stacks.length >= 1) {
                            loot = stacks[0].copy();
                        }
                    }

                    ItemHelper.spawnItem(world, pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D, loot);
                } catch (IllegalArgumentException e) {}
            }
        }
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return world.isDaytime() ? 9D : 2D;
    }
}

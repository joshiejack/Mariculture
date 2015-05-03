package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletRegen;
import static mariculture.core.lib.MCLib.dropletWater;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.helpers.PlayerHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishDamsel extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 24;
    }

    @Override
    public int getTemperatureTolerance() {
        return 7;
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
        return 5;
    }

    @Override
    public int getFertility() {
        return 500;
    }

    @Override
    public int getBaseProductivity() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 15;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3.5D);
        addProduct(dropletRegen, 2.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.900D;
    }

    @Override
    public int getFishMealSize() {
        return 1;
    }

    @Override
    public float getFoodSaturation() {
        return 0.1F;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 0));
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return world.isDaytime() ? 35D : 0D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.isDaytime() ? 75D : 0D;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(10) == 0) {
            ArrayList<EntityAnimal> entities = new ArrayList();
            for (CachedCoords coord : coords) {
                List list = world.getEntitiesWithinAABB(EntityAnimal.class, Blocks.stone.getCollisionBoundingBoxFromPool(world, coord.x, coord.y, coord.z));
                if (!list.isEmpty()) {
                    entities.addAll(list);
                }
            }

            /** Breed animals if there are less than 10 entities **/
            if (entities.size() < 10) {
                for (EntityAnimal animal : entities) {
                    if (!animal.isChild() && !animal.isInLove()) {
                        animal.func_146082_f(PlayerHelper.getFakePlayer(world));
                    }
                }
            }
        }
    }
}

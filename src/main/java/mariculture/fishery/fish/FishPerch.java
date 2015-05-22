package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletWater;

import java.util.ArrayList;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishPerch extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 15;
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
        return 0;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 10;
    }

    @Override
    public int getFertility() {
        return 2300;
    }

    @Override
    public int getWaterRequired() {
        return 25;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 5.5D);
        addProduct(dropletEarth, 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.220D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(Items.leather);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 5;
    }

    @Override
    public int getFishMealSize() {
        return 3;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(10) == 0) {
            if (coords.size() > 0) {
                int count = getCount(EntityBat.class, world, coords);
                if (count < 3) {
                    int coordinate = world.rand.nextInt(coords.size());
                    CachedCoords pos = coords.get(coordinate);
                    EntityBat entity = new EntityBat(world);
                    entity.setPosition(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D);
                    world.spawnEntityInWorld(entity);
                }
            }
        }
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return world.isDaytime() ? 25D : 0D;
    }
}

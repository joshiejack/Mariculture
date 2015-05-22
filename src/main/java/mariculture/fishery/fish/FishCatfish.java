package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletWater;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;

public class FishCatfish extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 32;
    }

    @Override
    public int getTemperatureTolerance() {
        return 12;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 2;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 22;
    }

    @Override
    public int getFertility() {
        return 4000;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 100;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 5.0D);
        addProduct(dropletAqua, 15.0D);
        addProduct(dropletEarth, 10.0D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.950D;
    }

    @Override
    public int getFishMealSize() {
        return 7;
    }

    @Override
    public float getFoodSaturation() {
        return 0.8F;
    }

    @Override
    public int getFoodDuration() {
        return 48;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(10) == 0) {
            if (coords.size() > 0) {
                int count = getCount(EntityOcelot.class, world, coords);
                if (count < 10) {
                    int coordinate = world.rand.nextInt(coords.size());
                    CachedCoords pos = coords.get(coordinate);
                    EntityOcelot entity = new EntityOcelot(world);
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
        return Height.isShallows(height) ? 25D : 0D;
    }
}

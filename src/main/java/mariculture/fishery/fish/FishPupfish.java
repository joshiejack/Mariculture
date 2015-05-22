package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletEarth;

import java.util.ArrayList;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;

public class FishPupfish extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 34;
    }

    @Override
    public int getTemperatureTolerance() {
        return 4;
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
        return 1;
    }

    @Override
    public int getFertility() {
        return 800;
    }

    @Override
    public int getFoodConsumption() {
        return 1;
    }

    @Override
    public int getWaterRequired() {
        return 25;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 5D);
        addProduct(dropletAir, 4D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.066D;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(10) == 0) {
            if (coords.size() > 0) {
                int count = getCount(EntityWolf.class, world, coords);
                if (count < 10) {
                    int coordinate = world.rand.nextInt(coords.size());
                    CachedCoords pos = coords.get(coordinate);
                    EntityWolf entity = new EntityWolf(world);
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
        return 35D;
    }
}

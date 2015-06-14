package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletMagic;
import static mariculture.core.lib.MCLib.dropletRegen;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.orangeDye;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishClown extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 25;
    }

    @Override
    public int getTemperatureTolerance() {
        return 18;
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
        return 7;
    }

    @Override
    public int getFertility() {
        return 2000;
    }

    @Override
    public int getBaseProductivity() {
        return 0;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 200;
    }
    
    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("custard");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3.5D);
        addProduct(dropletAqua, 7D);
        addProduct(dropletAir, 10D);
        addProduct(dropletRegen, 14.5D);
        addProduct(dropletMagic, 22.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.800D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return orangeDye;
    }

    @Override
    public int getLiquifiedProductChance() {
        return 3;
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isShallows(height) ? 20D : 0D;
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(10) == 0) {
            int count = getCount(EntityAnimal.class, world, coords);
            /** Breed animals if there are less than 10 entities **/
            if (count < 10) {
                int coordinate = world.rand.nextInt(coords.size());
                CachedCoords pos = coords.get(coordinate);
                EntityAnimal animal = null;
                if (world.rand.nextInt(5) == 0) {
                    animal = new EntityCow(world);
                } else if (world.rand.nextInt(5) == 0) {
                    animal = new EntitySheep(world);
                } else if (world.rand.nextInt(5) == 0) {
                    animal = new EntityPig(world);
                } if (world.rand.nextInt(6) == 0) {
                    animal = new EntityHorse(world);
                } else animal = new EntityChicken(world);

                animal.setPosition(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D);
                world.spawnEntityInWorld(animal);
            }
        }
    }
}

package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.blazePowder;
import static mariculture.core.lib.MCLib.blazeRod;
import static mariculture.core.lib.MCLib.dropletNether;

import java.util.ArrayList;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishBlaze extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 75;
    }

    @Override
    public int getTemperatureTolerance() {
        return 45;
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
    public boolean isLavaFish() {
        return true;
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
        return 350;
    }

    @Override
    public int getWaterRequired() {
        return 125;
    }
    
    @Override
    public Block getWater1() {
        return Blocks.lava;
    }
    
    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("custard");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletNether, 13.0D);
        addProduct(blazePowder, 20.0D);
        addProduct(blazeRod, 5.0D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.0D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(blazeRod);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 20;
    }

    @Override
    public int getFishMealSize() {
        return 2;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.setFire(7);
    }
    
    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(25) == 0) {
            if (coords.size() > 0) {
                int coordinate = world.rand.nextInt(coords.size());
                CachedCoords pos = coords.get(coordinate);
                EntityBlaze entity = new EntityBlaze(world);
                entity.setPosition(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D);
                world.spawnEntityInWorld(entity);
            }
        }
    }

    @Override
    public int getLightValue() {
        return 1;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return world.provider.isHellWorld;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 20;
    }
}

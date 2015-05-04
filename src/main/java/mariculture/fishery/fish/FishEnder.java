package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletEnder;
import static mariculture.core.lib.MCLib.enderPearl;

import java.util.ArrayList;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class FishEnder extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 3;
    }

    @Override
    public int getTemperatureTolerance() {
        return 17;
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
        return 25;
    }

    @Override
    public int getFertility() {
        return 75;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 140;
    }
    
    @Override
    public Block getWater1() {
        return Fluids.getFluidBlock("ender");
    }
    
    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("ender");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEnder, 20D);
        addProduct(enderPearl, 5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.450D;
    }

    @Override
    public int getFishMealSize() {
        return 2;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        if (!world.isRemote) {
            world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
            int x = (int) (player.posX + world.rand.nextInt(64) - 32);
            int z = (int) (player.posZ + world.rand.nextInt(64) - 32);
            if (BlockHelper.chunkExists(world, x, z)) {
                int y = world.getTopSolidOrLiquidBlock(x, z);

                if (world.getBlock(x, y, z).getMaterial() != Material.lava) {
                    world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F);
                    player.setPositionAndUpdate(x, y, z);
                    world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
                }
            }
        }
    }
    
    @Override
    public boolean hasLivingEffect() {
        return true;
    }
    
    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            if (entity.worldObj.rand.nextInt(10) == 0) {
                onConsumed(entity.worldObj, (EntityPlayer) entity);
            }
        }
    }
    
    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(25) == 0) {
            if (coords.size() > 0) {
                int coordinate = world.rand.nextInt(coords.size());
                CachedCoords pos = coords.get(coordinate);
                EntityEnderman entity = new EntityEnderman(world);
                entity.setPosition(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D);
                world.spawnEntityInWorld(entity);
            }
        }
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return !isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return world.provider.dimensionId == 1;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 66D;
    }
}

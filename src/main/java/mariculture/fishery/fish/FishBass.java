package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletPlant;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.gunpowder;

import java.util.Random;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.EntityBass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishBass extends FishSpecies {
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
        return 2;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 16;
    }

    @Override
    public int getFertility() {
        return 2000;
    }

    @Override
    public int getWaterRequired() {
        return 45;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 4D);
        addProduct(dropletEarth, 5.0D);
        addProduct(dropletDestroy, 7D);
        addProduct(dropletPlant, 6D);
        addProduct(gunpowder, 10D);
    }

    @Override
    public double getFishOilVolume() {
        return 4.325D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(gunpowder);
    }

    @Override
    public int getFishMealSize() {
        return 5;
    }

    @Override
    public int getFoodStat() {
        return 1;
    }

    @Override
    public float getFoodSaturation() {
        return 0.1F;
    }

    @Override
    public int getFoodDuration() {
        return 1;
    }

    @Override
    public boolean canAlwaysEat() {
        return true;
    }

    @Override
    public ItemStack onRightClick(World world, EntityPlayer player, ItemStack stack, Random rand) {
        if (!player.capabilities.isCreativeMode) {
            --stack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityBass(world, player));
        }

        return stack;
    }
    
    @Override
    public boolean hasLivingEffect() {
        return true;
    }
    
    @Override
    public void affectLiving(EntityLivingBase entity) {
        entity.worldObj.createExplosion(entity, entity.posX, entity.posY + 1D, entity.posZ, 1, true);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isOverground(height) ? 15D : 5D;
    }
}

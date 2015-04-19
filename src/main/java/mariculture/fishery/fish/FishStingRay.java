package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.core.lib.MCLib.dropletPoison;

import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishStingRay extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 24;
    }

    @Override
    public int getTemperatureTolerance() {
        return 5;
    }

    @Override
    public Salinity getSalinityBase() {
        return BRACKISH;
    }

    @Override
    public int getSalinityTolerance() {
        return 1;
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
        return 1;
    }

    @Override
    public int getWaterRequired() {
        return 20;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletPoison, 10D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.725D;
    }

    @Override
    public int getFishMealSize() {
        return 3;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.confusion.id, 800, 1));
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            Random rand = new Random();
            int difficulty = player.worldObj.difficultySetting.ordinal();
            if (difficulty > 0) {
                int chance = 40 - difficulty * 10;
                if (rand.nextInt(chance) == 0) {
                    player.addPotionEffect(new PotionEffect(Potion.poison.id, difficulty * 100, 1, true));
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

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.isDaytime() ? 60D : 35D;
    }
}

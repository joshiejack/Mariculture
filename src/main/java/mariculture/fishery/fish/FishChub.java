package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletRegen;

import java.util.Random;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishChub extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 35;
    }

    @Override
    public int getTemperatureTolerance() {
        return 20;
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
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 25;
    }

    @Override
    public int getFertility() {
        return 7500;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 85;
    }
   
    @Override
    public void addFishProducts() {
        addProduct(dropletEarth, 10D);
        addProduct(dropletRegen, 3D);
        addProduct(dropletAir, 7D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.3D;
    }
    
    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.hunger.id, 800, 1));
    }
    
    @Override
    public int getFoodStat() {
        return 12;
    }
    
    @Override
    public float getFoodSaturation() {
        return 0.6F;
    }
    
    @Override
    public boolean hasLivingEffect() {
        return true;
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
                    player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, difficulty * 100, 1, true));
                    player.getFoodStats().addStats(1, 0.1F);
                }
            }
        }
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 15D;
    }
}

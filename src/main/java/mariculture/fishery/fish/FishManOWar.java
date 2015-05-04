package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletPoison;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishManOWar extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 32;
    }

    @Override
    public int getTemperatureTolerance() {
        return 25;
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
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 1;
    }

    @Override
    public int getFertility() {
        return 90;
    }

    @Override
    public int getFoodConsumption() {
        return 0;
    }

    @Override
    public boolean requiresFood() {
        return false;
    }

    @Override
    public int getWaterRequired() {
        return 130;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 5D);
        addProduct(dropletPoison, 11.5D);
        addProduct(dropletDestroy, 17.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0D;
    }

    @Override
    public int getFishMealSize() {
        return 0;
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
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.harm.id, 5, 1));
        player.addPotionEffect(new PotionEffect(Potion.confusion.id, 1000, 1));
    }
    
    @Override
    public boolean hasLivingEffect() {
        return true;
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 250, 1));
        entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 500, 0));
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }
}

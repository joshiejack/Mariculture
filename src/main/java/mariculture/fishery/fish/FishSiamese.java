package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.MaricultureDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishSiamese extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 24;
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
        return 1;
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 2;
    }

    @Override
    public int getFertility() {
        return 40;
    }

    @Override
    public int getWaterRequired() {
        return 125;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 4D);
        addProduct(dropletDestroy, 3D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.012D;
    }

    @Override
    public int getFishMealSize() {
        return 1;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        if (world.isDaytime()) {
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 0));
        }
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) return;
        else {
            entity.attackEntityFrom(MaricultureDamage.siamese, 3);
        }
    }

    @Override
    public double getCatchChance(World world, int height) {
        return world.isDaytime() ? 33D : 3D;
    }
}

package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.dropletDestroy;
import static mariculture.core.lib.ItemLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
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
    public int[] setSuitableTemperature() {
        return new int[] { 20, 27 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH, BRACKISH };
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
    public double getCatchChance(World world, int height, int time) {
        return !Time.isMidnight(time) ? 33D : 3D;
    }
}

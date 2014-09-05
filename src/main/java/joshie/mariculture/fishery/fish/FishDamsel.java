package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletRegen;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
public class FishDamsel extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 22, 27 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH };
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 5;
    }

    @Override
    public int getFertility() {
        return 500;
    }

    @Override
    public int getBaseProductivity() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 15;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3.5D);
        addProduct(dropletRegen, 0.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.900D;
    }

    @Override
    public int getFishMealSize() {
        return 1;
    }

    @Override
    public float getFoodSaturation() {
        return 0.1F;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 0));
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return world.isDaytime() ? 35D : 0D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.isDaytime() ? 75D : 0D;
    }
}

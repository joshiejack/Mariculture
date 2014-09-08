package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletEnder;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishNight extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 28;
    }

    @Override
    public int getTemperatureTolerance() {
        return 38;
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
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 10;
    }

    @Override
    public int getFertility() {
        return 256;
    }

    @Override
    public int getWaterRequired() {
        return 50;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletEnder, 5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.333D;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        if (!world.isDaytime()) {
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1800, 0));
        }
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return !isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return !world.provider.isHellWorld;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return world.provider.dimensionId == 1 ? 55D : Height.isCave(height) ? 5D : !world.isDaytime() ? 35D : 0D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.provider.dimensionId == 1 ? 65D : !world.isDaytime() ? 44D : 0D;
    }
}

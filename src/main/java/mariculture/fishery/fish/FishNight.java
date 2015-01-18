package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletEnder;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
    public Block getWater2() {
        return Fluids.getFluidBlock("ender");
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

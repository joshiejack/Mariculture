package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishTetra extends FishSpecies {
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
        return 0;
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
        return 130;
    }

    @Override
    public int getBaseProductivity() {
        return 2;
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return -1;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 2.5D);
        addProduct(dropletFlux, 5D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.025D;
    }

    @Override
    public int getFishMealSize() {
        return 1;
    }

    @Override
    public float getFoodSaturation() {
        return 0.05F;
    }

    @Override
    public int getFoodDuration() {
        return 8;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 160, 0));
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 45D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return world.isDaytime() ? 65D : 35D;
    }
}

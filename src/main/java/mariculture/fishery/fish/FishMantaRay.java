package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletRegen;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishMantaRay extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 30;
    }

    @Override
    public int getTemperatureTolerance() {
        return 21;
    }

    @Override
    public Salinity getSalinityBase() {
        return SALINE;
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
        return 20;
    }

    @Override
    public int getFertility() {
        return 200;
    }

    @Override
    public int getFoodConsumption() {
        return 2;
    }

    @Override
    public int getWaterRequired() {
        return 115;
    }

    @Override
    public Block getWater2() {
        return Blocks.lava;
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return 1;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3.5D);
        addProduct(dropletRegen, 7.5D);
        addProduct(dropletAir, 5.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 6.130D;
    }

    @Override
    public int getFishMealSize() {
        return 9;
    }

    @Override
    public int getFoodStat() {
        return 2;
    }

    @Override
    public float getFoodSaturation() {
        return 0.45F;
    }

    @Override
    public int getFoodDuration() {
        return 64;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 1));
    }
    
    @Override
    public boolean hasLivingEffect() {
        return true;
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 33, 0, true));
        entity.heal(5F);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 10D;
    }
}

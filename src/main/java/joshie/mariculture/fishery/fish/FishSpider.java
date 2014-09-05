package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.BRACKISH;
import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import static joshie.mariculture.core.lib.MCLib.fermentedEye;
import static joshie.mariculture.core.lib.MCLib.spiderEye;
import static joshie.mariculture.core.lib.MCLib.string;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
public class FishSpider extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { -5, 20 };
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
        return 15;
    }

    @Override
    public int getFertility() {
        return 4500;
    }

    @Override
    public int getWaterRequired() {
        return 30;
    }

    @Override
    public int getAreaOfEffectBonus(ForgeDirection dir) {
        return dir == ForgeDirection.DOWN ? 5 : 0;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 2D);
        addProduct(string, 4D);
        addProduct(spiderEye, 2.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.350D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(fermentedEye);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 12;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1, true));
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 150, 1, true));
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
    public double getCatchChance(World world, int height) {
        return Height.isCave(height) || world.isDaytime() ? 25D : 0D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return Height.isCave(height) ? 75D : 0D;
    }
}

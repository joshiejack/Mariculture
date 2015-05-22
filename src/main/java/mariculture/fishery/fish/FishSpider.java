package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.fermentedEye;
import static mariculture.core.lib.MCLib.spiderEye;
import static mariculture.core.lib.MCLib.string;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishSpider extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 8;
    }

    @Override
    public int getTemperatureTolerance() {
        return 3;
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
        addProduct(dropletWater, 2.5D);
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
    public boolean hasLivingEffect() {
        return true;
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 150, 1, true));
        }
    }

    @Override
    public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
        if (world.rand.nextInt(10) == 0) {
            if (coords.size() > 0) {
                int count = getCount(EntitySpider.class, world, coords);
                if (count < 10) {
                    int coordinate = world.rand.nextInt(coords.size());
                    CachedCoords pos = coords.get(coordinate);
                    EntitySpider entity = new EntitySpider(world);
                    entity.setPosition(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D);
                    world.spawnEntityInWorld(entity);
                }
            }
        }
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return !isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isCave(height) || world.isDaytime() ? 25D : 0D;
    }
}

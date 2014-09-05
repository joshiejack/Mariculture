package joshie.mariculture.fishery.fish;
import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletAqua;
import static joshie.mariculture.core.lib.MCLib.dropletDestroy;
import static joshie.mariculture.core.lib.MCLib.dropletEarth;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import static joshie.mariculture.core.lib.MCLib.rottenFlesh;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.lib.MaricultureDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishPiranha extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 21, 28 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH };
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 10;
    }

    @Override
    public int getFertility() {
        return 5000;
    }

    @Override
    public int getFoodConsumption() {
        return 3;
    }

    @Override
    public int getWaterRequired() {
        return 250;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3D);
        addProduct(dropletAqua, 2.5D);
        addProduct(dropletDestroy, 5D);
        addProduct(dropletEarth, 4.0D);
        addProduct(rottenFlesh, 15.0D);
    }

    @Override
    public double getFishOilVolume() {
        return 3.500D;
    }

    @Override
    public int getFishMealSize() {
        return 3;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.harm.id, 20, 0));
    }

    @Override
    public void affectLiving(EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.worldObj.difficultySetting.ordinal() > 0) {
                player.attackEntityFrom(MaricultureDamage.piranha, player.worldObj.difficultySetting.ordinal());
            }
        } else {
            entity.attackEntityFrom(MaricultureDamage.piranha, 2);
        }
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return !world.isDaytime() ? 35D : 3D;
    }
}

package joshie.mariculture.fishery.fish;
import static joshie.mariculture.api.core.Environment.Salinity.BRACKISH;
import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletDestroy;
import static joshie.mariculture.core.lib.MCLib.dropletPoison;
import static joshie.mariculture.core.lib.MCLib.dropletWater;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

public class FishPuffer extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 20, 45 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { BRACKISH, FRESH };
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
        return 3;
    }

    @Override
    public int getFoodConsumption() {
        return 3;
    }

    @Override
    public int getWaterRequired() {
        return 115;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 3D);
        addProduct(dropletPoison, 7.5D);
        addProduct(dropletDestroy, 1.5D);
    }

    @Override
    public String getPotionEffect(ItemStack stack) {
        return PotionHelper.field_151423_m;
    }

    @Override
    public double getFishOilVolume() {
        return 4.625D;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
        player.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
        player.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.DIRE;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 17D;
    }
}

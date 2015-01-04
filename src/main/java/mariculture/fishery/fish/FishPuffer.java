package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.core.lib.MCLib.dropletDestroy;
import static mariculture.core.lib.MCLib.dropletPoison;
import static mariculture.core.lib.MCLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

public class FishPuffer extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 33;
    }

    @Override
    public int getTemperatureTolerance() {
        return 16;
    }

    @Override
    public Salinity getSalinityBase() {
        return BRACKISH;
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
    public boolean isValidWater(Block block) {
        return super.isValidWater(block) || block == Blocks.lava;
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

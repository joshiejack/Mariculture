package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.blazePowder;
import static joshie.mariculture.core.lib.MCLib.blazeRod;
import static joshie.mariculture.core.lib.MCLib.dropletNether;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishBlaze extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 75;
    }

    @Override
    public int getTemperatureTolerance() {
        return 25;
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
    public boolean isLavaFish() {
        return true;
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 15;
    }

    @Override
    public int getFertility() {
        return 350;
    }

    @Override
    public int getWaterRequired() {
        return 125;
    }
    
    @Override
    public boolean isValidWater(Block block) {
        return block == Blocks.lava;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletNether, 10D);
        addProduct(blazePowder, 5.0D);
    }

    @Override
    public double getFishOilVolume() {
        return 1.0D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(blazeRod);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 20;
    }

    @Override
    public int getFishMealSize() {
        return 2;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.setFire(7);
    }

    @Override
    public int getLightValue() {
        return 1;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.SUPER;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return world.provider.isHellWorld;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 20;
    }
}

package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletNether;
import static mariculture.core.lib.MCLib.glowstone;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.util.Fluids;
import mariculture.lib.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishGlow extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 57;
    }

    @Override
    public int getTemperatureTolerance() {
        return 33;
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
        return 20;
    }

    @Override
    public int getFertility() {
        return 450;
    }

    @Override
    public int getFoodConsumption() {
        return 3;
    }

    @Override
    public int getWaterRequired() {
        return 120;
    }
    
    @Override
    public boolean isValidWater(Block block) {
        return block == Blocks.lava || Fluids.isHalfway(block);
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletNether, 7.5D);
        addProduct(glowstone, 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 2.725D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(glowstone);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 1;
    }

    @Override
    public int getFishMealSize() {
        return 5;
    }

    @Override
    public int getFoodStat() {
        return 3;
    }

    @Override
    public float getFoodSaturation() {
        return 0.4F;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        ItemHelper.addToPlayerInventory(player, new ItemStack(glowstone));
    }

    @Override
    public int getLightValue() {
        return 15;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return world.provider.isHellWorld;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isHigh(height) ? 50D : 25D;
    }
}

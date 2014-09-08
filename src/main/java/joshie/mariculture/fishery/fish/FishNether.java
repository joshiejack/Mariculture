package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.MCLib.dropletNether;
import static joshie.mariculture.core.lib.MCLib.netherWart;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishNether extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 72;
    }

    @Override
    public int getTemperatureTolerance() {
        return 28;
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
        return true;
    }

    @Override
    public int getLifeSpan() {
        return 6;
    }

    @Override
    public int getFertility() {
        return 666;
    }

    @Override
    public int getWaterRequired() {
        return 55;
    }
    
    @Override
    public boolean isValidWater(Block block) {
        return block == Blocks.lava;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletNether, 5D);
        addProduct(netherWart, 1D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.666D;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 120, 0));
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return world.provider.isHellWorld;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 45D;
    }

    @Override
    public double getCaughtAliveChance(World world, int height) {
        return Height.isShallows(height) ? 65D : 25D;
    }
}

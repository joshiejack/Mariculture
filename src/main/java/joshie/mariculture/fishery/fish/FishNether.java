package joshie.mariculture.fishery.fish;

import static joshie.mariculture.api.core.Environment.Salinity.FRESH;
import static joshie.mariculture.core.lib.ItemLib.dropletNether;
import static joshie.mariculture.core.lib.ItemLib.netherWart;
import joshie.mariculture.api.core.Environment.Height;
import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
public class FishNether extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { 45, 100 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH };
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

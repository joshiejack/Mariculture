package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.lapis;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishTang extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 33;
    }

    @Override
    public int getTemperatureTolerance() {
        return 10;
    }

    @Override
    public Salinity getSalinityBase() {
        return SALINE;
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
        return 9;
    }

    @Override
    public int getFertility() {
        return 4000;
    }

    @Override
    public int getWaterRequired() {
        return 25;
    }
    
    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("custard");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 7D);
        addProduct(dropletAqua, 10D);
        addProduct(lapis, 2.0D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.725D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return lapis;
    }

    @Override
    public int getLiquifiedProductChance() {
        return 8;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(Potion.jump.id, 80, 0));
    }

    @Override
    public boolean canWorkAtThisTime(boolean isDay) {
        return isDay;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return Height.isShallows(height) ? 25D : 0D;
    }
}

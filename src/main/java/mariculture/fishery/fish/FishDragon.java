package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dragonEgg;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletEnder;
import static mariculture.core.lib.MCLib.enderPearl;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.util.Fluids;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishDragon extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 49;
    }

    @Override
    public int getTemperatureTolerance() {
        return 39;
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
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 60;
    }

    @Override
    public int getFertility() {
        return 10;
    }

    @Override
    public int getBaseProductivity() {
        return 0;
    }

    @Override
    public int getFoodConsumption() {
        return 3;
    }

    @Override
    public int getWaterRequired() {
        return 400;
    }
    
    @Override
    public Block getWater1() {
        return Fluids.getFluidBlock("ender");
    }
    
    @Override
    public Block getWater2() {
        return Fluids.getFluidBlock("ender");
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletAir, 20D);
        addProduct(dropletEnder, 22.5D);
        addProduct(enderPearl, 20D);
        addProduct(dragonEgg, 0.1D);
    }

    @Override
    public double getFishOilVolume() {
        return 7.5D;
    }

    @Override
    public int getFishMealSize() {
        return 13;
    }

    @Override
    public int getFoodStat() {
        return 3;
    }

    @Override
    public float getFoodSaturation() {
        return 0.65F;
    }

    @Override
    public int getFoodDuration() {
        return 64;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.getFoodStats().addStats(12, 0.5F);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2000, 0));
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.FLUX;
    }

    @Override
    public boolean isWorldCorrect(World world) {
        return world.provider.dimensionId == 1;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 10D;
    }
}

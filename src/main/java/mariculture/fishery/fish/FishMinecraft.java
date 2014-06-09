package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.cookedFish;
import static mariculture.core.lib.ItemLib.dropletAqua;
import static mariculture.core.lib.ItemLib.dropletMagic;
import static mariculture.core.lib.ItemLib.dropletWater;
import static mariculture.core.lib.ItemLib.vanillaFish;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishMinecraft extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { -1, 45 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH, BRACKISH, SALINE };
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 5;
    }

    @Override
    public int getFertility() {
        return 30;
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
        return 320;
    }

    @Override
    public void addFishProducts() {
        addProduct(dropletWater, 50D);
        addProduct(dropletAqua, 25D);
        addProduct(dropletMagic, 0.5D);
        addProduct(new ItemStack(vanillaFish, 1, getID()), 15D);
    }

    @Override
    public double getFishOilVolume() {
        return 0.200D;
    }

    @Override
    public ItemStack getLiquifiedProduct() {
        return new ItemStack(cookedFish);
    }

    @Override
    public int getLiquifiedProductChance() {
        return 1;
    }

    @Override
    public int getFishMealSize() {
        return 2;
    }

    @Override
    public int getFoodStat() {
        return 2;
    }

    @Override
    public float getFoodSaturation() {
        return 0.45F;
    }

    @Override
    public void onConsumed(World world, EntityPlayer player) {
        player.addExperience(8);
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public int getCatchChance() {
        return 5;
    }

    @Override
    public double getCaughtAliveChance(int height, int time) {
        return Time.isNoon(time) && height >= 110 && height <= 120 ? 5D : 0D;
    }

    @Override
    public double getCaughtAliveChance() {
        return 0D;
    }
}

package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.cookedFish;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletExperience;
import static mariculture.core.lib.MCLib.dropletMagic;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.vanillaFish;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishMinecraft extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 22;
    }

    @Override
    public int getTemperatureTolerance() {
        return 22;
    }

    @Override
    public Salinity getSalinityBase() {
        return FRESH;
    }

    @Override
    public int getSalinityTolerance() {
        return 2;
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
        addProduct(dropletExperience, 25D);
        addProduct(dropletWater, 5D);
        addProduct(dropletAqua, 8D);
        addProduct(dropletMagic, 16D);
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
    public void affectLiving(EntityLivingBase entity) {
    	if (entity instanceof EntityPlayer) {
    		((EntityPlayer)entity).addExperience(1);
    	}
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 5D;
    }
}

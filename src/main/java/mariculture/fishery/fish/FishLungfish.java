package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletPlant;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishLungfish extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 13;
    }

    @Override
    public int getTemperatureTolerance() {
        return 9;
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
    public boolean isDominant() {
        return false;
    }

    @Override
    public int getLifeSpan() {
        return 100;
    }

    @Override
    public int getFertility() {
        return 300;
    }

    @Override
    public int getFoodConsumption() {
        return 1;
    }

    @Override
    public int getWaterRequired() {
        return 50;
    }
    
    @Override
    public int getOnLandLifespan(FishSpecies fishy) {
        return 1200;
    }
   
    @Override
    public void addFishProducts() {
        addProduct(dropletAir, 15D);
        addProduct(dropletEarth, 10D);
        addProduct(dropletPlant, 7.5D);
    }

    @Override
    public double getFishOilVolume() {
        return 4.3D;
    }
    
    @Override
    public void affectLiving(EntityLivingBase entity) {
        entity.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 1, 500));
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.GOOD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 10D;
    }
}

package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.MCLib.blueDye;
import static mariculture.core.lib.MCLib.brownDye;
import static mariculture.core.lib.MCLib.cyanDye;
import static mariculture.core.lib.MCLib.greenDye;
import static mariculture.core.lib.MCLib.lightBlueDye;
import static mariculture.core.lib.MCLib.limeDye;
import static mariculture.core.lib.MCLib.magentaDye;
import static mariculture.core.lib.MCLib.orangeDye;
import static mariculture.core.lib.MCLib.pinkDye;
import static mariculture.core.lib.MCLib.purpleDye;
import static mariculture.core.lib.MCLib.redDye;
import static mariculture.core.lib.MCLib.yellowDye;

import java.util.UUID;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

public class FishTrout extends FishSpecies {
    @Override
    public int getTemperatureBase() {
        return 17;
    }

    @Override
    public int getTemperatureTolerance() {
        return 5;
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
        return 8;
    }

    @Override
    public int getFertility() {
        return 200;
    }

    @Override
    public int getWaterRequired() {
        return 40;
    }

    @Override
    public void addFishProducts() {
        addProduct(brownDye, 1.75D);
        addProduct(redDye, 5D);
        addProduct(orangeDye, 4D);
        addProduct(yellowDye, 5D);
        addProduct(limeDye, 1.75D);
        addProduct(greenDye, 3.5D);
        addProduct(cyanDye, 1.5D);
        addProduct(blueDye, 3.5D);
        addProduct(lightBlueDye, 1.75D);
        addProduct(magentaDye, 1.5D);
        addProduct(purpleDye, 2.5D);
        addProduct(pinkDye, 1D);
    }

    @Override
    public boolean destroyOnAttack() {
        return true;
    }

    @Override
    public Multimap getModifiers(UUID uuid, Multimap map) {
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(uuid, "Weapon modifier", 10.0F, 0));
        return map;
    }

    @Override
    public double getFishOilVolume() {
        return 3.600D;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height) {
        return 25D;
    }
}

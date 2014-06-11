package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.ItemLib.blueDye;
import static mariculture.core.lib.ItemLib.greenDye;
import static mariculture.core.lib.ItemLib.orangeDye;
import static mariculture.core.lib.ItemLib.pinkDye;
import static mariculture.core.lib.ItemLib.purpleDye;
import static mariculture.core.lib.ItemLib.redDye;
import static mariculture.core.lib.ItemLib.yellowDye;

import java.util.UUID;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

public class FishTrout extends FishSpecies {
    @Override
    public int[] setSuitableTemperature() {
        return new int[] { -1, 35 };
    }

    @Override
    public Salinity[] setSuitableSalinity() {
        return new Salinity[] { FRESH };
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
        addProduct(redDye, 5D);
        addProduct(orangeDye, 4D);
        addProduct(yellowDye, 5D);
        addProduct(greenDye, 3.5D);
        addProduct(blueDye, 3.5D);
        addProduct(purpleDye, 2.5D);
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
    public ItemStack getLiquifiedProduct() {
        return pinkDye;
    }

    @Override
    public RodType getRodNeeded() {
        return RodType.OLD;
    }

    @Override
    public double getCatchChance(World world, int height, int time) {
        return 25D;
    }
}

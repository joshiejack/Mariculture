package joshie.mariculture.magic.jewelry.parts;

import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.PearlColor;
import joshie.mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class MaterialPearlWhite extends JewelryMaterial {
    @Override
    public String getColor() {
        return joshie.lib.util.Text.WHITE;
    }

    @Override
    public int onAttack(LivingAttackEvent event, EntityPlayer player) {
        if (player.worldObj.isDaytime()) if (event.entity != null) {
            event.entity.attackEntityFrom(DamageSource.magic, event.ammount * 1.25F);
            return 5;
        } else return 1;

        return 0;
    }

    @Override
    public int getExtraEnchantments(JewelryType type) {
        return 0;
    }

    @Override
    public int getMaximumEnchantmentLevel(JewelryType type) {
        return 4;
    }

    @Override
    public float getRepairModifier(JewelryType type) {
        return 1.75F;
    }

    @Override
    public float getHitsModifier(JewelryType type) {
        return 3.0F;
    }

    @Override
    public float getDurabilityModifier(JewelryType type) {
        return 0.75F;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Core.pearls, 1, PearlColor.WHITE);
    }
}

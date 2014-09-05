package joshie.maritech.handlers;

import joshie.mariculture.core.helpers.PlayerHelper;
import joshie.mariculture.core.lib.ArmorSlot;
import joshie.maritech.extensions.modules.ExtensionDiving;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ScubaTank {
    public static void activate(EntityPlayer player) {
        ItemStack scuba = player.inventory.armorItemInSlot(ArmorSlot.TOP);
        if (scuba.getItemDamage() < scuba.getMaxDamage()) {
            player.setAir(300);
        }
    }

    public static void damage(ItemStack stack, EntityPlayer player) {
        if (stack.getItemDamage() < stack.getMaxDamage() && player.worldObj.getWorldTime() % 144 == 0) {
            stack.damageItem(1, player);
        }
    }
}

package maritech.handlers;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import maritech.extensions.modules.ExtensionDiving;
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
        if (stack.getItemDamage() < stack.getMaxDamage() && player.worldObj.getTotalWorldTime() % 150 == 0) {
            stack.damageItem(1, player);
        }
    }
}

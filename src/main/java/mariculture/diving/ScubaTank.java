package mariculture.diving;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ScubaTank {
    public static void init(EntityPlayer player) {
        if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.scubaMask) && PlayerHelper.hasArmor(player, ArmorSlot.TOP, Diving.scubaTank)) if (player.isInsideOfMaterial(Material.water)) {
            activate(player);
            damage(player.inventory.armorItemInSlot(ArmorSlot.TOP), player);
        }
    }

    private static void activate(EntityPlayer player) {
        ItemStack scuba = player.inventory.armorItemInSlot(ArmorSlot.TOP);
        if (scuba.getItemDamage() < scuba.getMaxDamage()) {
            player.setAir(300);
        }
    }

    private static void damage(ItemStack stack, EntityPlayer player) {
        if (stack.getItemDamage() < stack.getMaxDamage() && player.worldObj.getWorldTime() % 144 == 0) {
            stack.damageItem(1, player);
        }
    }
}

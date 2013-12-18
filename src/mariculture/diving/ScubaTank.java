package mariculture.diving;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Extra;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ScubaTank {

	public static void init(EntityPlayer player) {
		if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.scubaMask)
				&& PlayerHelper.hasArmor(player, ArmorSlot.TOP, Diving.scubaTank)) {
			if (player.isInsideOfMaterial(Material.water)) {
				activate(player);
				damage(player.inventory.armorItemInSlot(ArmorSlot.TOP), player);
			}
		} else if(player.getAir() > 0) {
			if(Extra.HARDCORE_DIVING > 0) {
				if(player.isInsideOfMaterial(Material.water)) {
					player.setAir(player.getAir() - Extra.HARDCORE_DIVING);
				}
			}
		}
	}

	private static void activate(EntityPlayer player) {
		ItemStack scuba = player.inventory.armorItemInSlot(ArmorSlot.TOP);
		if (scuba.getItemDamage() < scuba.getMaxDamage()) {
			player.setAir(300);
		}
	}

	private static void damage(final ItemStack stack, final EntityPlayer player) {
		if (stack.getItemDamage() < stack.getMaxDamage() && player.worldObj.getWorldTime() % 144 == 0) {
			stack.damageItem(1, player);
		}
	}
}

package mariculture.core.helpers;

import mariculture.api.core.MaricultureHandlers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantHelper {
	public static int getEnchantStrength(Enchantment enchant, EntityPlayer player) {
		if(!exists(enchant)) {
			return 0;
		}
		
		return MaricultureHandlers.mirror.getEnchantmentStrength(player, enchant.effectId);
	}
	
	public static boolean hasEnchantment(Enchantment enchant, EntityPlayer player) {		
		return getEnchantStrength(enchant, player) > 0;
	}

	public static void damageItems(Enchantment enchant, EntityPlayer player, int dmg) {
		if(!exists(enchant)) {
			return;
		}
		
		MaricultureHandlers.mirror.damageItemsWithEnchantment(player, enchant.effectId, dmg);
	}

	public static boolean exists(Enchantment enchant) {
		if(enchant == null) {
			return false;
		}
		
		return enchant.effectId > 0;
	}
	
	public static int getLevel(Enchantment enchant, ItemStack stack) {
		if(!exists(enchant)) {
			return 0;
		}
		
		return EnchantmentHelper.getEnchantmentLevel(enchant.effectId, stack);
	}
}

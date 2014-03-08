package mariculture.magic;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.magic.enchantments.EnchantmentFlight;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class MagicKeyHandler {
	public static void activate() {
		EntityPlayer player = ClientHelper.getPlayer();
		if(KeyHelper.ACTIVATE_PRESSED && player.capabilities.isFlying && EnchantHelper.hasEnchantment(Magic.flight, player)) {
			if(EnchantmentFlight.mode < EnchantmentFlight.maxMode) {
				EnchantmentFlight.mode++;
			} else {
				EnchantmentFlight.mode = 0;
			}
			
			ClientHelper.addToChat(StatCollector.translateToLocal("mariculture.string.flight") + (EnchantmentFlight.mode + 1));
			EnchantmentFlight.set(EnchantHelper.getEnchantStrength(Magic.flight, player), player);
		}
	}
}

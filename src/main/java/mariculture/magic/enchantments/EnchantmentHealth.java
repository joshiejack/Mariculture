package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentHealth extends EnchantmentJewelry {
	public EnchantmentHealth(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		setName("health");
		minLevel = 30;
		maxLevel = 45;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	public static void activate(EntityPlayer player) {
		int max = EnchantHelper.getEnchantStrength(Magic.health, player);
		int maxRestored = max * 3;
		
		if (maxRestored > player.getMaxHealth()) {
			maxRestored = (int) player.getMaxHealth();
		}

		if (player.getHealth() < maxRestored) {
			player.heal(1);
			EnchantHelper.damageItems(Magic.health, player, 1);
		}
	}
}

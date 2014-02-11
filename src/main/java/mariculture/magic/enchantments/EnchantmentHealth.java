package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentHealth extends EnchantmentJewelry {
	public EnchantmentHealth(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("health");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 35 + (level - 1) * 9;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		if(EnchantHelper.exists(Magic.hungry)) {
			if (enchantment.effectId == Magic.hungry.effectId) {
				return false;
			}
		}

		return super.canApplyTogether(enchantment);
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

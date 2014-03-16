package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class EnchantmentFallDamage extends EnchantmentJewelry {
	public EnchantmentFallDamage(final int i, final int weight, final EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("fall");
	}

	@Override
	public int getMinEnchantability(final int level) {
		return 1 + 10 * (level - 1);
	}

	@Override
	public int getMaxEnchantability(final int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	public static void activate(LivingFallEvent event) {		
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			int damage = (int) (event.distance - 3);

			if (EnchantHelper.hasEnchantment(Magic.fall, player)) {
				int strength = EnchantHelper.getEnchantStrength(Magic.fall, player);

				int maxDistanceAbsorbed = (strength * 10);

				if (damage < maxDistanceAbsorbed) {
					if (damage > 0) {
						EnchantHelper.damageItems(Magic.fall, player, damage / 5);
					}

					event.setCanceled(true);
				} else {
					event.distance = event.distance - maxDistanceAbsorbed;

					if (damage > 0) {
						EnchantHelper.damageItems(Magic.fall, player, (int) (event.distance / 5));
					}
				}
			}
		}
	}
}

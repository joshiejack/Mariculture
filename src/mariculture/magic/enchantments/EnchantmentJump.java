package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantmentJump extends EnchantmentJewelry {
	public EnchantmentJump(final int i, final int weight, final EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("jump");
	}

	@Override
	public int getMinEnchantability(final int level) {
		return 5 + (level - 1) * 8;
	}

	@Override
	public int getMaxEnchantability(final int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	private static float jumpHeight = 0;
	private static int damageTicker = 0;

	public static void activate(EntityPlayer player) {
		if (jumpHeight > 0 && !player.isSneaking() && KeyHelper.ACTIVATE_PRESSED) {
			player.motionY *= jumpHeight;
		}

		if (player.motionY > 0 && !player.handleWaterMovement() && !player.isSneaking() && KeyHelper.ACTIVATE_PRESSED && jumpHeight > 0) {
			damageTicker++;
						
			if (damageTicker >= 25) {
				damageTicker = 0;
							
				EnchantHelper.damageItems(Magic.jump, player, 1);
			}
		}
	}

	public static void set(int jump) {
		if (jump > 0) {
			jumpHeight = (float) (1F + (jump * 0.5));

			return;
		}

		jumpHeight = 0;
	}
}

package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentJump extends EnchantmentJewelry {
	public EnchantmentJump(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("jump");
	}

	@Override
	public int getMinEnchantability(final int level) {
		return 5 + (level - 1) * 8;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	private static float jumpHeight = 0;
	private static int damageTicker = 0;

	public static void activate(EntityPlayer player) {
		if (jumpHeight > 0 && !player.isSneaking() && KeyHelper.ACTIVATE_PRESSED && player.onGround) {
			player.motionY += jumpHeight;
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
			jumpHeight = (float) (jump * 0.15);

			return;
		}

		jumpHeight = 0;
	}
}

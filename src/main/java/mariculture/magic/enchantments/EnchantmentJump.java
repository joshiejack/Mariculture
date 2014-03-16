package mariculture.magic.enchantments;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.core.lib.EnchantSetting;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentJump extends EnchantmentJewelry {
	public EnchantmentJump(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		setName("jump");
		minLevel = 1;
		maxLevel = 35;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	private static float jumpHeight = 0;
	private static int damageTicker = 0;

	public static void activate(EntityPlayer player) {
		if (jumpHeight > 0 && !player.isSneaking() && ClientHelper.isActivateKeyPressed() && player.onGround) {
			player.motionY += jumpHeight;
			
			if(!player.handleWaterMovement()) {
				damageTicker++;
				if(damageTicker % EnchantSetting.JUMPS_PER == 0) {
					EnchantHelper.damageItems(Magic.jump, player, 1);
				}
			}
		}
	}

	public static void set(int jump) {
		if(jump > 0)
			jumpHeight = (float) (jump * EnchantSetting.JUMP_FACTOR);
		else
			jumpHeight = 0;
	}
}

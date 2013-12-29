package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantmentSpeed extends EnchantmentJewelry {
	public EnchantmentSpeed(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("speed");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 5 + (level - 1) * 8;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	private static float runSpeed = 0;
	private static int damageTicker = 0;

	public static void activate(EntityPlayer player) {
		if (runSpeed > 0 && player.onGround && !player.isInWater()) {
			if (runSpeed > 0 && KeyHelper.ACTIVATE_PRESSED
					&& GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward)) {
				player.moveFlying(0F, 1.0F, runSpeed);
			}

			if ((player.motionX != 0 || player.motionZ != 0) && !player.isSneaking() && KeyHelper.ACTIVATE_PRESSED) {

				damageTicker++;

				if (player.isSprinting()) {
					damageTicker++;
				}

				if (damageTicker >= 600) {
					damageTicker = 0;
					EnchantHelper.damageItems(Magic.speed, player, 1);
				}
			}
		}
	}

	public static void set(int speed) {		
		if(speed > 0) {
			runSpeed = 0.05F * speed;
			return;
		}

		runSpeed = 0;
	}
}

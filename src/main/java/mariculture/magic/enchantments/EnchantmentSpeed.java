package mariculture.magic.enchantments;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;

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
		if(runSpeed > 0 && player.onGround && !player.isInWater() && player.isSprinting() && ClientHelper.isForwardPressed()) {
			player.moveFlying(0F, 1.0F, runSpeed);
			
			damageTicker++;
			if(damageTicker % 1200 == 0) {
				EnchantHelper.damageItems(Magic.speed, player, 1);
			}
		}
	}

	public static void set(int speed) {	
		if(speed > 0)
			runSpeed = 0.035F * speed;
		else
			runSpeed = 0;
	}
}

package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentStepUp extends EnchantmentJewelry {
	public EnchantmentStepUp(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		setName("stepUp");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 4 + (level - 1) * 7;
	}
	
	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 60;
	}
	
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
	private static float stepUp = 0;
	private static int damageTicker = 0;

	public static void set(int step, EntityPlayer player) {
		float steps = 0.5F * (step + 1);
		if(steps != player.stepHeight) {
			player.stepHeight = steps;
			player.getEntityData().setBoolean("HasStepUp", true);
		}
			
		if(step == 0 && player.getEntityData().hasKey("HasStepUp")) {
			player.getEntityData().removeTag("HasStepUp");
			player.stepHeight = 0.5F;
		}
		
		if(step > 0) {
			damageTicker++;
			if(damageTicker >= 900) {
				EnchantHelper.damageItems(Magic.stepUp, player, 1);
				damageTicker = 0;
			}
		}
	}
}

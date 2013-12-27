package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantmentSpider extends EnchantmentJewelry {
	public static boolean activated = false;
	public static boolean toggledOn = false;
	private static int damageTicker = 0;

	public EnchantmentSpider(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("spiderman");
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		if(stack.getItem() instanceof ItemJewelry) {
			return true;
		}
		
		return false;
	}

	@Override
	public int getMinEnchantability(int level) {
		return 20;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	public static void activate(EntityPlayer player) {
		if (activated) {
			if (player.isCollidedHorizontally && toggledOn && !player.isOnLadder()) {
				final float factor = 0.15F;

				if (player.motionX < (-factor)) {
					player.motionX = -factor;
				}

				if (player.motionX > factor) {
					player.motionX = factor;
				}

				if (player.motionZ < (-factor)) {
					player.motionZ = -factor;
				}

				if (player.motionZ > factor) {
					player.motionZ = factor;
				}

				player.fallDistance = 0.0F;

				if (player.motionY < -0.14999999999999999D) {
					player.motionY = -0.14999999999999999D;
				}

				player.motionY = 0.20000000000000001D;

				damageTicker++;
				if (damageTicker == 1200) {
					damageTicker = 0;
					EnchantHelper.damageItems(Magic.spider, player, 1);
				}
			}
		}
	}

	public static void set(boolean spider) {
		activated = spider;
	}
}

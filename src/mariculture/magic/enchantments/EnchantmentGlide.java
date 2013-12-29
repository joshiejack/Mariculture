package mariculture.magic.enchantments;

import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantmentGlide extends EnchantmentJewelry {
	public EnchantmentGlide(final int i, final int weight, final EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("glide");
	}

	@Override
	public int getMinEnchantability(final int level) {
		return 10 + 20 * (level - 1);
	}

	@Override
	public int getMaxEnchantability(final int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	public static int hasGlide = 0;
	public static int toggleOn;
	public static int keyCoolDown = 0;

	public static void damage(EntityPlayer player, Random rand) {
		if (EnchantHelper.hasEnchantment(Magic.glide, player) && toggleOn > 0) {
			EnchantHelper.damageItems(Magic.glide, player, 1);
		}
	}

	public static void activate(final EntityPlayer player) {
		if (keyCoolDown > 0) {
			keyCoolDown--;
		}

		if (hasGlide > 0 && player.motionY < 0.0F && !player.isOnLadder() && !player.handleWaterMovement()
				&& !player.isSneaking() && toggleOn == 1) {
			player.motionY /= 1.6F;
		} else if (hasGlide > 1 && toggleOn == 2 && player.motionY < 0.0F && !player.isOnLadder()
				&& !player.handleWaterMovement() && !player.isSneaking()) {
			player.motionY /= 0.85F;
		}
	}

	public static void set(final int glide) {
		hasGlide = glide;
	}
}

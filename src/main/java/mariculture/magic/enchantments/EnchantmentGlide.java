package mariculture.magic.enchantments;

import java.util.Random;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class EnchantmentGlide extends EnchantmentJewelry {
	public EnchantmentGlide(final int i, final int weight, final EnumEnchantmentType type) {
		super(i, weight, type);
		setName("glide");
		minLevel = 1;
		maxLevel = 15;
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

	public static void activate(EntityPlayer player) {
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

	public static void toggle() {
		if (toggleOn == 0) {
			ClientHelper.addToChat(StatCollector.translateToLocal("mariculture.string.enabledGlide"));
			toggleOn = 1;
		} else if (EnchantmentGlide.toggleOn == 1) {
			if (EnchantmentGlide.hasGlide > 0) {
				ClientHelper.addToChat(StatCollector.translateToLocal("mariculture.string.enabledFastFall"));
				toggleOn = 2;
			} else {
				ClientHelper.addToChat(StatCollector.translateToLocal("mariculture.string.disabledGlide"));
				toggleOn = 0;
			}
		} else if (EnchantmentGlide.toggleOn == 2) {
			ClientHelper.addToChat(StatCollector.translateToLocal("mariculture.string.disabledGlide"));
			toggleOn = 0;
		}
		
		keyCoolDown = 2;
	}
}

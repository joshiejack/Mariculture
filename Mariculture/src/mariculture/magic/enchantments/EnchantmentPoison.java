package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class EnchantmentPoison extends EnchantmentJewelry {
	public EnchantmentPoison(final int i, final int weight, final EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("poison");
	}

	@Override
	public boolean canApply(ItemStack stack) {
		if (stack.getItem() instanceof ItemJewelry) {
			return true;
		}

		if (EnumEnchantmentType.weapon.canEnchantItem(stack.getItem())) {
			return true;
		}

		return false;
	}

	@Override
	public int getMinEnchantability(final int level) {
		return 1 + (level - 1) * 10;
	}

	@Override
	public int getMaxEnchantability(final int level) {
		return this.getMinEnchantability(level) + 15;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	public static void activate(EntityPlayer player) {
		if (EnchantHelper.hasEnchantment(Magic.poison, player)) {
			if (EnchantHelper.getEnchantStrength(Magic.poison, player) > 2) {
				if (player.isPotionActive(Potion.poison)) {
					EnchantHelper.damageItems(Magic.poison, player, 1);
					player.removePotionEffect(Potion.poison.id);
				}
			}
		}
	}

	public static void onAttack(EntityPlayer player, Entity target) {
		if (EnchantHelper.hasEnchantment(Magic.poison, player)) {
			if (target instanceof EntityLivingBase) {
				if (player.getCurrentEquippedItem() == null
						|| (player.getCurrentEquippedItem() != null &&
								EnchantHelper.getLevel(Magic.poison, player.getCurrentEquippedItem()) > 0)) {

					EntityLivingBase enemy = (EntityLivingBase) target;
					if (!enemy.isPotionActive(Potion.poison)) {
						EnchantHelper.damageItems(Magic.poison, player, 1);
					}

					enemy.addPotionEffect(new PotionEffect(Potion.poison.id, 20 * EnchantHelper.getEnchantStrength(Magic.poison, player), 0));
				}
			}
		}
	}

	public static void onRightClick(EntityPlayer player, Entity target) {
		if (KeyHelper.ACTIVATE_PRESSED) {
			if (EnchantHelper.hasEnchantment(Magic.poison, player)) {
				if (target instanceof EntityLivingBase
						&& EnchantHelper.getEnchantStrength(Magic.poison, player) > 1) {

					EntityLivingBase life = (EntityLivingBase) target;

					if (!life.isPotionActive(Potion.poison)) {
						EnchantHelper.damageItems(Magic.poison, player, 1);
					}

					life.addPotionEffect(new PotionEffect(Potion.poison.id, 30 * EnchantHelper.getEnchantStrength(Magic.poison, player), 0));

				}
			}
		}
	}
}

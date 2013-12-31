package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantmentRestore extends EnchantmentJewelry {
	public EnchantmentRestore(final int i, final int weight,
			final EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("restore");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 10 + 20 * (level - 1);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	public static void activate(EntityPlayer player) {
		//Repair Handheld
		ItemStack hand = player.getCurrentEquippedItem();
		if (hand != null) {
			if (hand.isItemStackDamageable() && hand.isItemDamaged() && hand.getItem().isRepairable()) {
				if (hand.getItemDamage() > 0) {
					hand.setItemDamage(player.getCurrentEquippedItem().getItemDamage() - 1);
					EnchantHelper.damageItems(Magic.repair, player, 1);
				}
			}
		}
		
		int strength = EnchantHelper.getEnchantStrength(Magic.repair, player);

		//Repair Armor
		if (strength > 2) {
			for (int i = 0; i < EnchantHelper.getEnchantStrength(Magic.repair, player) - 1; i++) {
				for (int j = 0; j < 4; j++) {
					ItemStack armor = player.getCurrentArmor(j);
					if (armor != null) {
						if (armor.isItemStackDamageable() && armor.isItemDamaged() && armor.getItem().isRepairable()) {
							if (armor.getItemDamage() > 0) {
								armor.setItemDamage(armor.getItemDamage() - 1);
								EnchantHelper.damageItems(Magic.repair, player, 1);
							}
						}
					}
				}
			}
		}

		//Repair the Hotbar
		if (strength >= 1) {
			for (int i = 0; i < 9; i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack != null) {
					if (stack.isItemStackDamageable() && stack.isItemDamaged() && stack.getItem().isRepairable()) {
						if (stack.getItemDamage() > 0) {
							stack.setItemDamage(stack.getItemDamage() - 1);
							EnchantHelper.damageItems(Magic.repair, player, 1);
						}
					}
				}
			}
		}
	}
}

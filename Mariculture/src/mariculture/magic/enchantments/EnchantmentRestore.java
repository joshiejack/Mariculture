package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantmentRestore extends EnchantmentJewelry {
	public EnchantmentRestore(final int i, final int weight, final EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("restore");
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
		if (EnchantHelper.hasEnchantment(Magic.repair, player)) {
			if (player.getCurrentEquippedItem() != null) {				
				if (player.getCurrentEquippedItem().isItemStackDamageable() && player.getCurrentEquippedItem().isItemDamaged()
						&& player.getCurrentEquippedItem().getItem().isRepairable()) {
					if (player.getCurrentEquippedItem().getItemDamage() > 0) {
						player.getCurrentEquippedItem().setItemDamage(player.getCurrentEquippedItem().getItemDamage() - 1);
						EnchantHelper.damageItems(Magic.repair, player, 1);
					}
				}
			}

			if (EnchantHelper.getEnchantStrength(Magic.repair, player) > 2) {
				for (int i = 0; i < EnchantHelper.getEnchantStrength(Magic.repair, player) - 1; i++) {
					for (int j = 0; j < 4; j++) {
						if (player.getCurrentArmor(j) != null) {
							if (player.getCurrentArmor(j).isItemStackDamageable() && player.getCurrentArmor(j).isItemDamaged()
									&& player.getCurrentArmor(j).getItem().isRepairable()) {
								if (player.getCurrentArmor(j).getItemDamage() > 0) {
									player.getCurrentArmor(j).setItemDamage(player.getCurrentArmor(j).getItemDamage() - 1);
									EnchantHelper.damageItems(Magic.repair, player, 1);
								}
							}
						}
					}
				}
			}

			if (EnchantHelper.getEnchantStrength(Magic.repair, player) >= 1) {
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
}

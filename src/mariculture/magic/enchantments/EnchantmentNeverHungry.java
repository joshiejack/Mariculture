package mariculture.magic.enchantments;

import java.util.List;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import mariculture.magic.gui.ContainerMirror;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantmentNeverHungry extends EnchantmentJewelry {
	public EnchantmentNeverHungry(final int i, final int weight, final EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("hungry");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 30 + (level - 1) * 9;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		if(EnchantHelper.exists(Magic.health)) {
			if (enchantment.effectId == Magic.health.effectId) {
				return false;
			}
		}

		return super.canApplyTogether(enchantment);
	}

	public static void activate(EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			if (!player.capabilities.isCreativeMode) {
				if (EnchantHelper.hasEnchantment(Magic.hungry, player)) {
					int level = EnchantHelper.getEnchantStrength(Magic.hungry, player);

					if (player.getFoodStats().getFoodLevel() < 20) {
						float saturation = 0.05F * level;

						player.getFoodStats().addStats(1, saturation);

						if (!(player.openContainer instanceof ContainerMirror)) {
							EnchantHelper.damageItems(Magic.hungry, player, 1);

							return;
						}
					}

				}

				if (player.openContainer instanceof ContainerMirror && player.getFoodStats().getFoodLevel() < 20) {
					List mirror = player.openContainer.getInventory();
					for (int i = 0; i < mirror.size() - 1; i++) {
						if (mirror.get(i) != null && EnchantHelper.getLevel(Magic.hungry, (ItemStack) mirror.get(i)) > 0) {
							ItemStack item = (ItemStack) mirror.get(i);

							item.setItemDamage(item.getItemDamage() + 3);

							if (item.getItemDamage() >= item.getMaxDamage()) {
								item = null;
							}
						}
					}
				}
			}
		}
	}
}

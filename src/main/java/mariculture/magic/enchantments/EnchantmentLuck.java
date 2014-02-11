package mariculture.magic.enchantments;

import mariculture.api.fishery.ItemBaseRod;
import mariculture.magic.ItemMirror;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchantmentLuck extends Enchantment {
	public EnchantmentLuck(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		setName("luck");
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() instanceof ItemMirror || stack.getItem() instanceof ItemBaseRod;
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemMirror || stack.getItem() instanceof ItemBaseRod;
	}
	
	@Override
	public int getMinEnchantability(int level) {
		return 25 + (level - 1) * 10;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return this.getMinEnchantability(level) + 25;
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}
}

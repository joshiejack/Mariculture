package mariculture.magic.enchantments;

import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchantmentJewelry extends Enchantment {
	protected EnchantmentJewelry(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() instanceof ItemJewelry;
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemJewelry;
	}
}

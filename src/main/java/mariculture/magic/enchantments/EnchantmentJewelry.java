package mariculture.magic.enchantments;

import mariculture.core.items.ItemPearl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchantmentJewelry extends Enchantment {
/**Enchantment Levels
	* Step-Up = 1, Spider = 1, Speed = 1, Blink = 25, Glide = 1, Jump = 1, Fall = 1
	* Health = 30, Elemental = 35, Repair = 40, Never Hungry = 45, Resurrection = 50, Flight = 55  **/
	protected int minLevel;
	protected int maxLevel;

	protected EnchantmentJewelry(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() instanceof ItemPearl;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemPearl;
	}

	@Override
	public int getMinEnchantability(int level) {
		double req = (level * ((level / 3) * 2)) + ((level * getMaxLevel()) / (maxLevel));
		return (int) (req + ((minLevel - 1) + ((level) + 1)));
	}

	@Override
	public int getMaxEnchantability(int level) {
		double req = (level * ((level / 3) * 2)) + ((level * getMaxLevel()) / (maxLevel));
		return (int) (req + ((maxLevel - 1) + ((level) + 1)));
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return false;
	}

	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
}

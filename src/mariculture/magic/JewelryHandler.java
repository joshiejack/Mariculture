package mariculture.magic;

import mariculture.core.lib.Jewelry;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class JewelryHandler {
	public static void addJewelry(ItemStack item, ItemStack extra, ItemStack material, int type) {
		switch (type) {
		case Jewelry.RING:
			addRing(item, extra, material);
			break;
		case Jewelry.BRACELET:
			addBracelet(item, extra, material);
			break;
		case Jewelry.NECKLACE:
			addNecklace(item, extra, material);
			break;
		}
	}

	public static void addRing(ItemStack item, ItemStack jewel, ItemStack material) {
		GameRegistry.addRecipe(item, new Object[] { "ABA", "A A", "AAA", Character.valueOf('A'), material,
				Character.valueOf('B'), jewel });
	}

	public static void addBracelet(ItemStack item, ItemStack string, ItemStack material) {
		GameRegistry.addRecipe(item, new Object[] { "A A", "B B", " B ", Character.valueOf('A'), material,
				Character.valueOf('B'), string });
	}

	public static void addNecklace(ItemStack item, ItemStack string, ItemStack material) {
		GameRegistry.addRecipe(item, new Object[] { "BAB", "B B", "BBB", Character.valueOf('A'), material,
				Character.valueOf('B'), string });
	}
}

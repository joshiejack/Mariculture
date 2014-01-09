package mariculture.magic;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.Jewelry;
import net.minecraft.item.ItemStack;

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
		RecipeHelper.addShapedRecipe(MaricultureHandlers.anvil.createWorkedItem(item, 250), new Object[] { 
			"ABA", "A A", "AAA", 'A', material, 'B', jewel 
		});
	}

	public static void addBracelet(ItemStack item, ItemStack string, ItemStack material) {
		RecipeHelper.addShapedRecipe(MaricultureHandlers.anvil.createWorkedItem(item, 450), 
				new Object[] { "A A", "B B", " B ", 'A', material, 'B', string 
		});
	}

	public static void addNecklace(ItemStack item, ItemStack string, ItemStack material) {
		RecipeHelper.addShapedRecipe(MaricultureHandlers.anvil.createWorkedItem(item, 750), new Object[] { 
			"BAB", "B B", "BBB", 'A', material, 'B', string 
		});
	}
}

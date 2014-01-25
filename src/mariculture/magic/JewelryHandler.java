package mariculture.magic;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.Jewelry;
import net.minecraft.item.ItemStack;

public class JewelryHandler {	
	public static void addJewelry(ItemStack item, ItemStack extra, ItemStack material, int type, int hits) {
		switch (type) {
		case Jewelry.RING:
			addRing(item, extra, material, hits);
			break;
		case Jewelry.BRACELET:
			addBracelet(item, extra, material, hits);
			break;
		case Jewelry.NECKLACE:
			addNecklace(item, extra, material, hits);
			break;
		}
	}
	
	public static int getUIdentifier(ItemStack output, ItemStack stack1, ItemStack stack2) {
		return Integer.parseInt("" + output.stackTagCompound.getInteger("Part1") + output.stackTagCompound.getInteger("Part2"));
	}

	public static void addRing(ItemStack item, ItemStack jewel, ItemStack material, int hits) {
		String damage = "" + getUIdentifier(item, jewel, material);
		ItemStack jewelry = MaricultureHandlers.anvil.createWorkedItem(item, hits);
		jewelry.setItemDamage(Integer.parseInt(damage));
		RecipeHelper.addShapedRecipe(jewelry, new Object[] { 
			"ABA", "A A", "AAA", 'A', material, 'B', jewel 
		});
	}

	public static void addBracelet(ItemStack item, ItemStack resource, ItemStack thread, int hits) {
		String damage = "" + getUIdentifier(item, resource, thread);
		ItemStack jewelry = MaricultureHandlers.anvil.createWorkedItem(item, hits);
		jewelry.setItemDamage(Integer.parseInt(damage));
		RecipeHelper.addShapedRecipe(jewelry, 
				new Object[] { "A A", "B B", " B ", 'A', thread, 'B', resource 
		});
	}

	public static void addNecklace(ItemStack item, ItemStack resource, ItemStack thread, int hits) {
		String damage = "" + getUIdentifier(item, resource, thread);
		ItemStack jewelry = MaricultureHandlers.anvil.createWorkedItem(item, hits);
		jewelry.setItemDamage(Integer.parseInt(damage));
		RecipeHelper.addShapedRecipe(jewelry, new Object[] { 
			"BAB", "B B", "BBB", 'A', thread, 'B', resource 
		});
	}
}

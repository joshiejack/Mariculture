package mariculture.magic.jewelry.parts;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Jewelry;

public class PartString extends JewelryPart {
	@Override
	public boolean isValid(int type) {
		return type == Jewelry.BRACELET;
	}
		
	@Override
	public boolean isVisible(int type) {
		return false;
	}

	@Override
	public String getPartName() {
		return "string";
	}
	
	@Override
	public String getPartLang() {
		return "item.string.name";
	}
	
	@Override
	public int getEnchantability() {
		return 1;
	}

	@Override
	public String getPartType(int type) {
		return "string";
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Item.silk);
	}
}

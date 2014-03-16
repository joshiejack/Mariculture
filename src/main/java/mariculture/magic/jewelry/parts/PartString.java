package mariculture.magic.jewelry.parts;

import mariculture.core.lib.Jewelry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
	
	@Override
	public int getHits(int type) {
		return 10;
	}
	
	@Override
	public double getDurabilityModifier(int type) {
		return 2.0D;
	}
}

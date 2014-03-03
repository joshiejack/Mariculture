package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Jewelry;
import mariculture.core.util.Text;
import net.minecraft.item.ItemStack;

public class PartGoldString extends JewelryPart {
	public PartGoldString(int id) {
		super(id);
	}

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
		return "goldString";
	}
	
	@Override
	public String getPartLang() {
		return "item.craftingItems.goldenSilk.name";
	}
	
	@Override
	public int getEnchantability() {
		return 16;
	}
	
	@Override
	public String getColor() {
		return Text.YELLOW;
	}

	@Override
	public String getPartType(int type) {
		return "string";
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK);
	}
	
	@Override
	public int getHits(int type) {
		return 35;
	}
	
	@Override
	public double getDurabilityModifier(int type) {
		return 4.0D;
	}
}

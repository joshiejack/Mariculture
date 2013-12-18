package mariculture.magic.jewelry.parts;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import mariculture.core.Core;
import mariculture.core.lib.PrefixColor;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.PearlColor;

public class PartIron extends JewelryPart {
	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "iron";
	}
	
	@Override
	public String getPartLang() {
		return "part.material." + getPartName();
	}

	@Override
	public String getPartType(int type) {
		return "material";
	}
	
	@Override
	public String getColor() {
		return PrefixColor.GREY;
	}
	
	@Override
	public int getEnchantability() {
		return -1;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Item.ingotIron);
	}
}

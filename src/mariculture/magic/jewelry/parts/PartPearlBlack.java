package mariculture.magic.jewelry.parts;

import net.minecraft.item.ItemStack;
import mariculture.core.Core;
import mariculture.core.lib.Text;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.PearlColor;

public class PartPearlBlack extends JewelryPart {
	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "pearlBlack";
	}
	
	@Override
	public String getPartLang() {
		return "item.pearls." + getPartName() + ".name";
	}

	@Override
	public String getPartType(int type) {
		return (type == Jewelry.RING)? "jewel": "material";
	}
	
	@Override
	public String getColor() {
		return Text.GREY;
	}
	
	@Override
	public int getEnchantability() {
		return 1;
	}
	
	@Override
	public double getDurabilityModifier(int type) {
		return 3.0D;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.pearls, 1, PearlColor.BLACK);
	}
}

package mariculture.magic.jewelry.parts;

import net.minecraft.item.ItemStack;
import mariculture.core.Core;
import mariculture.core.lib.Text;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.PearlColor;

public class PartPearlOrange extends JewelryPart {
	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "pearlOrange";
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
		return Text.ORANGE;
	}
	
	@Override
	public int getEnchantability() {
		return 4;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.pearls, 1, PearlColor.ORANGE);
	}
	
	@Override
	public int getDurabilityBase(int type) {
		return 65;
	}
	
	@Override
	public int getHits(int type) {
		if(type == Jewelry.RING)
			return 20;
		if(type == Jewelry.BRACELET)
			return 45;
		return 40;
	}
}

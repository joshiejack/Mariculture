package mariculture.magic.jewelry.parts;

import net.minecraft.item.ItemStack;
import mariculture.core.Core;
import mariculture.core.lib.Text;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.PearlColor;

public class PartPearlGold extends JewelryPart {
	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "pearlGold";
	}
	
	@Override
	public String getPartLang() {
		return "item.pearls." + getPartName() + ".name";
	}

	@Override
	public String getPartType(int type) {
		return (type == Jewelry.RING)? "jewel": "material";
	}
	
	public String getColor() {
		return Text.YELLOW;
	}
	
	@Override
	public int getEnchantability() {
		return 12;
	}

	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.pearls, 1, PearlColor.GOLD);
	}
	
	public int getHits(int type) {
		if(type == Jewelry.RING)
			return 40;
		if(type == Jewelry.BRACELET)
			return 70;
		return 60;
	}
}

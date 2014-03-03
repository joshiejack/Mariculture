package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.Text;
import net.minecraft.item.ItemStack;

public class PartPearlPurple extends JewelryPart {
	public PartPearlPurple(int id) {
		super(id);
	}

	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "pearlPurple";
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
		return Text.PURPLE;
	}
	
	@Override
	public int getEnchantability() {
		return 10;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.pearls, 1, PearlColor.PURPLE);
	}
	
	@Override
	public int getHits(int type) {
		if(type == Jewelry.RING)
			return 35;
		if(type == Jewelry.BRACELET)
			return 65;
		return 55;
	}
}

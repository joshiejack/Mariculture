package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.Text;
import net.minecraft.item.ItemStack;

public class PartPearlGreen extends JewelryPart {	
	public PartPearlGreen(int id) {
		super(id);
	}

	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "pearlGreen";
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
		return Text.DARK_GREEN;
	}
	
	@Override
	public int getEnchantability() {
		return 4;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.pearls, 1, PearlColor.GREEN);
	}
	
	@Override
	public int getHits(int type) {
		if(type == Jewelry.RING)
			return 15;
		if(type == Jewelry.BRACELET)
			return 30;
		return 25;
	}
	
	public double getDurabilityModifier(int type) {
		return 0.25D;
	}
}

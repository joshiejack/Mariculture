package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.Text;
import net.minecraft.item.ItemStack;

public class PartPearlBlue extends JewelryPart {
	public PartPearlBlue(int id) {
		super(id);
	}

	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "pearlBlue";
	}
	
	@Override
	public String getPartLang() {
		return "item.pearls.pearlBlue.name";
	}

	@Override
	public String getPartType(int type) {
		return (type == Jewelry.RING)? "jewel": "material";
	}
	
	@Override
	public String getColor() {
		return Text.AQUA;
	}
	
	@Override
	public int getEnchantability() {
		return 4;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.pearls, 1, PearlColor.BLUE);
	}
}

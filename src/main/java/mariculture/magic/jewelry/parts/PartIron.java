package mariculture.magic.jewelry.parts;

import mariculture.core.lib.Jewelry;
import mariculture.core.lib.Text;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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
		return Text.GREY;
	}
	
	@Override
	public int getEnchantability() {
		return -1;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Items.iron_ingot);
	}
	
	@Override
	public int getHits(int type) {
		if(type == Jewelry.RING)
			return 5;
		if(type == Jewelry.BRACELET)
			return 35;
		if(type == Jewelry.NECKLACE)
			return 40;
		return 0;
	}
	
	@Override
	public int getDurabilityBase(int type) {
		return 75;
	}
}

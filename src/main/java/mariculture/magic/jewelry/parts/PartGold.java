package mariculture.magic.jewelry.parts;

import mariculture.core.lib.Jewelry;
import mariculture.core.lib.Text;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PartGold extends JewelryPart {
	@Override
	public boolean isValid(int type) {
		return (type == Jewelry.RING)? true: false;
	}
	
	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "gold";
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
	public int getEnchantability() {
		return 16;
	}
	
	@Override
	public String getColor() {
		return Text.YELLOW;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Item.ingotGold);
	}
	
	@Override
	public int getHits(int type) {
		return 10;
	}
	
	@Override
	public int getDurabilityBase(int type) {
		return 150;
	}
}

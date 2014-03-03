package mariculture.magic.jewelry.parts;

import mariculture.core.lib.Jewelry;
import mariculture.core.util.Text;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PartGold extends JewelryPart {
	public PartGold(int id) {
		super(id);
	}

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
		return new ItemStack(Items.gold_ingot);
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

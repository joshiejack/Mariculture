package mariculture.magic.jewelry.parts;

import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.Jewelry;
import mariculture.magic.Magic;
import net.minecraft.item.ItemStack;

public class PartOneRing extends JewelryPart {
	@Override
	public boolean addOnce() {
		return true;
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public boolean isValid(int type) {
		return (type == Jewelry.RING) ? true : false;
	}
		
	@Override
	public boolean isVisible(int type) {
		return true;
	}

	@Override
	public String getPartName() {
		return "oneRing";
	}
	
	@Override
	public String getPartLang() {
		return "part.special." + getPartName();
	}
	
	@Override
	public int getEnchantability() {
		return 20;
	}

	@Override
	public String getPartType(int type) {
		return "jewel";
	}
	
	@Override
	public ItemStack getItemStack() {
		return null;
	}
	
	@Override
	public ItemStack addEnchantments(ItemStack stack) {
		if(EnchantHelper.exists(Magic.oneRing)) {
			stack.addEnchantment(Magic.oneRing, 1);
		}
		
		return stack;
	}
	
	@Override
	public double getDurabilityModifier(int type) {
		return 1.5D;
	}
}

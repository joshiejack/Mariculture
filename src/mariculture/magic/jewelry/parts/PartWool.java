package mariculture.magic.jewelry.parts;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Jewelry;

public class PartWool extends JewelryPart {
	@Override
	public boolean isValid(int type) {
		return type == Jewelry.NECKLACE;
	}
		
	@Override
	public boolean isVisible(int type) {
		return false;
	}

	@Override
	public String getPartName() {
		return "wool";
	}
	
	@Override
	public String getPartLang() {
		return "tile.cloth.name";
	}
	
	@Override
	public int getEnchantability() {
		return 4;
	}

	@Override
	public String getPartType(int type) {
		return "string";
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Block.cloth, 1, OreDictionary.WILDCARD_VALUE);
	}
	
	@Override
	public int getHits(int type) {
		return 50;
	}
	
	@Override
	public double getDurabilityModifier(int type) {
		return 7D;
	}
}

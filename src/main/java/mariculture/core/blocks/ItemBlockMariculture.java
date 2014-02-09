package mariculture.core.blocks;

import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMariculture extends ItemBlock implements IItemRegistry {
	public ItemBlockMariculture(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override 
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName() + "." + getName(stack);
	}
	
	@Override
	public void register() {
		return;
	}
	
	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return "blank";
	}
}

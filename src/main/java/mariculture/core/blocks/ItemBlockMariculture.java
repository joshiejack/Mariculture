package mariculture.core.blocks;

import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ItemBlockMariculture extends ItemBlock implements IItemRegistry {
	public ItemBlockMariculture(Block block) {
		super(block);
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
		return ((IItemRegistry)Block.getBlockFromItem(this)).getMetaCount();
	}
}

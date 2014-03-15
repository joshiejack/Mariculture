package mariculture.core.blocks;

import mariculture.api.core.MaricultureRegistry;
import mariculture.core.util.IHasMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
	public void register(Item item) {
		String prefix = "";
		Block block = Block.getBlockFromItem(item);
		if(block instanceof BlockDecorative) {
			prefix = ((BlockDecorative) block).prefix;
		}
		
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register((prefix != null? prefix: "") + getName(new ItemStack(item, 1, j)), new ItemStack(item, 1, j));
		}
	}
	
	@Override
	public int getMetaCount() {
		return ((IHasMeta)Block.getBlockFromItem(this)).getMetaCount();
	}
}

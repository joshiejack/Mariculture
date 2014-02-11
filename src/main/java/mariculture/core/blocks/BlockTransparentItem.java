package mariculture.core.blocks;

import mariculture.core.lib.TransparentMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockTransparentItem extends ItemBlockMariculture {
	public BlockTransparentItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack itemstack) {
		switch (itemstack.getItemDamage()) {
		case TransparentMeta.PLASTIC:
			return "plastic";
		default:
			return "plastic";
		}
	}
}
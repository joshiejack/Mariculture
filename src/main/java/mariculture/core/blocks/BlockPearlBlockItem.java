package mariculture.core.blocks;

import mariculture.core.lib.PearlColor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockPearlBlockItem extends ItemBlockMariculture {
	public BlockPearlBlockItem(Block block) {
		super(block);
	}

	public String getName(ItemStack stack) {
		return PearlColor.get(stack.getItemDamage());
	}
}
package mariculture.core.blocks;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.WoodMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockWoodItem extends ItemBlockMariculture {
	public BlockWoodItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case WoodMeta.POLISHED_LOG:
			return "polishedLog";
		case WoodMeta.POLISHED_PLANK:
			return "polishedPlank";
		case WoodMeta.BASE_WOOD:
			return "baseWood";
		default:
			return "woodBlocks";
		}
	}
}

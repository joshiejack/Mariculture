package mariculture.core.blocks;

import mariculture.core.lib.WoodMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockWoodItem extends ItemBlockMariculture {
	public BlockWoodItem(int i, Block block) {
		super(i);
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

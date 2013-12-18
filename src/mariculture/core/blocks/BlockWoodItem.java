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
		String name = "";
		switch (stack.getItemDamage()) {
		case WoodMeta.POLISHED_LOG:
			name = "polishedLog";
			break;
		case WoodMeta.POLISHED_PLANK:
			name = "polishedPlank";
			break;
		case WoodMeta.BASE_WOOD:
			name = "baseWood";
			break;
		default:
			name = "woodBlocks";
		}

		return name;
	}
}

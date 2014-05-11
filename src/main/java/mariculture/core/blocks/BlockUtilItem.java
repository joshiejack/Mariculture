package mariculture.core.blocks;

import mariculture.core.lib.MachineMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockUtilItem extends ItemBlockMariculture {
	public BlockUtilItem(int i, Block block) {
		super(i);
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case MachineMeta.INCUBATOR_BASE: 
			name = "incubatorBase";
			break;
		case MachineMeta.INCUBATOR_TOP: 
			name = "incubatorTop";
			break;
		case MachineMeta.AUTOFISHER:
			name = "autoFishing";
			break;
		case MachineMeta.LIQUIFIER:
			name = "liquifier";
			break;
		case MachineMeta.BOOKSHELF:
			name = "bookshelf";
			break;
		case MachineMeta.SAWMILL:
			name = "sawmill";
			break;
		case MachineMeta.SLUICE:
			name = "sluice";
			break;
		case MachineMeta.SPONGE: 
			name = "sponge";
			break;
		case MachineMeta.DICTIONARY: 
			name = "dictionary";
			break;
		case MachineMeta.FISH_SORTER:
			name = "fishSorter";
			break;
		default:
			name = "utilBlocks";
		}

		return name;
	}
}
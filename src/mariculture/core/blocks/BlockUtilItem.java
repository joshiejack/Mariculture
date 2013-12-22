package mariculture.core.blocks;

import mariculture.core.lib.UtilMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockUtilItem extends ItemBlockMariculture {
	public BlockUtilItem(int i, Block block) {
		super(i);
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case UtilMeta.INCUBATOR_BASE: 
			name = "incubatorBase";
			break;
		case UtilMeta.INCUBATOR_TOP: 
			name = "incubatorTop";
			break;
		case UtilMeta.AUTOFISHER:
			name = "autoFishing";
			break;
		case UtilMeta.LIQUIFIER:
			name = "liquifier";
			break;
		case UtilMeta.SETTLER:
			name = "settler";
			break;
		case UtilMeta.BOOKSHELF:
			name = "bookshelf";
			break;
		case UtilMeta.SAWMILL:
			name = "sawmill";
			break;
		case UtilMeta.SLUICE:
			name = "sluice";
			break;
		case UtilMeta.SPONGE: 
			name = "sponge";
			break;
		case UtilMeta.DICTIONARY: 
			name = "dictionary";
			break;
		case UtilMeta.GAS_OVEN:
			name = "gasOven";
			break;
		case UtilMeta.FISH_SORTER:
			name = "fishSorter";
			break;
		default:
			name = "dictionary";
		}

		return name;
	}
}
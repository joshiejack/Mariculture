package mariculture.core.blocks;

import mariculture.core.lib.PearlColor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockPearlBrickItem extends ItemBlockMariculture {
	public BlockPearlBrickItem(int i, Block block) {
		super(i);
	}

	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case PearlColor.WHITE: {
			name = "white";
			break;
		}
		case PearlColor.GREEN: {
			name = "green";
			break;
		}
		case PearlColor.YELLOW: {
			name = "yellow";
			break;
		}
		case PearlColor.ORANGE: {
			name = "orange";
			break;
		}
		case PearlColor.RED: {
			name = "red";
			break;
		}
		case PearlColor.GOLD: {
			name = "gold";
			break;
		}
		case PearlColor.BROWN: {
			name = "brown";
			break;
		}
		case PearlColor.PURPLE: {
			name = "purple";
			break;
		}
		case PearlColor.BLUE: {
			name = "blue";
			break;
		}
		case PearlColor.BLACK: {
			name = "black";
			break;
		}
		case PearlColor.PINK: {
			name = "pink";
			break;
		}
		case PearlColor.SILVER: {
			name = "silver";
			break;
		}

		default:
			name = "lamp";
		}

		return name;

	}
}
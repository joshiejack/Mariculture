package mariculture.fishery.blocks;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.PearlColor;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockNeonLampItem extends ItemBlockMariculture {
	public BlockNeonLampItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case PearlColor.WHITE:
			return "white";
		case PearlColor.GREEN:
			return "green";
		case PearlColor.YELLOW:
			return "yellow";
		case PearlColor.ORANGE:
			return "orange";
		case PearlColor.RED:
			return "red";
		case PearlColor.GOLD:
			return "gold";
		case PearlColor.BROWN:
			return "brown";
		case PearlColor.PURPLE:
			return "purple";
		case PearlColor.BLUE:
			return "blue";
		case PearlColor.BLACK:
			return "black";
		case PearlColor.PINK:
			return "pink";
		case PearlColor.SILVER:
			return "silver";
		default:
			return "lamp";
		}
	}
}
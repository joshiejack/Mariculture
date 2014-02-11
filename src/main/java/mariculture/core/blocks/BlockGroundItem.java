package mariculture.core.blocks;

import mariculture.core.lib.GroundMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockGroundItem extends ItemBlockMariculture {
	public BlockGroundItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack itemstack) {
		switch (itemstack.getItemDamage()) {
		case GroundMeta.BUBBLES:
			return "gas";
		default:
			return "groundBlocks";
		}
	}
}
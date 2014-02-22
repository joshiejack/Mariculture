package mariculture.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockWaterItem extends ItemBlockMariculture {
	public BlockWaterItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack stack) {
		switch(stack.getItemDamage()) {
			case 0:
				return "oyster";
			case 1:
				return "net";
			default:
				return getUnlocalizedName();
		}
	}
}

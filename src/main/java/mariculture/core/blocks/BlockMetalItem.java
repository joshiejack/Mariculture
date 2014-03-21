package mariculture.core.blocks;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.MetalMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockMetalItem extends ItemBlockMariculture {
	public BlockMetalItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
			case MetalMeta.COPPER_BLOCK: 	return "copperBlock";
			case MetalMeta.ALUMINUM_BLOCK:	return "aluminumBlock";
			case MetalMeta.RUTILE_BLOCK:	return "rutileBlock";
			case MetalMeta.MAGNESIUM_BLOCK:	return "magnesiumBlock";
			case MetalMeta.TITANIUM_BLOCK:  return "titaniumBlock";
			case MetalMeta.BASE_IRON:		return "baseIron";
			default: 						return null;
		}
	}
}

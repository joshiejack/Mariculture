package mariculture.core.blocks;

import mariculture.core.lib.RockMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockRockItem extends ItemBlockMariculture {
	public BlockRockItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
			case RockMeta.CORAL_ROCK: 	return "coralRock";
			case RockMeta.COPPER: 		return "copperOre";
			case RockMeta.BAUXITE:		return "bauxiteOre";
			case RockMeta.RUTILE:		return "rutileOre";
			case RockMeta.BASE_BRICK:	return "baseBrick";
			default: 					return null;
		}
	}
}
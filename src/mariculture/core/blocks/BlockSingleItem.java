package mariculture.core.blocks;

import mariculture.core.lib.SingleMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockSingleItem extends ItemBlockMariculture {
	public BlockSingleItem(int i, Block block) {
		super(i);
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case SingleMeta.AIR_PUMP:
			return "airpump";
		case SingleMeta.FISH_FEEDER:
			return "feeder";
		case SingleMeta.TURBINE_WATER:
			return "turbine";
		case SingleMeta.FLUDD_STAND:
			return "fludd";
		case SingleMeta.TURBINE_GAS:
			return "turbineGas";
		case SingleMeta.GEYSER:
			return "geyser";
		case SingleMeta.TURBINE_HAND:
			return "turbineHand";
		case SingleMeta.ANVIL:
			return "anvil";
		default:
			return "customBlocks";
		}
	}
}

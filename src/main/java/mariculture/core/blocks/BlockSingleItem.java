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
		case SingleMeta.ANVIL_1:
			return "anvil";
		case SingleMeta.ANVIL_2:
			return "anvil2";
		case SingleMeta.ANVIL_3:
			return "anvil3";
		case SingleMeta.ANVIL_4:
			return "anvil4";
		case SingleMeta.INGOT_CASTER:
			return "ingotCaster";
		default:
			return "customBlocks";
		}
	}
}

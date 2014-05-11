package mariculture.core.blocks;

import mariculture.core.lib.RenderMeta;
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
		case RenderMeta.AIR_PUMP:
			return "airpump";
		case RenderMeta.FISH_FEEDER:
			return "feeder";
		case RenderMeta.TURBINE_WATER:
			return "turbine";
		case RenderMeta.FLUDD_STAND:
			return "fludd";
		case RenderMeta.TURBINE_GAS:
			return "turbineGas";
		case RenderMeta.GEYSER:
			return "geyser";
		case RenderMeta.TURBINE_HAND:
			return "turbineHand";
		case RenderMeta.ANVIL_1:
			return "anvil";
		case RenderMeta.ANVIL_2:
			return "anvil2";
		case RenderMeta.ANVIL_3:
			return "anvil3";
		case RenderMeta.ANVIL_4:
			return "anvil4";
		case RenderMeta.INGOT_CASTER:
			return "ingotCaster";
		case RenderMeta.BLOCK_CASTER:
			return "blockCaster";
		case RenderMeta.NUGGET_CASTER:
			return "nuggetCaster";
		default:
			return "customBlocks";
		}
	}
}

package mariculture.core.blocks;

import mariculture.core.lib.SingleMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
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
			name = "airpump";
			break;
		case SingleMeta.FISH_FEEDER:
			name = "feeder";
			break;
		case SingleMeta.NET:
			name = "net";
			break;
		case SingleMeta.TURBINE_WATER:
			name = "turbine";
			break;
		case SingleMeta.FLUDD_STAND:
			name = "fludd";
			break;
		case SingleMeta.TURBINE_GAS:
			name = "turbineGas";
			break;
		case SingleMeta.GEYSER:
			name = "geyser";
		default:
			name = "customBlocks";
		}

		return name;
	}
}

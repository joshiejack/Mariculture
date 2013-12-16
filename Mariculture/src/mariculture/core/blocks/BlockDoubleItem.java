package mariculture.core.blocks;

import mariculture.core.lib.DoubleMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockDoubleItem extends ItemBlockMariculture {
	public BlockDoubleItem(int i, Block block) {
		super(i);
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case DoubleMeta.AIR_COMPRESSOR:
			name = "airCompressor";
			break;
		case DoubleMeta.AIR_COMPRESSOR_POWER:
			name = "airCompressorPower";
			break;
		case DoubleMeta.PRESSURE_VESSEL:
			name = "pressureVessel";
			break;
		default:
			name = "airMachine";
		}

		return name;
	}
}

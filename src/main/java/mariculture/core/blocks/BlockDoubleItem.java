package mariculture.core.blocks;

import mariculture.core.lib.DoubleMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockDoubleItem extends ItemBlockMariculture {
	public BlockDoubleItem(int i, Block block) {
		super(i);
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case DoubleMeta.COMPRESSOR_BASE:
			return "airCompressor";
		case DoubleMeta.COMPRESSOR_TOP:
			return "airCompressorPower";
		case DoubleMeta.PRESSURE_VESSEL:
			return "pressureVessel";
		case DoubleMeta.VAT:
			return "vat";
		default:
			name = "doubleBlocks";
		}

		return name;
	}
}

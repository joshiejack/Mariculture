package mariculture.core.blocks;

import mariculture.core.lib.MultiMeta;
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
		case MultiMeta.COMPRESSOR_BASE:
			return "airCompressor";
		case MultiMeta.COMPRESSOR_TOP:
			return "airCompressorPower";
		case MultiMeta.PRESSURE_VESSEL:
			return "pressureVessel";
		case MultiMeta.VAT:
			return "vat";
		default:
			name = "doubleBlocks";
		}

		return name;
	}
}

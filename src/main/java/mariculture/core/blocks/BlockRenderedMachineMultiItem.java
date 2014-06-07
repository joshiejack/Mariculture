package mariculture.core.blocks;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.MachineRenderedMultiMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockRenderedMachineMultiItem extends ItemBlockMariculture {
	public BlockRenderedMachineMultiItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
			case MachineRenderedMultiMeta.COMPRESSOR_BASE:	return "airCompressor";
			case MachineRenderedMultiMeta.COMPRESSOR_TOP: 	return "airCompressorPower";
			case MachineRenderedMultiMeta.PRESSURE_VESSEL: 	return "pressureVessel";
			case MachineRenderedMultiMeta.VAT: 				return "vat";
			case MachineRenderedMultiMeta.SIFTER: 				return "sifter";
			default: 										return "doubleBlocks";
		}
	}
}

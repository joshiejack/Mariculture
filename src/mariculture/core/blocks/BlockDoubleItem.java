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
			return "airCompressor";
		case DoubleMeta.AIR_COMPRESSOR_POWER:
			return "airCompressorPower";
		case DoubleMeta.PRESSURE_VESSEL:
			return "pressureVessel";
		case DoubleMeta.FORGE:
			return "forge";
		default:
			name = "airMachine";
		}

		return name;
	}
}

package mariculture.core.blocks;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.MachineMultiMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockMachineMultiItem extends ItemBlockMariculture {
	public BlockMachineMultiItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
			case MachineMultiMeta.CRUCIBLE: 	return "crucible";
			case MachineMultiMeta.INCUBATOR_BASE: return "incubatorBase";
			case MachineMultiMeta.INCUBATOR_TOP: return "incubatorTop";
			default: return null;
		}
	}
}

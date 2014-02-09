package mariculture.core.blocks;

import mariculture.core.lib.AirMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockAirItem extends ItemBlockMariculture {
	public BlockAirItem(int id, Block block) {
		super(id);
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case AirMeta.NATURAL_GAS:
			return "naturalGas";
		case AirMeta.FAKE_AIR:
			return "air";
		default:
			return "air";
		}
	}
}
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
	public String getName(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
		case AirMeta.NATURAL_GAS:
			name = "naturalGas";
			break;
		case AirMeta.FAKE_AIR:
			name = "air";
			break;

		default:
			name = "air";
		}

		return name;
	}
}
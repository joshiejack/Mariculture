package mariculture.core.blocks;

import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockTransparentItem extends ItemBlockMariculture {
	public BlockTransparentItem(int id, Block block) {
		super(id);
	}

	@Override
	public String getName(ItemStack itemstack) {
		switch (itemstack.getItemDamage()) {
		case GlassMeta.PLASTIC:
			return "plastic";
		case GlassMeta.HEAT:
			return "heatglass";
		default:
			return "plastic";
		}
	}
}
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
		String name = "";
		switch (itemstack.getItemDamage()) {
		case GlassMeta.PLASTIC:
			name = "plastic";
			break;

		default:
			name = "plastic";
		}

		return name;
	}
}
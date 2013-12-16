package mariculture.core.blocks;

import mariculture.core.lib.OresMeta;
import mariculture.core.lib.TankMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockTankItem extends ItemBlockMariculture {
	public BlockTankItem(int id, Block block) {
		super(id);
	}

	@Override
	public String getName(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
		case TankMeta.FISH:
			return "fish";
		case TankMeta.TANK:
			return "normal";
		default:
			return "tank";
		}
	}
}
package mariculture.core.blocks;

import mariculture.core.lib.LimestoneMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockLimestoneItem extends ItemBlockMariculture {
	public BlockLimestoneItem(int id, Block block) {
		super(id);
	}
	
	@Override 
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();
		if (meta < LimestoneMeta.PILLAR_1) return super.getUnlocalizedName(stack);
		else {
			if(meta < LimestoneMeta.PEDESTAL_1) return "tile.limestone.pillar";
			else return "tile.limestone.pedestal";
		}
	}

	@Override
	public String getName(ItemStack stack) {
		switch(stack.getItemDamage()) {
			case LimestoneMeta.SMALL_BRICK: return "brickSmall";
			case LimestoneMeta.CHISELED:	return "chiseled";
			case LimestoneMeta.PILLAR_1:	return "pillar1";
			case LimestoneMeta.PILLAR_2:	return "pillar2";
			case LimestoneMeta.PILLAR_3:	return "pillar3";
			case LimestoneMeta.PEDESTAL_1:	return "pedestal1";
			case LimestoneMeta.PEDESTAL_2:	return "pedestal2";
			case LimestoneMeta.PEDESTAL_3:	return "pedestal3";
			case LimestoneMeta.PEDESTAL_4:	return "pedestal4";
			case LimestoneMeta.PEDESTAL_5:	return "pedestal5";
			case LimestoneMeta.PEDESTAL_6:	return "pedestal6";
			default: return null;
		}
	}
}

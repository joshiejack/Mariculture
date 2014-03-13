package mariculture.core.blocks;

import mariculture.core.lib.LimestoneMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockLimestoneItem extends ItemBlockMariculture {
	public BlockLimestoneItem(Block block) {
		super(block);
	}
	
	@Override 
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getItemDamage();
		if (meta < LimestoneMeta.PILLAR_1) return super.getUnlocalizedName(stack);
		else {
			if(meta < LimestoneMeta.PEDESTAL_1) return "tile.limestone.limestonePillar";
			else return "tile.limestone.limestonePedestal";
		}
	}

	@Override
	public String getName(ItemStack stack) {
		switch(stack.getItemDamage()) {
			case LimestoneMeta.RAW: 		return "limestoneRaw";
			case LimestoneMeta.SMOOTH: 		return "limestoneSmooth";
			case LimestoneMeta.BRICK: 		return "limestoneBrick";
			case LimestoneMeta.SMALL_BRICK: return "limestoneBrickSmall";
			case LimestoneMeta.THIN_BRICK: 	return "limestoneBrickThin";
			case LimestoneMeta.BORDERED: 	return "limestoneBordered";
			case LimestoneMeta.CHISELED:	return "limestoneChiseled";
			case LimestoneMeta.PILLAR_1:	return "limestonePillar1";
			case LimestoneMeta.PILLAR_2:	return "limestonePillar2";
			case LimestoneMeta.PILLAR_3:	return "limestonePillar3";
			case LimestoneMeta.PEDESTAL_1:	return "limestonePedestal1";
			case LimestoneMeta.PEDESTAL_2:	return "limestonePedestal2";
			case LimestoneMeta.PEDESTAL_3:	return "limestonePedestal3";
			case LimestoneMeta.PEDESTAL_4:	return "limestonePedestal4";
			case LimestoneMeta.PEDESTAL_5:	return "limestonePedestal5";
			case LimestoneMeta.PEDESTAL_6:	return "limestonePedestal6";
			default: return null;
		}
	}
}

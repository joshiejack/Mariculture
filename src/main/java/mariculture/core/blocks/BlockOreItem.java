package mariculture.core.blocks;

import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockOreItem extends ItemBlockMariculture {
	public BlockOreItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
			case OresMeta.BAUXITE: 		   return "bauxiteOre";
			case OresMeta.COPPER: 		   return "copperOre";
			case OresMeta.RUTILE: 		   return "rutileOre";
			case OresMeta.CORAL_ROCK: 	   return "coralRock";
			case OresMeta.ALUMINUM_BLOCK:  return "aluminumBlock";
			case OresMeta.TITANIUM_BLOCK:  return "titaniumBlock";
			case OresMeta.MAGNESIUM_BLOCK: return "magnesiumBlock";
			case OresMeta.RUTILE_BLOCK:    return "rutileBlock";
			case OresMeta.COPPER_BLOCK:    return "copperBlock";
			case OresMeta.BASE_BRICK: 	   return "baseBrick";
			case OresMeta.BASE_IRON: 	   return "baseIron";
			default: 					   return null;
		}
	}
}
package mariculture.core.blocks;

import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockOreItem extends ItemBlockMariculture {
	public BlockOreItem(int id, Block block) {
		super(id);
	}

	@Override
	public String getName(ItemStack itemstack) {
		String name = "";
		switch (itemstack.getItemDamage()) {
		case OresMeta.BAUXITE:
			return "bauxiteOre";
		case OresMeta.COPPER:
			return "copperOre";
		case OresMeta.RUTILE:
			return "rutileOre";
		case OresMeta.LIMESTONE:
			return "limestone";
		case OresMeta.LIMESTONE_BRICK:
			return "limestoneBrick";
		case OresMeta.CORAL_ROCK:
			return "coralRock";
		case OresMeta.ALUMINUM_BLOCK:
			return "aluminumBlock";
		case OresMeta.TITANIUM_BLOCK:
			return "titaniumBlock";
		case OresMeta.MAGNESIUM_BLOCK:
			return "magnesiumBlock";
		case OresMeta.COPPER_BLOCK:
			return "copperBlock";
		case OresMeta.LIMESTONE_SMOOTH:
			return "limestoneSmooth";
		case OresMeta.LIMESTONE_CHISELED:
			return "limestoneBordered";
		case OresMeta.BASE_BRICK:
			return "baseBrick";
		case OresMeta.BASE_IRON:
			return "baseIron";
		case OresMeta.LIMESTONE_THIN:
			return "limestoneThing";
		case OresMeta.RUTILE_BLOCK:
			return "rutileBlock";
		default:
			return "baseWood";
		}
	}
}
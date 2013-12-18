package mariculture.core.blocks;

import mariculture.core.lib.OresMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
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
			name = "bauxiteOre";
			break;
		case OresMeta.COPPER:
			name = "copperOre";
			break;
		case OresMeta.RUTILE:
			name = "rutileOre";
			break;
		case OresMeta.LIMESTONE:
			name = "limestone";
			break;
		case OresMeta.LIMESTONE_BRICK:
			name = "limestoneBrick";
			break;
		case OresMeta.CORAL_ROCK:
			name = "coralRock";
			break;
		case OresMeta.ALUMINUM_BLOCK:
			name = "aluminumBlock";
			break;
		case OresMeta.TITANIUM_BLOCK:
			name = "titaniumBlock";
			break;
		case OresMeta.MAGNESIUM_BLOCK:
			name = "magnesiumBlock";
			break;
		case OresMeta.COPPER_BLOCK:
			name = "copperBlock";
			break;
		case OresMeta.LIMESTONE_SMOOTH:
			name = "limestoneSmooth";
			break;
		case OresMeta.LIMESTONE_CHISELED:
			name = "limestoneBordered";
			break;
		case OresMeta.BASE_BRICK:
			name = "baseBrick";
			break;
		case OresMeta.BASE_IRON:
			name = "baseIron";
			break;

		default:
			name = "baseWood";
		}

		return name;
	}
}
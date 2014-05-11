package mariculture.aesthetics;

import mariculture.core.Core;
import mariculture.core.blocks.BlockPearlBrick;
import mariculture.core.blocks.BlockPearlBrickItem;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class Aesthetics extends RegistrationModule {
	public static Block pearlBrick;
	public static Block limestone;
		
	@Override
	public void registerHandlers() {
		return;
	}

	@Override
	public void registerBlocks() {
		pearlBrick = new BlockPearlBrick(BlockIds.pearlBricks, "pearlBrick_").setUnlocalizedName("pearlBrick");
		limestone = new BlockLimestone(BlockIds.limestone).setStepSound(Block.soundStoneFootstep).setResistance(1F).setUnlocalizedName("limestone");
		
		Item.itemsList[BlockIds.pearlBricks] = new BlockPearlBrickItem(BlockIds.pearlBricks - 256, pearlBrick).setUnlocalizedName("pearlBrick");
		Item.itemsList[BlockIds.limestone] = new BlockLimestoneItem(BlockIds.limestone - 256, limestone).setUnlocalizedName("limestone");
		
		MinecraftForge.setBlockHarvestLevel(limestone, OreDictionary.WILDCARD_VALUE, "pickaxe", 0);
		MinecraftForge.setBlockHarvestLevel(pearlBrick, OreDictionary.WILDCARD_VALUE, "pickaxe", 1);
		
		RegistryHelper.register(new Object[] { limestone, pearlBrick });
	}

	@Override
	public void registerItems() {
		return;
	}

	@Override
	public void registerOther() {
		return;
	}

	@Override
	public void registerRecipes() {
		for (int i = 0; i < 12; i++) {
			RecipeHelper.add2x2Recipe(new ItemStack(pearlBrick, 4, i), new ItemStack(Core.pearl, 1, i));
		}
		
		RecipeHelper.add4x4Recipe(new ItemStack(Core.ores, 4, OresMeta.LIMESTONE_BRICK), Core.ores, OresMeta.LIMESTONE);
		RecipeHelper.add4x4Recipe(new ItemStack(Core.ores, 4, OresMeta.LIMESTONE_CHISELED), Core.ores, OresMeta.LIMESTONE_SMOOTH);
		RecipeHelper.add4x4Recipe(new ItemStack(limestone, 4, LimestoneMeta.SMALL_BRICK), Core.ores, OresMeta.LIMESTONE_BRICK);
		RecipeHelper.add4x4Recipe(new ItemStack(limestone, 4, LimestoneMeta.CHISELED), Core.ores, OresMeta.LIMESTONE_CHISELED);
		RecipeHelper.addSmelting(Core.ores.blockID, OresMeta.LIMESTONE, new ItemStack(Core.ores, 1, OresMeta.LIMESTONE_SMOOTH), 0.1F);
		RecipeHelper.addShapedRecipe(new ItemStack(Core.ores, 4, OresMeta.LIMESTONE_THIN), new Object[] {
			"XY", "YX", 'X', new ItemStack(Core.ores, 1, OresMeta.LIMESTONE_BRICK), 'Y', new ItemStack(limestone, 1, LimestoneMeta.SMALL_BRICK)
		});

		RecipeHelper.addShapedRecipe(new ItemStack(limestone, 2, LimestoneMeta.PILLAR_1), new Object[] {
			"X", "X", 'X', new ItemStack(Core.ores, 1, OresMeta.LIMESTONE_SMOOTH)
		});

		RecipeHelper.addShapedRecipe(new ItemStack(limestone, 2, LimestoneMeta.PEDESTAL_1), new Object[] {
			"X", "Y", 'X', new ItemStack(limestone, 1, LimestoneMeta.PILLAR_1), 'Y', new ItemStack(Core.ores, 1, OresMeta.LIMESTONE_CHISELED)
		});
	}
}

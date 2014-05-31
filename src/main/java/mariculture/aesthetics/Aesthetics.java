package mariculture.aesthetics;

import static mariculture.core.helpers.RecipeHelper.add4x4Recipe;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.addSmelting;
import mariculture.core.Core;
import mariculture.core.blocks.BlockPearlBlock;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class Aesthetics extends RegistrationModule {
	public static Block pearlBrick;
		
	@Override
	public void registerHandlers() {
		return;
	}

	@Override
	public void registerBlocks() {
		pearlBrick = new BlockPearlBlock("pearlBrick_").setStepSound(Block.soundTypeStone).setResistance(2F).setBlockName("pearl.brick");
		RegistryHelper.registerBlocks(new Block[] { pearlBrick });
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
			add4x4Recipe(new ItemStack(pearlBrick, 4, i), new ItemStack(Core.pearlBlock, 1, i));
		}
		
		add4x4Recipe(new ItemStack(Core.limestone, 4, LimestoneMeta.BRICK), Core.limestone, LimestoneMeta.RAW);
		add4x4Recipe(new ItemStack(Core.limestone, 4, LimestoneMeta.BORDERED), Core.limestone, LimestoneMeta.SMOOTH);
		add4x4Recipe(new ItemStack(Core.limestone, 4, LimestoneMeta.SMALL_BRICK), Core.limestone, LimestoneMeta.BRICK);
		add4x4Recipe(new ItemStack(Core.limestone, 4, LimestoneMeta.CHISELED), Core.limestone, LimestoneMeta.BORDERED);
		addSmelting(new ItemStack(Core.limestone, 1, LimestoneMeta.SMOOTH), new ItemStack(Core.limestone, 1, LimestoneMeta.RAW), 0.1F);
		addShaped(new ItemStack(Core.limestone, 4, LimestoneMeta.THIN_BRICK), new Object[] {
			"XY", "YX", 'X', new ItemStack(Core.limestone, 1, LimestoneMeta.BRICK), 'Y', new ItemStack(Core.limestone, 1, LimestoneMeta.SMALL_BRICK)
		});
		
		addShaped(new ItemStack(Core.limestone, 2, LimestoneMeta.PILLAR_1), new Object[] {
			"X", "X", 'X', new ItemStack(Core.limestone, 1, LimestoneMeta.SMOOTH)
		});
		
		addShaped(new ItemStack(Core.limestone, 2, LimestoneMeta.PEDESTAL_1), new Object[] {
			"X", "Y", 'X', new ItemStack(Core.limestone, 1, LimestoneMeta.PILLAR_1), 'Y', new ItemStack(Core.limestone, 1, LimestoneMeta.BORDERED)
		});
	}
}

package mariculture.aesthetics;

import static mariculture.core.helpers.RecipeHelper.add4x4Recipe;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.addSmelting;
import static mariculture.core.helpers.RecipeHelper.asStack;
import mariculture.core.Core;
import mariculture.core.blocks.BlockPearlBlock;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.lib.helpers.RegistryHelper;
import net.minecraft.block.Block;

public class Aesthetics extends RegistrationModule {
    public static Block pearlBrick;

    @Override
    public void registerHandlers() {
        return;
    }

    @Override
    public void registerBlocks() {
        pearlBrick = new BlockPearlBlock("pearlBrick_").setStepSound(Block.soundTypeStone).setResistance(2F).setBlockName("pearl.brick");
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
            add4x4Recipe(asStack(pearlBrick, 4, i), asStack(Core.pearlBlock, i));
        }

        add4x4Recipe(asStack(Core.limestone, 4, LimestoneMeta.BRICK), Core.limestone, LimestoneMeta.RAW);
        add4x4Recipe(asStack(Core.limestone, 4, LimestoneMeta.BORDERED), Core.limestone, LimestoneMeta.SMOOTH);
        add4x4Recipe(asStack(Core.limestone, 4, LimestoneMeta.SMALL_BRICK), Core.limestone, LimestoneMeta.BRICK);
        add4x4Recipe(asStack(Core.limestone, 4, LimestoneMeta.CHISELED), Core.limestone, LimestoneMeta.BORDERED);
        addSmelting(asStack(Core.limestone, LimestoneMeta.SMOOTH), asStack(Core.limestone, LimestoneMeta.RAW), 0.1F);
        addShaped(asStack(Core.limestone, 4, LimestoneMeta.THIN_BRICK), new Object[] { "XY", "YX", 'X', asStack(Core.limestone, LimestoneMeta.BRICK), 'Y', asStack(Core.limestone, LimestoneMeta.SMALL_BRICK) });
        addShaped(asStack(Core.limestone, 2, LimestoneMeta.PILLAR_1), new Object[] { "X", "X", 'X', asStack(Core.limestone, LimestoneMeta.SMOOTH) });
        addShaped(asStack(Core.limestone, 2, LimestoneMeta.PEDESTAL_1), new Object[] { "X", "Y", 'X', asStack(Core.limestone, LimestoneMeta.PILLAR_1), 'Y', asStack(Core.limestone, LimestoneMeta.BORDERED) });
    }
}

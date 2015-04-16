package mariculture.factory;

import static mariculture.core.helpers.RecipeHelper.addMelting;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.addShapeless;
import static mariculture.core.helpers.RecipeHelper.asStack;
import static mariculture.core.lib.MCLib.autodictionary;
import static mariculture.core.lib.MCLib.baseIron;
import static mariculture.core.lib.MCLib.baseWood;
import static mariculture.core.lib.MCLib.bookAndQuill;
import static mariculture.core.lib.MCLib.craftingTable;
import static mariculture.core.lib.MCLib.feather;
import static mariculture.core.lib.MCLib.filterer;
import static mariculture.core.lib.MCLib.fishSorter;
import static mariculture.core.lib.MCLib.geyser;
import static mariculture.core.lib.MCLib.ironAxe;
import static mariculture.core.lib.MCLib.paper;
import static mariculture.core.lib.MCLib.pearls;
import static mariculture.core.lib.MCLib.plan;
import static mariculture.core.lib.MCLib.sawmill;
import static mariculture.core.lib.MCLib.unpacker;
import static mariculture.core.lib.MCLib.water;
import static mariculture.core.lib.MCLib.wicker;
import static mariculture.core.lib.MCLib.wool;
import mariculture.core.handlers.FluidDicHandler;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.PlansMeta;
import mariculture.core.util.Fluids;
import mariculture.factory.blocks.BlockCustomBlock;
import mariculture.factory.blocks.BlockCustomFence;
import mariculture.factory.blocks.BlockCustomFlooring;
import mariculture.factory.blocks.BlockCustomGate;
import mariculture.factory.blocks.BlockCustomLight;
import mariculture.factory.blocks.BlockCustomPower;
import mariculture.factory.blocks.BlockCustomRFWall;
import mariculture.factory.blocks.BlockCustomSlab;
import mariculture.factory.blocks.BlockCustomStairs;
import mariculture.factory.blocks.BlockCustomWall;
import mariculture.factory.items.ItemChalk;
import mariculture.factory.items.ItemFilter;
import mariculture.factory.items.ItemPaintbrush;
import mariculture.factory.items.ItemPlan;
import mariculture.factory.tile.TileCustom;
import mariculture.factory.tile.TileCustomPowered;
import mariculture.factory.tile.TileDictionaryFluid;
import mariculture.factory.tile.TileDictionaryItem;
import mariculture.factory.tile.TileFishSorter;
import mariculture.factory.tile.TileGeyser;
import mariculture.factory.tile.TileSawmill;
import mariculture.factory.tile.TileUnpacker;
import mariculture.lib.helpers.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class Factory extends RegistrationModule {
    public static Block customFlooring;
    public static Block customBlock;
    public static Block customStairs;
    public static Block customSlabs;
    public static Block customSlabsDouble;
    public static Block customFence;
    public static Block customGate;
    public static Block customWall;
    public static Block customLight;
    public static Block customRFBlock;
    public static Block customRFWall;

    public static Item chalk;
    public static Item plans;
    public static Item paintbrush;
    public static Item filter;

    @Override
    public void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new FactoryEvents());
    }

    @Override
    public void registerBlocks() {
        customFlooring = new BlockCustomFlooring().setStepSound(Block.soundTypePiston).setBlockName("customFlooring");
        customBlock = new BlockCustomBlock().setStepSound(Block.soundTypePiston).setBlockName("customBlock");
        customStairs = new BlockCustomStairs(0).setStepSound(Block.soundTypePiston).setBlockName("customStairs");
        customSlabs = new BlockCustomSlab(false).setStepSound(Block.soundTypePiston).setBlockName("customSlabs");
        customSlabsDouble = new BlockCustomSlab(true).setStepSound(Block.soundTypePiston).setBlockName("customSlabsDouble");
        customFence = new BlockCustomFence().setStepSound(Block.soundTypePiston).setBlockName("customFence");
        customGate = new BlockCustomGate().setStepSound(Block.soundTypePiston).setBlockName("customGate");
        customWall = new BlockCustomWall().setStepSound(Block.soundTypePiston).setBlockName("customWall");
        customLight = new BlockCustomLight().setStepSound(Block.soundTypePiston).setBlockName("customLight").setLightLevel(1.0F);
        customRFBlock = new BlockCustomPower().setStepSound(Block.soundTypePiston).setBlockName("customRFBlock");
        customRFWall = new BlockCustomRFWall().setStepSound(Block.soundTypePiston).setBlockName("customRFWall");

        RegistryHelper.registerBlocks(new Block[] { customStairs, customSlabs, customFence, customGate, customWall, customSlabsDouble, customRFWall });
        RegistryHelper.registerTiles("Mariculture", TileCustom.class, TileCustomPowered.class, TileSawmill.class, TileDictionaryItem.class, TileFishSorter.class, TileGeyser.class, TileDictionaryFluid.class, TileUnpacker.class);
    }

    @Override
    public void registerItems() {
        chalk = new ItemChalk(64).setUnlocalizedName("chalk");
        plans = new ItemPlan().setUnlocalizedName("plans");
        paintbrush = new ItemPaintbrush(128).setUnlocalizedName("paintbrush");
        filter = new ItemFilter().setUnlocalizedName("filter");
    }

    @Override
    public void registerOther() {
        FluidDicHandler.register("water", "water", 2000);
        FluidDicHandler.register("xp", "xpjuice", 200);
        FluidDicHandler.register("xp", "immibis.liquidxp", 1000);
        FluidDicHandler.register("xp", "mobessence", 666);
    }

    @Override
    public void registerRecipes() {
        addShaped(unpacker, new Object[] { "LLL", "LCL", "LBL", 'L', "logWood", 'B', baseWood, 'C', craftingTable });
        addShaped(sawmill, new Object[] { " A ", "DWD", "IMI", 'A', ironAxe, 'D', "slabWood", 'M', baseWood, 'W', "logWood", 'I', "ingotCopper" });
        addShaped(autodictionary, new Object[] { " B ", "FPF", "IMI", 'F', feather, 'P', pearls, 'M', baseWood, 'B', bookAndQuill, 'I', "ingotCopper" });
        addShaped(fishSorter, new Object[] { "BPY", "GFA", "RCW", 'B', "dyeBlack", 'P', pearls, 'Y', "dyeYellow", 'G', "dyeGreen", 'F', "fish", 'A', "dyeCyan", 'R', "dyeRed", 'C', baseWood, 'W', "dyeWhite" });
        addShaped(asStack(geyser, 16), new Object[] { " W ", " G ", "RCR", 'W', water, 'G', "blockGlass", 'R', "dustRedstone", 'C', baseIron, });
        addShaped(asStack(chalk), new Object[] { "LLN", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { "L  ", "L  ", "N  ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { " L ", " L ", " N ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { " N ", " L ", " L ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { "N  ", "L  ", "L  ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { "N  ", " L ", "  L", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { "L  ", " L ", "  N", 'L', "blockLimestone", 'N', "dyeWhite" });
        addMelting(asStack(chalk), 825, Fluids.getFluidStack("quicklime", 2500));
        addShapeless(plan, new Object[] { "dyeBlue", "dyeBlack", paper, "dyeBlue" });
        addShaped(asStack(filter), new Object[] { "W W", "WNW", " W ", 'W', wicker, 'N', filterer });
        addShaped(asStack(paintbrush), new Object[] { " WW", " IW", "S  ", 'W', wool, 'I', "blockAluminum", 'S', sawmill });
    }
}

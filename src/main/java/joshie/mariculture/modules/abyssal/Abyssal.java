package joshie.mariculture.modules.abyssal;

import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.core.util.annotation.MCLoader;
import joshie.mariculture.modules.ModuleManager;
import joshie.mariculture.modules.abyssal.block.BlockLimestone;
import joshie.mariculture.modules.abyssal.block.BlockLimestoneSlab;
import joshie.mariculture.modules.abyssal.block.BlockLimestoneSlab.Type;
import joshie.mariculture.modules.abyssal.gen.WorldGenOverworld;
import joshie.mariculture.core.util.block.BlockStairsMC;
import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.modules.sealife.blocks.BlockPlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.ChunkProviderOverworld;

import static joshie.mariculture.core.helpers.ConfigHelper.*;
import static joshie.mariculture.core.helpers.RecipeHelper.*;
import static joshie.mariculture.modules.abyssal.block.BlockLimestone.Limestone.*;
import static net.minecraft.init.Blocks.SAND;

/** The Abyssal (Seabed/Seafloor) Module is all about changing the ocean floor itself, to make it more interesting
 *  Instead of just the vanilla gravel oceans, it adds a limestone top, covered by sand,
 *  It also will add more interesting features, like deep sea trents, caves, and hydrothermal vents  */
@MCLoader
public class Abyssal {
    public static final BlockLimestone LIMESTONE = new BlockLimestone().register("limestone");
    private static BlockStairsMC LIMESTONE_STAIRS_RAW;
    private static BlockStairsMC LIMESTONE_STAIRS_SMOOTH;
    private static BlockStairsMC LIMESTONE_STAIRS_BRICK;
    private static BlockStairsMC LIMESTONE_STAIRS_SMALL_BRICK;
    private static BlockStairsMC LIMESTONE_STAIRS_THIN_BRICK;
    private static BlockStairsMC LIMESTONE_STAIRS_BORDERED;
    public static BlockLimestoneSlab LIMESTONE_SLAB;
    public static BlockLimestoneSlab LIMESTONE_SLAB_DOUBLE;

    public static void preInit() {
        MCTab.getCore().setStack(Abyssal.LIMESTONE.getStackFromEnum(RAW));
        if (LIMESTONE_STAIRS) {
            LIMESTONE_STAIRS_RAW = new BlockStairsMC(LIMESTONE.getStateFromEnum(RAW)).register("limestone_raw_stairs");
            LIMESTONE_STAIRS_SMOOTH = new BlockStairsMC(LIMESTONE.getStateFromEnum(SMOOTH)).register("limestone_smooth_stairs");
            LIMESTONE_STAIRS_BRICK = new BlockStairsMC(LIMESTONE.getStateFromEnum(BRICK)).register("limestone_brick_stairs");
            LIMESTONE_STAIRS_SMALL_BRICK = new BlockStairsMC(LIMESTONE.getStateFromEnum(SMALL_BRICK)).register("limestone_smallbrick_stairs");
            LIMESTONE_STAIRS_THIN_BRICK = new BlockStairsMC(LIMESTONE.getStateFromEnum(THIN_BRICK)).register("limestone_thinbrick_stairs");
            LIMESTONE_STAIRS_BORDERED = new BlockStairsMC(LIMESTONE.getStateFromEnum(BORDERED)).register("limestone_bordered_stairs");
        }

        if (LIMESTONE_SLABS) {
            LIMESTONE_SLAB = new BlockLimestoneSlab.Half().registerWithoutItem("limestone_slab");
            LIMESTONE_SLAB_DOUBLE = new BlockLimestoneSlab.Double().registerWithoutItem("limestone_slab_double");
            LIMESTONE_SLAB.getItemBlock().register("limestone_slab");
            LIMESTONE.getItemBlock().register("limestone_slab_double");
        }

        if (OCEAN_REPLACE) MaricultureAPI.worldGen.registerWorldGenHandler(ChunkProviderOverworld.class, new WorldGenOverworld());

    }

    //Add recipes for all the limestone variants
    public static void init() {
        addSmelting(LIMESTONE.getStackFromEnum(SMOOTH), LIMESTONE.getStackFromEnum(RAW), 0.1F);
        add4x4Recipe(LIMESTONE.getStackFromEnum(BRICK), LIMESTONE.getStackFromEnum(RAW));
        add4x4Recipe(LIMESTONE.getStackFromEnum(BORDERED), LIMESTONE.getStackFromEnum(SMOOTH));
        add4x4Recipe(LIMESTONE.getStackFromEnum(SMALL_BRICK), LIMESTONE.getStackFromEnum(BRICK));
        add4x4Recipe(LIMESTONE.getStackFromEnum(CHISELED), LIMESTONE.getStackFromEnum(BORDERED));
        addShaped(LIMESTONE.getStackFromEnum(THIN_BRICK), "XY", "YX", 'X', LIMESTONE.getStackFromEnum(BRICK), 'Y', LIMESTONE.getStackFromEnum(SMALL_BRICK));
        addShaped(LIMESTONE.getStackFromEnum(PILLAR_1), "X", "X", 'X', LIMESTONE.getStackFromEnum(SMOOTH));
        addShaped(LIMESTONE.getStackFromEnum(PEDESTAL_1), "X", "Y", 'X', LIMESTONE.getStackFromEnum(PILLAR_1), 'Y', LIMESTONE.getStackFromEnum(BORDERED));

        //Add Stair Recipe if stairs were enabled
        if (LIMESTONE_STAIRS) {
            addStairRecipe(LIMESTONE_STAIRS_RAW.getStack(4), LIMESTONE.getStackFromEnum(RAW));
            addStairRecipe(LIMESTONE_STAIRS_SMOOTH.getStack(4), LIMESTONE.getStackFromEnum(SMOOTH));
            addStairRecipe(LIMESTONE_STAIRS_BRICK.getStack(4), LIMESTONE.getStackFromEnum(BRICK));
            addStairRecipe(LIMESTONE_STAIRS_SMALL_BRICK.getStack(4), LIMESTONE.getStackFromEnum(SMALL_BRICK));
            addStairRecipe(LIMESTONE_STAIRS_THIN_BRICK.getStack(4), LIMESTONE.getStackFromEnum(THIN_BRICK));
            addStairRecipe(LIMESTONE_STAIRS_BORDERED.getStack(4), LIMESTONE.getStackFromEnum(BORDERED));
        }

        //Add Slab Recipe if slabs were enable
        if (LIMESTONE_SLABS) {
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.RAW, 6), LIMESTONE.getStackFromEnum(RAW));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.SMOOTH, 6), LIMESTONE.getStackFromEnum(SMOOTH));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.BRICK, 6), LIMESTONE.getStackFromEnum(BRICK));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.SMALL_BRICK, 6), LIMESTONE.getStackFromEnum(SMALL_BRICK));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.THIN_BRICK, 6), LIMESTONE.getStackFromEnum(THIN_BRICK));
            addSlabRecipe(LIMESTONE_SLAB.getStackFromEnum(Type.BORDERED, 6), LIMESTONE.getStackFromEnum(BORDERED));
        }

        //Cross Module Support
        if (ModuleManager.isModuleEnabled("sealife")) BlockPlant.FLOOR_BLOCKS.add(LIMESTONE);
    }

    //Configuration options
    public static boolean OCEAN_REPLACE;
    public static int OCEAN_FILLER_DEPTH;
    public static IBlockState OCEAN_FILLER;
    public static IBlockState OCEAN_SURFACE;
    public static boolean LIMESTONE_STAIRS;
    public static boolean LIMESTONE_SLABS;
    public static boolean DEEP_SEA_TRENCHES;
    public static int DEEP_SEA_MAX_HEIGHT;
    public static int DEEP_SEA_RARITY;
    public static int DEEP_SEA_LENGTH;
    public static float DEEP_SEA_WIDTH;
    public static float DEEP_SEA_WIDTH_2;
    public static float DEEP_SEA_WIDTH_3;
    public static double DEEP_SEA_DEPTH;

    public static void configure() {
        OCEAN_REPLACE = getBoolean("Replace Ocean Blocks", true);
        OCEAN_FILLER_DEPTH = getInteger("Ocean Filler Depth", 5) * 2; //To make it fit the actual input value
        OCEAN_FILLER = getBlockState("Ocean Filler Block", LIMESTONE.getDefaultState());
        OCEAN_SURFACE = getBlockState("Ocean Surface Block", SAND.getDefaultState());
        LIMESTONE_STAIRS = getBoolean("Enable Limestone Stairs", true);
        LIMESTONE_SLABS = getBoolean("Enable Limestone Slabs", true);

        DEEP_SEA_TRENCHES = getBoolean("Deep Sea Trenches > Enable", true);
        DEEP_SEA_MAX_HEIGHT = getInteger("Deep Sea Trenches > Maximum Floor Height", 36, "When generating, trenches pick a random block, and will only generate if the block is this number or lower");
        DEEP_SEA_RARITY = getRandomInteger("Deep Sea Trenches > Rarity", 300, "The higher the number, the rarer the trenches will be");
        DEEP_SEA_LENGTH = getInteger("Deep Sea Trenches > Length", 12);
        DEEP_SEA_WIDTH = getFloat("Deep Sea Trenches > Width", 5F);
        DEEP_SEA_WIDTH_2 = DEEP_SEA_WIDTH * 2F;
        DEEP_SEA_WIDTH_3 = DEEP_SEA_WIDTH / 4F;
        DEEP_SEA_DEPTH = getDouble("Deep Sea Trenches > Depth", 3.25D);
    }
}

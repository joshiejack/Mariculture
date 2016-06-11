package joshie.mariculture.modules.abyssal;

import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.abyssal.block.BlockLimestone;
import net.minecraft.block.state.IBlockState;

import static joshie.mariculture.helpers.ConfigHelper.getBlockState;
import static joshie.mariculture.helpers.ConfigHelper.getBoolean;
import static joshie.mariculture.helpers.ConfigHelper.getInteger;
import static joshie.mariculture.helpers.RecipeHelper.*;
import static joshie.mariculture.modules.abyssal.block.BlockLimestone.Type.*;
import static joshie.mariculture.util.MCTab.getTab;
import static net.minecraft.init.Blocks.SAND;

/** The Abyssal (Seabed/Seafloor) Module is all about changing the ocean floor itself, to make it more interesting
 *  Instead of just the vanilla gravel oceans, it adds a limestone top, covered by sand,
 *  It also will add more interesting features, like deep sea trents, caves, and hydrothermal vents  */
@Module(name = "abyssal")
public class Abyssal {
    public static final BlockLimestone LIMESTONE = new BlockLimestone().setUnlocalizedName("limestone");

    public static void preInit() {
        getTab("core").setStack(Abyssal.LIMESTONE.getStackFromEnum(RAW));
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
    }

    //Configuration options
    public static boolean OCEAN_REPLACE;
    public static int OCEAN_FILLER_DEPTH;
    public static IBlockState OCEAN_FILLER;
    public static IBlockState OCEAN_SURFACE;

    public static void configure() {
        OCEAN_REPLACE = getBoolean("Replace Ocean Blocks", true);
        OCEAN_FILLER_DEPTH = getInteger("Ocean Filler Depth", 5) * 2; //To make it fit the actual input value
        OCEAN_FILLER = getBlockState("Ocean Filler Block", LIMESTONE.getDefaultState());
        OCEAN_SURFACE = getBlockState("Ocean Surface Block", SAND.getDefaultState());
    }
}

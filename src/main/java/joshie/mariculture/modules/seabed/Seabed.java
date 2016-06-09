package joshie.mariculture.modules.seabed;

import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.core.Core;

import static joshie.mariculture.helpers.RecipeHelper.add4x4Recipe;
import static joshie.mariculture.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.helpers.RecipeHelper.addSmelting;
import static joshie.mariculture.modules.seabed.BlockLimestone.Type.*;
import static joshie.mariculture.modules.seabed.BlockLimestone.Type.BORDERED;

/** The Seabed Module is all about changing the ocean floor itself, to make it more interesting
 *  Instead of just the vanilla gravel oceans, it adds a limestone top, covered by sand,
 *  It also will add more interesting features, like deep sea trents, caves, and hydrothermal vents  */
@Module(name = "seabed")
public class Seabed {
    public static final BlockLimestone LIMESTONE = new BlockLimestone().setUnlocalizedName("limestone");

    public static void preInit() {
        Core.TAB.setStack(Seabed.LIMESTONE.getStackFromEnum(RAW));
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
}

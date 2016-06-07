package joshie.mariculture.modules.core;

import joshie.mariculture.handlers.GuiHandler;
import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.core.blocks.BlockLimestone;
import joshie.mariculture.util.BlockMCEnum;
import joshie.mariculture.util.MCTab;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static joshie.mariculture.Mariculture.instance;
import static joshie.mariculture.helpers.RecipeHelper.addSmelting;
import static joshie.mariculture.modules.core.blocks.BlockLimestone.Type.RAW;
import static joshie.mariculture.modules.core.blocks.BlockLimestone.Type.SMOOTH;

/** The Core Module contains the basic ingredients for every other module to operate,
 * 	It cannot be disabled **/
@Module(name = "core")
public class Core  {
    public static final MCTab TAB = new MCTab("core");
    public static final BlockMCEnum LIMESTONE = new BlockLimestone().setUnlocalizedName("limestone");

    //Make the default tab have the limestone as it's icon
    public static void preInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        TAB.setStack(LIMESTONE.getStackFromEnum(RAW));
    }

    //Add a smelting recipe for raw to smooth limestone
    public static void postInit() {
        addSmelting(LIMESTONE.getStackFromEnum(RAW), LIMESTONE.getStackFromEnum(SMOOTH), 0.1F);
    }
}

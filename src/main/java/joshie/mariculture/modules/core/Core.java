package joshie.mariculture.modules.core;

import joshie.mariculture.api.MaricultureAPI;
import joshie.mariculture.handlers.GuiHandler;
import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.fishery.FishingAPI;
import joshie.mariculture.util.MCTab;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static joshie.mariculture.Mariculture.instance;

/** The Core Module contains the basic ingredients for every other module to operate,
 * 	It cannot be disabled **/
@Module(name = "core")
public class Core  {
    public static final MCTab TAB = new MCTab("core");

    //This is called before anything else, and therefore is the best place to create the API
    public static void configure() {
        MaricultureAPI.fishing = new FishingAPI();
    }

    //Make the default tab have the limestone as it's icon
    public static void preInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }
}

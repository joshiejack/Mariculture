package mariculture;

import java.io.File;

import mariculture.api.fishery.Fishing;
import mariculture.core.CommonProxy;
import mariculture.core.Config;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.network.PacketPipeline;
import mariculture.plugins.Plugins;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "Mariculture", name = "Mariculture", dependencies = Plugins.after)
public class Mariculture {
	public static final PacketPipeline packets = new PacketPipeline();
	public static final String modid = "mariculture";

	@SidedProxy(clientSide = "mariculture.core.ClientProxy", serverSide = "mariculture.core.CommonProxy")
	public static CommonProxy proxy;

	@Instance("Mariculture")
	public static Mariculture instance = new Mariculture();
	public static Modules modules = new Modules();
	public static File root;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		root = event.getModConfigurationDirectory();
		Config.setup(root + "/mariculture/");
		for (Module module : Modules.modules) {
			module.preInit();
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		//Fish need to be registered before, the recipes that use them are called
		if (Modules.isActive(Modules.fishery)) {
			Fishing.fishHelper.registerFishies();
		}

		packets.init();
		for (Module module : Modules.modules) {
			module.init();
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		packets.postInit();
		for (Module module : Modules.modules) {
			module.postInit();
		}

		proxy.setupClient();
	}
}
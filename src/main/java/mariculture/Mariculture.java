package mariculture;

import java.io.File;

import mariculture.api.fishery.Fishing;
import mariculture.core.CommonProxy;
import mariculture.core.config.Config;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.Required;
import mariculture.core.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "Mariculture", name = "Mariculture", dependencies = Required.after)
public class Mariculture {
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
		Config.setup();
		for (Module module : Modules.modules) {
			module.preInit();
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		PacketHandler.init();
		//Fish need to be registered before, the recipes that use them are called
		if (Modules.isActive(Modules.fishery)) {
			Fishing.fishHelper.registerFishies();
		}

		for (Module module : Modules.modules) {
			module.init();
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		for (Module module : Modules.modules) {
			module.postInit();
		}

		proxy.setupClient();
	}
}
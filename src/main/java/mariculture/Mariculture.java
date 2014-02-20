package mariculture;

import java.io.File;

import mariculture.api.core.MaricultureTab;
import mariculture.core.CommonProxy;
import mariculture.core.Config;
import mariculture.core.RecipesSmelting;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Modules;
import mariculture.core.network.Packets;
import mariculture.plugins.Plugins;
import mariculture.plugins.compatibility.Compat;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "Mariculture", name = "Mariculture", version = "1.2.1", dependencies="after:TConstruct;after:Railcraft;after:ExtrabiomesXL;after:Forestry;after:IC2;after:Thaumcraft;after:BiomesOPlenty;after:AWWayofTime")
public class Mariculture {
	public static final String modid = "mariculture";

	@SidedProxy(clientSide = "mariculture.core.ClientProxy", serverSide = "mariculture.core.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance("Mariculture")
	public static Mariculture instance = new Mariculture();
	
	//Root folder
	public static File root;
	
	//Plugins
	public static Plugins plugins = new Plugins();
	public static enum Stage {
		PRE, INIT, POST;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		root = event.getModConfigurationDirectory();
		Config.init(root + "/mariculture/");		
		MaricultureTab.tabMariculture = new MaricultureTab("maricultureTab");
		MaricultureTab.tabFish = (Modules.fishery.isActive())? new MaricultureTab("fishTab"): null;
		MaricultureTab.tabJewelry = (Modules.magic.isActive())? new MaricultureTab("jewelryTab"): null;
		
		plugins.init();
		Modules.core.preInit();
		Modules.diving.preInit();
		Modules.factory.preInit();
		Modules.fishery.preInit();
		Modules.magic.preInit();
		Modules.sealife.preInit();
		Modules.transport.preInit();
		Modules.world.preInit();
		plugins.load(Stage.PRE);
		Compat.preInit();
		proxy.registerKeyBindings();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		plugins.load(Stage.INIT);
		Modules.core.init();
		Modules.diving.init();
		Modules.factory.init();
		Modules.fishery.init();
		Modules.magic.init();
		Modules.sealife.init();
		Modules.transport.init();
		Modules.world.init();
		Compat.init();
		Packets.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		plugins.load(Stage.POST);
		proxy.initClient();
		proxy.loadBooks();
		RecipesSmelting.postAdd();
	}
}
package mariculture;

import java.io.File;
import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import mariculture.api.core.MaricultureTab;
import mariculture.core.CommonProxy;
import mariculture.core.Config;
import mariculture.core.RecipesSmelting;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketPipeline;
import mariculture.plugins.Plugins;
import mariculture.plugins.compatibility.Compat;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "Mariculture", name = "Mariculture", version = "1.2.1", dependencies="after:Enchiridion;after:TConstruct;after:Railcraft;after:ExtrabiomesXL;after:Forestry;after:IC2;after:Thaumcraft;after:BiomesOPlenty;after:AWWayofTime")
public class Mariculture {
	public static final PacketPipeline packets = new PacketPipeline();
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
		if(MaricultureTab.tabFish == null) MaricultureTab.tabFish = (Modules.fishery.isActive())? new MaricultureTab("fishTab"): null;
		MaricultureTab.tabMariculture = new MaricultureTab("maricultureTab");
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
		packets.initialise();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		plugins.load(Stage.POST);
		proxy.initClient();
		proxy.loadBooks();
		RecipesSmelting.postAdd();
		packets.postInitialise();
	}
	
	//TODO:
	/** Finish off Oyster Rendering
	 *  Fix the rendering for the custom blocks
	 *  Split the books in 1.7, and in 1.6, Merge up the fixes from 1.6
	 *  Then do the forge fluid dictionary and dsu for fluids
	 *  Then merge down what I need to, for 1.6 + Bug fixes for it, Plus other stuff
	 *  Make Vats not accept extra liquids in other tank
	 *  Release Fixied 1.6 Tomorrow/Saturday as 1.2.1
	 *  Work on new Jewelry System, Enchantment Numbers, Jewelry Mobs, Make Mob Magnet require LP for 1.2.2
	 */
}
package mariculture;

import java.io.File;

import mariculture.core.CommonProxy;
import mariculture.core.Config;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.Packets;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
@Mod(modid = "Mariculture", name = "Mariculture", dependencies="after:Enchiridion;after:TConstruct;after:Railcraft;after:ExtrabiomesXL;after:Forestry;after:IC2;after:Thaumcraft;after:BiomesOPlenty;after:AWWayofTime@(v1.0.1,]")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { "Mariculture" }, packetHandler = PacketHandler.class)
public class Mariculture {
	public static final String modid = "mariculture";

	@SidedProxy(clientSide = "mariculture.core.ClientProxy", serverSide = "mariculture.core.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance("Mariculture")
	public static Mariculture instance = new Mariculture();
	
	//Root folder
	public static File root;
	
	//Modules
	public static Modules modules = new Modules();

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogHandler.init();
		root = event.getModConfigurationDirectory();
		Config.init(root + "/mariculture/");
		for(Module module: Modules.modules) {
			module.preInit();
		}

		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		Packets.init();
		for(Module module: Modules.modules) {
			module.init();
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		for(Module module: Modules.modules) {
			module.postInit();
		}
		
		proxy.setupClient();
		//TODO: RecipeSmelting.postAdd();
	}
}
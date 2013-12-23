package mariculture.core;

import java.io.File;

import net.minecraftforge.common.Configuration;
import mariculture.api.core.MaricultureTab;
import mariculture.core.handlers.ClientPacketHandler;
import mariculture.core.handlers.LegacyConfigCopier;
import mariculture.core.handlers.LegacyLoader;
import mariculture.core.handlers.LogHandler;
import mariculture.core.handlers.ServerPacketHandler;
import mariculture.core.lib.Modules;
import mariculture.plugins.Plugins;
import mariculture.plugins.compatibility.Compat;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "Mariculture", name = "Mariculture", version = "1.1.4d", dependencies="after:TConstruct;after:Railcraft;after:ExtrabiomesXL;after:Forestry;after:IC2;after:carbonization;after:Thaumcraft;after:BiomesOPlenty")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "Mariculture" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "Mariculture" }, packetHandler = ServerPacketHandler.class))
public class Mariculture {
	public static final String modid = "mariculture";

	@SidedProxy(clientSide = "mariculture.core.ClientProxy", serverSide = "mariculture.core.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance("Mariculture")
	public static Mariculture instance = new Mariculture();
	
	//Root folder
	public static File root;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File file = event.getSuggestedConfigurationFile();
		
		if(file.exists()) {
			LegacyLoader.load(new Configuration(file));
			file.delete();
		} 
		
		root = event.getModConfigurationDirectory();
		Config.load(root + "/mariculture/");
		LogHandler.init();	
		
		MaricultureTab.tabMariculture = new MaricultureTab("maricultureTab");
		MaricultureTab.tabFish = (Modules.fishery.isActive())? new MaricultureTab("fishTab"): null;
		MaricultureTab.tabJewelry = (Modules.magic.isActive())? new MaricultureTab("jewelryTab"): null;
		
		Modules.core.load();
		Modules.diving.load();
		Modules.factory.load();
		Modules.fishery.load();
		Modules.magic.load();
		Modules.sealife.load();
		Modules.transport.load();
		Modules.world.load();

		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		Modules.core.postLoad();
		Modules.diving.postLoad();
		Modules.factory.postLoad();
		Modules.fishery.postLoad();
		Modules.magic.postLoad();
		Modules.sealife.postLoad();
		Modules.transport.postLoad();
		Modules.world.postLoad();
		Plugins.init();
		Compat.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.initClient();
	}
}

package mariculture;

import java.io.File;

import mariculture.core.CommonProxy;
import mariculture.core.Config;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.network.PacketPipeline;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "Mariculture", name = "Mariculture", version = "1.2.2", dependencies="after:Enchiridion;after:TConstruct;after:Railcraft;after:ExtrabiomesXL;after:Forestry;after:IC2;after:Thaumcraft;after:BiomesOPlenty;after:AWWayofTime")
public class Mariculture {
	public static final PacketPipeline packets = new PacketPipeline();
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
		root = event.getModConfigurationDirectory();
		Config.setup(root + "/mariculture/");	
		for(Module module: Modules.modules) {
			module.preInit();
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		packets.init();
		for(Module module: Modules.modules) {
			module.init();
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		packets.postInit();
		for(Module module: Modules.modules) {
			module.postInit();
		}
		
		proxy.setupClient();
	}
	
	//TODO:
	/** Finish off Oyster Rendering
	 *  Fix the rendering for the custom blocks
	 *  forge fluid dictionary and dsu for fluids, hopper tank
	 *  1.7 Elemental Affinity Enchantments
	 *  Move all 1.7 to the same handler as the oyster
	 *  Switch the rendered 'double' and 'single' blocks over to the rendered machine block types
	 *  Fix tank drops of blocktank meta
	 *  Fix Lava tank exploit with TiC
	 *  Fix books using excessive ram when opened...
	 *  1.7 Coral and Kelp growables
	 *  Add Tinkers and carpenters torch to gas explode list
	 *  Add 'exception' for the wood bucket to be returned
	 */
}
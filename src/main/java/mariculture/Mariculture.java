package mariculture;

import static mariculture.core.lib.MCModInfo.AFTER;
import static mariculture.core.lib.MCModInfo.JAVAPATH;
import static mariculture.core.lib.MCModInfo.MODID;
import static mariculture.core.lib.MCModInfo.MODNAME;

import java.io.File;

import mariculture.api.fishery.Fishing;
import mariculture.core.config.Config;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.network.PacketHandler;
import mariculture.fishery.Fish;
import mariculture.fishery.FisheryEventHandler;
import mariculture.plugins.PluginEE3;

import com.google.common.collect.HashMultimap;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = MODID, name = MODNAME, dependencies = AFTER)
public class Mariculture {
    public static final String modid = "mariculture";

    @SidedProxy(clientSide = JAVAPATH + "MCClientProxy", serverSide = JAVAPATH + "MCCommonProxy")
    public static MCCommonProxy proxy;

    @Instance("Mariculture")
    public static Mariculture instance;
    public static Modules modules = new Modules();
    public static File root;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (EnchiridionManager.isPresent()) {
            root = event.getModConfigurationDirectory();
            Config.setup();
            for (Module module : Modules.modules) {
                module.preInit();
            }
    
            NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PacketHandler.init();
        //Fish need to be registered before, the recipes that use them are called
        if (Modules.isActive(Modules.fishery)) {
            Fish.addOptionalFish();
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
    
    public static boolean EE3 = false;
    
    @EventHandler
    public void onComplete(FMLLoadCompleteEvent event) {
        if (EE3) {
            PluginEE3.load();
        }
    }
    
    @EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        if (Modules.isActive(Modules.fishery)) {
            FisheryEventHandler.invalid_spawns = HashMultimap.create();
        }
    }
}
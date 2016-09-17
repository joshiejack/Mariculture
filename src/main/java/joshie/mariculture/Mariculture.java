package joshie.mariculture;

import joshie.mariculture.modules.ModuleManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static joshie.mariculture.core.helpers.ConfigHelper.setConfig;
import static joshie.mariculture.core.lib.MaricultureInfo.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION)
public class Mariculture {
    public static final Logger logger = LogManager.getLogger(MODNAME);

    @SidedProxy(clientSide = JAVAPATH + "MClientProxy", serverSide = JAVAPATH + "MCommonProxy")
    public static MCommonProxy proxy;

    @Instance(MODID)
    public static Mariculture instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        setConfig(event.getSuggestedConfigurationFile());
        ModuleManager.loadModules(event.getAsmData(), proxy.isClient());
        ModuleManager.loadConfigs();
        proxy.load("preInit");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.load("init");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.load("postInit");
    }
}
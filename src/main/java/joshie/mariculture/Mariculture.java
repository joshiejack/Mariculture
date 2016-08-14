package joshie.mariculture;

import joshie.mariculture.modules.ModuleManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static joshie.mariculture.core.lib.MaricultureInfo.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION)
public class Mariculture {
    public static final Logger logger = LogManager.getLogger(MODNAME);

    @SidedProxy(clientSide = JAVAPATH + "MClientProxy", serverSide = JAVAPATH + "MCommonProxy")
    public static MCommonProxy proxy;

    @Instance(MODID)
    public static Mariculture instance;
    public static File root;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        root = new File(event.getModConfigurationDirectory(), MODID);
        ModuleManager.init(event.getAsmData());
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

    @EventHandler
    public void finish(FMLLoadCompleteEvent event) {
        ModuleManager.clear();
    }
}
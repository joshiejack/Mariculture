package joshie.mariculture;

import static java.io.File.separator;
import static joshie.mariculture.lib.MaricultureInfo.DEPENDENCIES;
import static joshie.mariculture.lib.MaricultureInfo.INITIALS;
import static joshie.mariculture.lib.MaricultureInfo.JAVAPATH;
import static joshie.mariculture.lib.MaricultureInfo.MODID;
import static joshie.mariculture.lib.MaricultureInfo.MODNAME;
import static joshie.mariculture.lib.MaricultureInfo.MODPATH;
import static joshie.mariculture.lib.MaricultureInfo.VERSION;

import java.io.File;

import joshie.mariculture.lib.MaricultureInfo;
import joshie.mariculture.util.DependencyManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MODID, name = MODNAME, version = VERSION, dependencies = DEPENDENCIES)
public class Mariculture {
    @SidedProxy(clientSide = JAVAPATH + INITIALS + "ClientProxy", serverSide = JAVAPATH + INITIALS + "CommonProxy")
    public static MCommonProxy proxy;

    @Instance(MODID)
    public static Mariculture instance;
    public static File root;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	MaricultureInfo.updateDev();
    	if (DependencyManager.hasDependencies()) {
	    	root = new File(event.getModConfigurationDirectory() + separator + MODPATH);
			MConfig.init();
			proxy.preInit();
    	}
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
}
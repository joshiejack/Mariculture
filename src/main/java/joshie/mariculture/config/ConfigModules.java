package joshie.mariculture.config;

import joshie.lib.util.Text;
import joshie.mariculture.MLogger;
import joshie.mariculture.lib.MaricultureInfo;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

public class ConfigModules {
	private static void activate(String module) {
		try {
			Class.forName(MaricultureInfo.JAVAPATH + "modules." + module + "." + Text.capitalizeFirst(module)).newInstance();
		} catch (Exception e) { }
	}
	
	public static void init(Configuration config) {
        try {
            config.load();
            //Activate the Core Module
            activate("core");
            
            for(String module: MaricultureInfo.MODULES) {
            	if(config.get("Modules", module, true).getBoolean(true)) {
            		activate(module);
            	}
            }
            
            //Activate the Hardcore Module???
            if(config.get("Modules", "hardcore", false).getBoolean(false)) {
        		activate("hardcore");
        	}
       } catch (Exception e) {
            MLogger.log(Level.ERROR, MaricultureInfo.MODNAME + " failed to load it's modules config");
            e.printStackTrace();
        } finally {
            config.save();
        }
	}

}

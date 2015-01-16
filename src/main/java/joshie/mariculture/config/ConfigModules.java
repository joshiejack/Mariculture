package joshie.mariculture.config;

import joshie.lib.util.Text;
import joshie.mariculture.MLogger;
import joshie.mariculture.lib.MaricultureInfo;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

public class ConfigModules {
	public static void init(Configuration config) {
        try {
            config.load();
            for(String module: MaricultureInfo.MODULES) {
            	if(config.get("Modules", module, true).getBoolean(true)) {
            		try {
            			Class.forName(MaricultureInfo.JAVAPATH + "modules." + module + "." + Text.capitalizeFirst(module)).newInstance();
            		} catch (Exception e) { }
            	}
            }
       } catch (Exception e) {
            MLogger.log(Level.ERROR, MaricultureInfo.MODNAME + " failed to load it's modules config");
            e.printStackTrace();
        } finally {
            config.save();
        }
	}

}

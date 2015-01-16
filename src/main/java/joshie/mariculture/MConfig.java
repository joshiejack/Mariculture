package joshie.mariculture;

import static java.io.File.separator;
import static joshie.mariculture.Mariculture.root;

import java.io.File;

import joshie.mariculture.config.ConfigModules;
import net.minecraftforge.common.config.Configuration;

public class MConfig {
    public static void init() {
    	ConfigModules.init(new Configuration(new File(root + separator + "modules.cfg")));
    }
}
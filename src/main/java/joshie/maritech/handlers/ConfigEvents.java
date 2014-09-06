package joshie.maritech.handlers;

import java.util.HashMap;

import joshie.mariculture.core.events.ConfigEvent;
import joshie.maritech.util.IConfigExtension;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigEvents {
    private static HashMap<String, IConfigExtension> extensions = new HashMap();
    
    public static void register(IConfigExtension extension) {
       extensions.put(extension.getName(), extension);
    }

    @SubscribeEvent
    public void onConfigure(ConfigEvent event) {
        IConfigExtension extension = extensions.get(event.clazz);
        if (extension != null) {
            extension.init(event.config);
        }
    }
}

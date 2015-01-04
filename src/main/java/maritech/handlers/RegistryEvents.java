package maritech.handlers;

import java.util.HashMap;

import mariculture.core.events.RegistryEvent;
import mariculture.plugins.Plugins.Plugin.Stage;
import maritech.util.IModuleExtension;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RegistryEvents {
    private static HashMap<String, IModuleExtension> extensions = new HashMap();

    @SubscribeEvent
    public void onRegistration(RegistryEvent event) {
        IModuleExtension extension = extensions.get(event.module.getClass().getSimpleName().toLowerCase());
        if (extension != null) {
            if (event.stage == Stage.PRE) {
                extension.preInit();
            } else if (event.stage == Stage.INIT) {
                extension.init();
            } else if (event.stage == Stage.POST) {
                extension.postInit();
            }
        }
    }

    public static void register(IModuleExtension extension) {
        extensions.put(extension.getClass().getSimpleName().replace("Extension", "").toLowerCase(), extension);
    }
}

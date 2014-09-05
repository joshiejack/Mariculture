package joshie.mariculture.core.events;

import joshie.mariculture.core.lib.Modules.Module;
import joshie.mariculture.plugins.Plugins.Plugin.Stage;
import cpw.mods.fml.common.eventhandler.Event;

public class RegistryEvent extends Event {
    public final Module module;
    public final Stage stage;
    
    public RegistryEvent(Module module, Stage stage) {
        this.module = module;
        this.stage = stage;
    }
}

package joshie.mariculture.core.events;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.eventhandler.Event;

public class ConfigEvent extends Event {
    public final String clazz;
    public final Configuration config;

    public ConfigEvent(String clazz, Configuration config) {
        this.clazz = clazz;
        this.config = config;
    }
}

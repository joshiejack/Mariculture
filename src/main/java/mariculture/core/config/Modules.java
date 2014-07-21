package mariculture.core.config;

import mariculture.Mariculture;
import mariculture.aesthetics.Aesthetics;
import mariculture.core.Core;
import mariculture.diving.Diving;
import mariculture.factory.Factory;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import mariculture.plugins.Plugins;
import mariculture.plugins.Plugins.Plugin;
import mariculture.sealife.Sealife;
import mariculture.transport.Transport;
import mariculture.world.WorldPlus;
import net.minecraftforge.common.config.Configuration;

public class Modules {
    public static void init(Configuration config) {
        Mariculture.modules.setup(Core.class, true);
        Mariculture.modules.setup(Aesthetics.class, config.get("Modules", "Aesthetics", true).getBoolean(true));
        Mariculture.modules.setup(Diving.class, config.get("Modules", "Diving", true).getBoolean(true));
        Mariculture.modules.setup(Factory.class, config.get("Modules", "Factory", true).getBoolean(true));
        Mariculture.modules.setup(Fishery.class, config.get("Modules", "Fishery", true).getBoolean(true));
        Mariculture.modules.setup(Magic.class, config.get("Modules", "Magic", true).getBoolean(true));
        Mariculture.modules.setup(Sealife.class, false);
        Mariculture.modules.setup(Transport.class, config.get("Modules", "Transport", true).getBoolean(true));
        Mariculture.modules.setup(WorldPlus.class, config.get("Modules", "World Plus", true).getBoolean(true));
        Mariculture.modules.setup(Plugins.class, true);

        for (int i = 0; i < Plugins.plugins.size(); i++) {
            Plugin plugin = Plugins.plugins.get(i);
            if (config.get("Plugins", plugin.name, true).getBoolean(true) == false) {
                Plugins.plugins.remove(i);
            }
        }
    }
}

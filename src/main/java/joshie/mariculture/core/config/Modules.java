package joshie.mariculture.core.config;

import joshie.mariculture.Mariculture;
import joshie.mariculture.aesthetics.Aesthetics;
import joshie.mariculture.core.Core;
import joshie.mariculture.diving.Diving;
import joshie.mariculture.factory.Factory;
import joshie.mariculture.fishery.Fishery;
import joshie.mariculture.magic.Magic;
import joshie.mariculture.plugins.Plugins;
import joshie.mariculture.plugins.Plugins.Plugin;
import joshie.mariculture.sealife.Sealife;
import joshie.mariculture.transport.Transport;
import joshie.mariculture.world.WorldPlus;
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
            if (config.get("Plugins", plugin.getClass().getSimpleName().substring(6), true).getBoolean(true) == false) {
                Plugins.plugins.remove(i);
            }
        }
    }
}

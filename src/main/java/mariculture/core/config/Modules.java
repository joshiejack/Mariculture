package mariculture.core.config;

import mariculture.Mariculture;
import mariculture.aesthetics.Aesthetics;
import mariculture.compatibility.Compat;
import mariculture.core.Core;
import mariculture.core.lib.config.Category;
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
        Mariculture.modules.setup(Aesthetics.class, config.get(Category.MODULES, "Aesthetics", true).getBoolean(true));
        Mariculture.modules.setup(Diving.class, config.get(Category.MODULES, "Diving", true).getBoolean(true));
        Mariculture.modules.setup(Factory.class, config.get(Category.MODULES, "Factory", true).getBoolean(true));
        Mariculture.modules.setup(Fishery.class, config.get(Category.MODULES, "Fishery", true).getBoolean(true));
        Mariculture.modules.setup(Magic.class, config.get(Category.MODULES, "Magic", true).getBoolean(true));
        Mariculture.modules.setup(Sealife.class, false);
        Mariculture.modules.setup(Transport.class, config.get(Category.MODULES, "Transport", true).getBoolean(true));
        Mariculture.modules.setup(WorldPlus.class, config.get(Category.MODULES, "World Plus", true).getBoolean(true));
        Mariculture.modules.setup(Compat.class, false);
        Mariculture.modules.setup(Plugins.class, true);

        for (int i = 0; i < Plugins.plugins.size(); i++) {
            Plugin plugin = Plugins.plugins.get(i);
            if (config.get(Category.PLUGINS, plugin.name, true).getBoolean(true) == false) {
                Plugins.plugins.remove(i);
            }
        }
    }
}

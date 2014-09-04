package joshie.maritech.extensions.config;

import static joshie.mariculture.core.helpers.ConfigHelper.getBoolean;
import static joshie.mariculture.core.helpers.ConfigHelper.setCategory;
import joshie.maritech.util.IConfigExtension;
import net.minecraftforge.common.config.Configuration;

public class ExtensionMachines implements IConfigExtension {
    @Override
    public String getName() {
        return "Machines";
    }

    @Override
    public void init(Configuration config) {
        setCategory("Client Settings", "The settings only affect clientside");
        ExtendedClient.FLUDD_BLOCK_ANIM = getBoolean("FLUDD - Enable Particles", true);
    }
    
    public static class ExtendedClient {
        public static boolean FLUDD_BLOCK_ANIM;
    }
}

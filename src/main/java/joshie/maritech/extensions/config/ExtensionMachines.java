package joshie.maritech.extensions.config;

import static joshie.mariculture.core.helpers.ConfigHelper.getBoolean;
import static joshie.mariculture.core.helpers.ConfigHelper.getInt;
import static joshie.mariculture.core.helpers.ConfigHelper.setCategory;
import static joshie.mariculture.core.helpers.ConfigHelper.setConfig;
import joshie.mariculture.core.config.Machines.MachineSettings;
import joshie.mariculture.core.lib.MachineSpeeds;
import joshie.maritech.util.IConfigExtension;
import net.minecraftforge.common.config.Configuration;

public class ExtensionMachines implements IConfigExtension {
    @Override
    public String getName() {
        return "Machines";
    }

    @Override
    public void init(Configuration config) {        
        setConfig(config);
        setCategory("Speed Settings");
        MachineSpeeds.autofisher = getInt("Automatic Fisher", 2500);
        MachineSpeeds.incubator = getInt("Incubator", 400);
        
        setCategory("Client Settings", "The settings only affect clientside");
        ExtendedSettings.FLUDD_BLOCK_ANIM = getBoolean("FLUDD - Enable Particles", true);
        
        setCategory("Tick Settings");
        ExtendedSettings.ADVANCED_SLUICE_TICK = getInt("Advanced Sluice > Tick Rate", 60);
        
        setCategory("Machine Settings");
        ExtendedSettings.DRAGON_EGG_ETHEREAL = getInt("Incubator > Dragon Egg Chance - Ethereal", 48000, "Same as the normal chance but this is the chance when you have an ethereal upgrade in the incubator");
        ExtendedSettings.DRAGON_EGG_BASE = getInt("Incubator > Dragon Egg Chance", 64000, "This is a 1 in this many chance for the chance to get a Spawn Ender Dragon from a Dragon Egg");
        ExtendedSettings.ADVANCED_SLUICE_RADIUS = getInt("Advanced Sluice > Radius to Drain Fluids", 66);
        ExtendedSettings.ENABLE_ADVANCED_SLUICE_DRAIN = getBoolean("Advanced Sluice > Enable Overpowered, Laggy Draining", false);
    }
    
    public static class ExtendedSettings {
        public static boolean FLUDD_BLOCK_ANIM;
        public static int ADVANCED_SLUICE_TICK;
        public static int DRAGON_EGG_ETHEREAL;
        public static int DRAGON_EGG_BASE;
        public static int ADVANCED_SLUICE_RADIUS;
        public static boolean ENABLE_ADVANCED_SLUICE_DRAIN;
    }
}

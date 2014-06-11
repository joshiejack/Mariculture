package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import mariculture.core.lib.MachineSpeeds;
import net.minecraftforge.common.config.Configuration;

public class Machines {
    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Speed Settings");
        MachineSpeeds.autofisher = getInt("Automatic Fisher", 2500);
        MachineSpeeds.feeder = getInt("Fish Feeder", 200);
        MachineSpeeds.incubator = getInt("Incubator", 400);
        MachineSpeeds.crucible = getInt("Industrial Smelter", 40000);
        MachineSpeeds.net = getInt("Fishing Net", 300);
        MachineSpeeds.sawmill = getInt("Sawmill", 650);

        setCategory("Client Settings", "The settings only affect clientside");
        Client.GEYSER_ANIM = getBoolean("Geyser - Enable Particles", true);
        Client.FLUDD_BLOCK_ANIM = getBoolean("FLUDD - Enable Particles", true);
        Client.PUMP_ANIM = getBoolean("Air Pump - Enable Rotation", true, "This will not work if Enable Ticking is set to false under Tick Settings");
        Client.TURBINE_ANIM = getBoolean("Turbines - Enable Rotation", true);
        Client.OLD_TURBINES = getBoolean("Turbines - Use Old Renderer", false);

        setCategory("Tick Settings");
        Ticks.ITEM_EJECT_TICK = getInt("Item Eject Tick", 20);
        Ticks.FLUID_EJECT_TICK = getInt("Fluid Eject Tick", 100);
        Ticks.EFFECT_TICK = getInt("Fish Feeder > Effect Tick", 20, "This is how many ticks for an effect to occur in a fish tank, such as poison or regen");
        Ticks.PUMP_ENABLE_TICKS = getBoolean("Air Pump - Enable Ticking", true);
        Ticks.TURBINE_PACKET_TICKS = getInt("Turbines - Ticks between Packet Updates", 20);
        Ticks.FISH_FOOD_TICK = getInt("Fish Feeder > Fish Food Tick Rate", 25, "This is how many minecraft ticks, before attempting to pick up fish food, set to 0 to disable");

        setCategory("Machine Settings");
        MachineSettings.PACKET_DISTANCE = getInt("How many blocks away to send rendering packet updates to players", 176);
        MachineSettings.PURITY = getInt("Crucible Furnace > Nuggets Per Purity Upgrade Level", 2);
        MachineSettings.DRAGON_EGG_ETHEREAL = getInt("Incubator > Dragon Egg Chance - Ethereal", 48000, "Same as the normal chance but this is the chance when you have an ethereal upgrade in the incubator");
        MachineSettings.DRAGON_EGG_BASE = getInt("Incubator > Dragon Egg Chance", 64000, "This is a 1 in this many chance for the chance to get a Spawn Ender Dragon from a Dragon Egg");
    }

    public static class Client {
        public static boolean PUMP_ANIM;
        public static boolean GEYSER_ANIM;
        public static boolean FLUDD_BLOCK_ANIM;
        public static boolean TURBINE_ANIM;
        public static boolean OLD_TURBINES;
    }

    public static class MachineSettings {
        public static int PACKET_DISTANCE;
        public static int PURITY;
        public static int DRAGON_EGG_ETHEREAL;
        public static int DRAGON_EGG_BASE;
    }

    public static class Ticks {
        public static int ITEM_EJECT_TICK;
        public static int FLUID_EJECT_TICK;
        public static int EFFECT_TICK;
        public static boolean PUMP_ENABLE_TICKS;
        public static int TURBINE_PACKET_TICKS;
        public static int FISH_FOOD_TICK;
        public static int TANK_UPDATE;
    }
}

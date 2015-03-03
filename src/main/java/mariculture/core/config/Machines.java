package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import mariculture.core.events.MaricultureEvents;
import mariculture.core.lib.MachineSpeeds;
import net.minecraftforge.common.config.Configuration;

public class Machines {
    public static void init(Configuration config) {
        setConfig(config);
        setCategory("Speed Settings");
        MachineSpeeds.feeder = getInt("Fish Feeder", 200);
        MachineSpeeds.crucible = getInt("Crucible Furnace", 40000);
        MachineSpeeds.net = getInt("Fishing Net", 300);
        MachineSpeeds.sawmill = getInt("Sawmill", 650);
        MachineSpeeds.hatchery = getInt("Hatchery", 100);

        setCategory("Client Settings", "The settings only affect clientside");
        Client.GEYSER_ANIM = getBoolean("Geyser - Enable Particles", true);
        Client.PUMP_ANIM = getBoolean("Air Pump - Enable Rotation", true, "This will not work if Enable Ticking is set to false under Tick Settings");
        Client.HAMMER_ANIM = getBoolean("Autohammer - Enabled Animation", true);
        Client.SHOW_FISH = getBoolean("Fish Feeder > Show Fish", true);

        setCategory("Tick Settings");
        Ticks.ITEM_EJECT_TICK = getInt("Item Eject Tick", 20);
        Ticks.FLUID_EJECT_TICK = getInt("Fluid Eject Tick", 100);
        Ticks.EFFECT_TICK = getInt("Fish Feeder > Effect Tick", 20, "This is how many ticks for an effect to occur in a fish tank, such as poison or regen");
        Ticks.PUMP_ENABLE_TICKS = getBoolean("Air Pump - Enable Ticking", true);
        Ticks.FISH_FOOD_TICK = getInt("Fish Feeder > Fish Food Tick Rate", 25, "This is how many minecraft ticks, before attempting to pick up fish food, set to 0 to disable");
        Ticks.PICKUP_TICK = getInt("Fish Feeder > Fish Food Pickup Tick Rate", -1, "How often it tries to pick up fish food, set to less than 0 to disable");
        Ticks.PUMP_TICK_TIMER = getInt("Air Pump > Ticks Between Supplying Air", 300);
        
        setCategory("Machine Settings");
        MachineSettings.PACKET_DISTANCE = getInt("How many blocks away to send rendering packet updates to players", 176);
        MachineSettings.PURITY = getInt("Crucible Furnace > mB Per Purity Upgrade Level", 32);
        MachineSettings.ENABLE_PURITY_IN_CRUCIBLE = getBoolean("Crucible Furnace > Enable Purity Bonus", false);
        MachineSettings.PUMP_ACTIVATE_ON_TICK = getBoolean("Air Pump > Activate on Tick", false, "Whether the air pump should fill out air when ticking the block");
        MachineSettings.PUMP_ACTIVATE_ON_RECEIVE = getBoolean("Air Pump > Activate on RF", true, "Whether the air pump should fill out air when it receives rf");
        MachineSettings.SLUICE_POWER_MULTIPLIER = getInt("Sluice > Power Multiplier", 25, "Increase this to increase the power produced by the sluice, take note the old default was 10");
        MachineSettings.SAWMILL_STACK_MULTIPLIER = getInt("Sawmill > Output Multiplier", 2, "Increase this to have more output for your sawmill blocks");
        MachineSettings.TANK_UPDATE_AMOUNT = getInt("Tanks > Send Update to Client on mB", 144, "When the difference in the tank has changed by this much, then it will send an update to the client. Make this number higher, to reduce lag");
        MaricultureEvents.onConfigure("Machines", config);
    }

    public static class Client {
        public static boolean SHOW_FISH;
        public static boolean PUMP_ANIM;
        public static boolean GEYSER_ANIM;
        public static boolean HAMMER_ANIM;
    }

    public static class MachineSettings {
        public static int SAWMILL_STACK_MULTIPLIER;
        public static int SLUICE_POWER_MULTIPLIER;
        public static int PACKET_DISTANCE;
        public static int PURITY;
        public static boolean ENABLE_PURITY_IN_CRUCIBLE;
        public static boolean PUMP_ACTIVATE_ON_TICK;
        public static boolean PUMP_ACTIVATE_ON_RECEIVE;
        public static int TANK_UPDATE_AMOUNT;
    }

    public static class Ticks {
        public static int ITEM_EJECT_TICK;
        public static int FLUID_EJECT_TICK;
        public static int EFFECT_TICK;
        public static boolean PUMP_ENABLE_TICKS;
        public static int PUMP_TICK_TIMER;
        public static int FISH_FOOD_TICK;
        public static int TANK_UPDATE;
        public static int PICKUP_TICK;
    }
}

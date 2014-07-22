package mariculture.core.lib;

import mariculture.core.config.GeneralStuff;

public class MachineSpeeds {
    public static int sawmill;
    public static int crucible;
    public static int incubator;
    public static int autofisher;
    public static int feeder;
    public static int net;
    public static int hatchery;

    public static int getSawmillSpeed() {
        return GeneralStuff.DEBUG_ON ? 10 : sawmill;
    }

    public static int getCrucibleSpeed() {
        return GeneralStuff.DEBUG_ON ? 10 : crucible;
    }

    public static int getIncubatorSpeed() {
        return GeneralStuff.DEBUG_ON ? 10 : incubator;
    }

    public static int getAutofisherSpeed() {
        return GeneralStuff.DEBUG_ON ? 10 : autofisher;
    }

    public static int getFeederSpeed() {
        return GeneralStuff.DEBUG_ON ? 10 : feeder;
    }

    public static int getNetSpeed() {
        return GeneralStuff.DEBUG_ON ? 10 : net;
    }

    public static int getHatcherySpeed() {
        return GeneralStuff.DEBUG_ON? 10: hatchery;
    }
}

package joshie.mariculture.core.lib;

import joshie.lib.util.Library;

public class MachineSpeeds {
    public static int sawmill;
    public static int crucible;
    public static int incubator; /** MT **/
    public static int autofisher; /** MT **/
    public static int feeder;
    public static int net;
    public static int hatchery;

    public static int getSawmillSpeed() {
        return Library.DEBUG_ON ? 10 : sawmill;
    }

    public static int getCrucibleSpeed() {
        return Library.DEBUG_ON ? 10 : crucible;
    }

    public static int getIncubatorSpeed() {
        return Library.DEBUG_ON ? 10 : incubator;
    }

    public static int getAutofisherSpeed() {
        return Library.DEBUG_ON ? 10 : autofisher;
    }

    public static int getFeederSpeed() {
        return Library.DEBUG_ON ? 10 : feeder;
    }

    public static int getNetSpeed() {
        return Library.DEBUG_ON ? 10 : net;
    }

    public static int getHatcherySpeed() {
        return Library.DEBUG_ON ? 10 : hatchery;
    }
}

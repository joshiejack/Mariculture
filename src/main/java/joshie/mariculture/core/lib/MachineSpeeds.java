package joshie.mariculture.core.lib;

import joshie.lib.util.Library;

public class MachineSpeeds {
    public static int sawmill;
    public static int crucible;
    public static int feeder;
    public static int net;
    public static int hatchery;
    
    public static int dna; /** MT **/
    public static int incubator; /** MT **/
    public static int autofisher; /** MT **/

    public static int getSawmillSpeed() {
        return Math.max(1, Library.DEBUG_ON ? 10 : sawmill);
    }

    public static int getCrucibleSpeed() {
        return Math.max(1, Library.DEBUG_ON ? 10 : crucible);
    }

    public static int getIncubatorSpeed() {
        return Math.max(1, Library.DEBUG_ON ? 10 : incubator);
    }

    public static int getAutofisherSpeed() {
        return Math.max(1, Library.DEBUG_ON ? 10 : autofisher);
    }

    public static int getFeederSpeed() {
        return Math.max(1, Library.DEBUG_ON ? 10 : feeder);
    }

    public static int getNetSpeed() {
        return Math.max(1, Library.DEBUG_ON ? 10 : net);
    }

    public static int getHatcherySpeed() {
        return Math.max(1, Library.DEBUG_ON ? 10 : hatchery);
    }
    
    public static int getDNAMachineSpeed() {
        return Math.max(1, Library.DEBUG_ON ? 10 : dna);
    }
}

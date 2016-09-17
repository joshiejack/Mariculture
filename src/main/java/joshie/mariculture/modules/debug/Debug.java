package joshie.mariculture.modules.debug;

import joshie.mariculture.core.util.annotation.MCLoader;

import static joshie.mariculture.core.helpers.ConfigHelper.getInteger;

@MCLoader
public class Debug {
    public static int CHICKEN_FISHING;

    public static void configure() {
        CHICKEN_FISHING = getInteger("Chicken Fishing Loot Amount", 25);
    }
}

package joshie.mariculture.modules.debug;

import joshie.mariculture.modules.Module;

import static joshie.mariculture.core.helpers.ConfigHelper.getInteger;

@Module(name = "debug", disableByDefault = true)
public class Debug {
    public static int CHICKEN_FISHING;

    public static void configure() {
        CHICKEN_FISHING = getInteger("Chicken Fishing Loot Amount", 25);
    }
}

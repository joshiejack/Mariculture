package joshie.mariculture.modules.sealife;

import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.sealife.lib.Pearl;
import joshie.mariculture.util.ItemComponent;

import static joshie.mariculture.lib.CreativeOrder.PEARLS;

/** The Sealife Module is about bringing the oceans to life
 *  It's mostly plants, and critters, like coral, kelp, turtles adding to the atmosphere of the ocean,
 *  they'll have limited use, but it's about the aesthetic*/
@Module(name = "sealife")
public class Sealife {
    public static final ItemComponent PEARL = new ItemComponent<>(PEARLS, Pearl.class).register("pearl");

    public static void preInit() {}
}

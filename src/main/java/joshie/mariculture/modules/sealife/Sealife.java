package joshie.mariculture.modules.sealife;

import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.sealife.blocks.BlockPlant;
import joshie.mariculture.modules.sealife.lib.Pearl;
import joshie.mariculture.core.util.item.ItemComponent;

import static joshie.mariculture.core.lib.CreativeOrder.PEARLS;

/** The Sealife Module is about bringing the oceans to life
 *  It's mostly plants, and critters, like coral, kelp, turtles adding to the atmosphere of the ocean,
 *  they'll have limited use, but it's about the aesthetic*/
@Module(name = "sealife")
public class Sealife {
    public static final BlockPlant PLANTS = new BlockPlant().register("plant");
    public static final ItemComponent PEARL = new ItemComponent<>(PEARLS, Pearl.class).register("pearl");

    public static void preInit() {}
}

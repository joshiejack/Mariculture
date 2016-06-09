package joshie.mariculture.modules.fishery;

import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.fishery.loot.InBiomeType;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;

/** The Fishery module is about the fish, catching them, breeding them,
 *  using them for various different things, and hopefully a new way of fishing */
@Module(name = "fishery")
public class Fishery {
    public static void preInit() {
        LootConditionManager.registerCondition(new InBiomeType.Serializer());
    }
}

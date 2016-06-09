package joshie.mariculture.modules.fishery;

import joshie.mariculture.modules.Module;
import joshie.mariculture.modules.fishery.loot.InBiomeType;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;

/** The Fishery module is about the fish, and catching them,
 *  gutting them and processing them */
@Module(name = "fishery")
public class Fishery {
    public static void preInit() {
        LootConditionManager.registerCondition(new InBiomeType.Serializer());
    }
}

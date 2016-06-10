package joshie.mariculture.modules.fishery.loot;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

/** Abstract condition, with a world context **/
public abstract class AbstractWorldLocation implements LootCondition {
    @Override
    public boolean testCondition(Random rand, LootContext context) {
        if (context.getLootedEntity() != null) {
            return testCondition(context.getLootedEntity().worldObj, new BlockPos(context.getLootedEntity()));
        }

        return false;
    }

    public abstract boolean testCondition(World world, BlockPos pos);
}

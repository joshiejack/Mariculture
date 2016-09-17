package joshie.mariculture.modules.hardcore.feature;

import joshie.mariculture.core.util.annotation.MCEvents;
import joshie.mariculture.core.util.annotation.MCLoader;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@MCLoader
@MCEvents(modules = "hardcore-waterworld")
public class Waterworld {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onBreakBlock(BreakEvent event) {
        MinecraftForge.EVENT_BUS.register(new SetWater(event.getWorld(), event.getPos()));
    }

    private static class SetWater {
        private World world;
        private BlockPos pos;

        public SetWater(World world, BlockPos pos) {
            this.world = world;
            this.pos = pos;
        }

        @SubscribeEvent
        public void onWorldTick(WorldTickEvent event) {
            if (event.phase == Phase.END && event.world == world) {
                world.setBlockState(pos, Blocks.WATER.getDefaultState());
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }
}

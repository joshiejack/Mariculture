package joshie.mariculture.api.diving;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface AirPumpTicker {
    /** Number of ticks between calling onAirPumpTick
     *  Needs to be divisible by 20 **/
    default int getTicks() { return 300; }

    /** Called when the air pump ticks
     *  @param world    the world the air pump is in
     *  @param pos      the position of the air pump
     *  @param energy   the amount of energy in the air pump
     *  @return return the amount of energy consumed**/
    int onAirPumpTick(World world, BlockPos pos, int energy);
}

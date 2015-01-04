package mariculture.api.core.interfaces;

import net.minecraft.world.World;

public interface IBlacklisted {
    /** Tile Entities should implement this interface if their tanks 
     * should be ignored by the pressure vessel when transferring high pressure water **/
    public boolean isBlacklisted(World world, int x, int y, int z);
}

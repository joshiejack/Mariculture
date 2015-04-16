package mariculture.factory.blocks;

import mariculture.core.lib.PlansMeta;
import mariculture.factory.tile.TileCustomPowered;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCustomRFWall extends BlockCustomWall {
    @Override
    public int getID() {
        return PlansMeta.RF_WALL;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileCustomPowered();
    }
}

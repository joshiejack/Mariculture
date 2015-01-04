package mariculture.factory.blocks;

import mariculture.core.lib.PlansMeta;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockCustomBlock extends BlockCustomBase {
    public BlockCustomBlock() {
        super(Material.rock);
    }

    @Override
    public int getID() {
        return PlansMeta.BLOCK;
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return true;
    }
}

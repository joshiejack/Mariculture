package joshie.mariculture.factory.blocks;

import joshie.mariculture.core.lib.PlansMeta;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockCustomLight extends BlockCustomBase {
    public BlockCustomLight() {
        super(Material.rock);
    }

    @Override
    public int getID() {
        return PlansMeta.LIGHT;
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return true;
    }
}

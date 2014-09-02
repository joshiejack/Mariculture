package joshie.mariculture.factory.blocks;

import joshie.mariculture.core.lib.PlansMeta;
import joshie.mariculture.factory.tile.TileCustomPowered;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCustomPower extends BlockCustomBase {

    public BlockCustomPower() {
        super(Material.rock);
    }

    @Override
    public int getID() {
        return PlansMeta.RF;
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileCustomPowered();
    }
}

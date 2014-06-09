package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.core.blocks.base.BlockConnected;
import mariculture.core.lib.GlassMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockGlass extends BlockConnected {
    private static IIcon[] heatglass = new IIcon[47];

    public BlockGlass() {
        super(Material.glass);
        setHardness(0.5F);
    }

    @Override
    public String getToolType(int meta) {
        return null;
    }

    @Override
    public int getToolLevel(int meta) {
        return 0;
    }

    @Override
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        Block block = world.getBlock(x, y, z);
        return block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public int getMetaCount() {
        return GlassMeta.COUNT;
    }

    @Override
    public IIcon[] getTexture(int meta) {
        switch (meta) {
            case GlassMeta.HEAT:
                return heatglass;
            default:
                return null;
        }
    }

    @Override
    public void registerConnectedTextures(IIconRegister iconRegister) {
        for (int i = 0; i < 47; i++) {
            heatglass[i] = iconRegister.registerIcon(Mariculture.modid + ":heatglass/" + (i + 1));
        }
    }
}
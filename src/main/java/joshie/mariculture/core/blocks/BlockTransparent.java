package joshie.mariculture.core.blocks;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.blocks.base.BlockConnected;
import joshie.mariculture.core.lib.TransparentMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockTransparent extends BlockConnected {
    private static IIcon[] plastic = new IIcon[47];

    public BlockTransparent() {
        super(Material.glass);
        setHardness(0.5F);
        prefix = "transparent_";
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
        return 1;
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
        return TransparentMeta.COUNT;
    }

    @Override
    public IIcon[] getTexture(int meta) {
        switch (meta) {
            case TransparentMeta.PLASTIC:
                return plastic;
            default:
                return null;
        }
    }

    @Override
    public void registerConnectedTextures(IIconRegister iconRegister) {
        for (int i = 0; i < 47; i++) {
            plastic[i] = iconRegister.registerIcon(Mariculture.modid + ":plastic/" + (i + 1));
        }
    }
}
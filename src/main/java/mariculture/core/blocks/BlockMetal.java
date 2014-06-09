package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.core.blocks.base.BlockDecorative;
import mariculture.core.lib.MetalMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetal extends BlockDecorative {
    private IIcon[] aluminum;

    public BlockMetal() {
        super(Material.iron);
    }

    @Override
    public String getToolType(int meta) {
        return "pickaxe";
    }

    @Override
    public int getToolLevel(int meta) {
        switch (meta) {
            case MetalMeta.COPPER_BLOCK:
                return 0;
            case MetalMeta.ALUMINUM_BLOCK:
                return 1;
            case MetalMeta.RUTILE_BLOCK:
                return 2;
            case MetalMeta.MAGNESIUM_BLOCK:
                return 0;
            case MetalMeta.TITANIUM_BLOCK:
                return 3;
            case MetalMeta.BASE_IRON:
                return 1;
            default:
                return 1;
        }
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case MetalMeta.COPPER_BLOCK:
                return 2.5F;
            case MetalMeta.ALUMINUM_BLOCK:
                return 4F;
            case MetalMeta.RUTILE_BLOCK:
                return 10F;
            case MetalMeta.MAGNESIUM_BLOCK:
                return 1F;
            case MetalMeta.TITANIUM_BLOCK:
                return 15F;
            case MetalMeta.BASE_IRON:
                return 3F;
            default:
                return 4F;
        }
    }

    @Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        switch (world.getBlockMetadata(x, y, z)) {
            case MetalMeta.COPPER_BLOCK:
                return 10F;
            case MetalMeta.ALUMINUM_BLOCK:
                return 12F;
            case MetalMeta.RUTILE_BLOCK:
                return 15F;
            case MetalMeta.MAGNESIUM_BLOCK:
                return 2F;
            case MetalMeta.TITANIUM_BLOCK:
                return 45F;
            case MetalMeta.BASE_IRON:
                return 10F;
            default:
                return 10F;
        }
    }

    //Checks whether the block is the same type or not
    private boolean isSameBlock(IBlockAccess world, int[] coords1, int[] coords2) {
        return world.getBlock(coords1[0], coords1[1], coords1[2]) == world.getBlock(coords2[0], coords2[1], coords2[2]) && world.getBlockMetadata(coords1[1], coords1[0], coords1[2]) == world.getBlockMetadata(coords2[1], coords2[0], coords2[2]);
    }

    @Override
    public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
        int meta = block.getBlockMetadata(x, y, z);
        if (meta != MetalMeta.ALUMINUM_BLOCK) return super.getIcon(block, x, y, z, side);
        else {
            if (isSameBlock(block, new int[] { x, y, z }, new int[] { x, y + 1, z })) {
                if (isSameBlock(block, new int[] { x, y, z }, new int[] { x, y - 1, z })) return aluminum[0];

                return aluminum[1];
            }

            if (isSameBlock(block, new int[] { x, y, z }, new int[] { x, y - 1, z })) {
                if (isSameBlock(block, new int[] { x, y, z }, new int[] { x, y + 1, z })) return aluminum[0];

                return aluminum[2];
            }

            return super.getIcon(block, x, y, z, side);
        }
    }

    @Override
    public int getMetaCount() {
        return MetalMeta.COUNT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        super.registerBlockIcons(register);
        aluminum = new IIcon[3];
        for (int i = 0; i < aluminum.length; i++) {
            aluminum[i] = register.registerIcon(Mariculture.modid + ":aluminumBlock" + i);
        }
    }
}

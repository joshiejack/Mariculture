package joshie.mariculture.core.blocks;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.blocks.base.BlockMCBaseMeta;
import joshie.mariculture.core.lib.LimestoneMeta;
import joshie.mariculture.core.lib.Modules;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLimestone extends BlockMCBaseMeta {
    public BlockLimestone() {
        super(Material.rock);
        prefix = "limestone";
    }

    @Override
    public String getToolType(int meta) {
        return "pickaxe";
    }

    @Override
    public int getToolLevel(int meta) {
        return 0;
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case LimestoneMeta.RAW:
                return 0.75F;
            case LimestoneMeta.SMOOTH:
                return 1F;
            case LimestoneMeta.SMALL_BRICK:
                return 1.75F;
            case LimestoneMeta.THIN_BRICK:
                return 2F;
            default:
                return 1.5F;
        }
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        if (meta >= LimestoneMeta.PILLAR_1 && meta <= LimestoneMeta.PILLAR_3) return side == 0 || side == 1 ? LimestoneMeta.PILLAR_1 : side == 2 || side == 3 ? LimestoneMeta.PILLAR_2 : LimestoneMeta.PILLAR_3;
        else if (meta >= LimestoneMeta.PEDESTAL_1) {
            if (hitY >= 0.85F) return LimestoneMeta.PEDESTAL_1;
            else if (hitY <= 0.15F) return LimestoneMeta.PEDESTAL_2;
            else return side + LimestoneMeta.PEDESTAL_1;
        } else return meta;
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta >= LimestoneMeta.PILLAR_1 && meta < LimestoneMeta.PILLAR_3) {
            world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
        } else if (meta == LimestoneMeta.PILLAR_3) {
            world.setBlockMetadataWithNotify(x, y, z, LimestoneMeta.PILLAR_1, 2);
        } else if (meta >= LimestoneMeta.PEDESTAL_1 && meta < LimestoneMeta.PEDESTAL_6) {
            world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
        } else if (meta == LimestoneMeta.PEDESTAL_6) {
            world.setBlockMetadataWithNotify(x, y, z, LimestoneMeta.PEDESTAL_1, 2);
        }

        return false;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta == LimestoneMeta.PILLAR_1) return side == 0 || side == 1 ? icons[LimestoneMeta.PILLAR_2] : icons[LimestoneMeta.PILLAR_3];
        if (meta == LimestoneMeta.PILLAR_2) return side == 0 || side == 1 ? icons[LimestoneMeta.PILLAR_3] : side == 4 || side == 5 ? icons[LimestoneMeta.PILLAR_1] : icons[LimestoneMeta.PILLAR_2];
        if (meta == LimestoneMeta.PILLAR_3) return side == 4 || side == 5 ? icons[LimestoneMeta.PILLAR_2] : icons[LimestoneMeta.PILLAR_1];
        if (meta == LimestoneMeta.PEDESTAL_1) return side == 0 ? icons[LimestoneMeta.PILLAR_2] : side == 1 ? icons[LimestoneMeta.BORDERED] : icons[LimestoneMeta.PEDESTAL_2];
        if (meta == LimestoneMeta.PEDESTAL_2) return side == 1 ? icons[LimestoneMeta.PILLAR_2] : side == 0 ? icons[LimestoneMeta.BORDERED] : icons[LimestoneMeta.PEDESTAL_1];
        if (meta == LimestoneMeta.PEDESTAL_3) return side == 3 ? icons[LimestoneMeta.PILLAR_2] : side == 2 ? icons[LimestoneMeta.BORDERED] : side == 0 || side == 1 ? icons[LimestoneMeta.PEDESTAL_2] : icons[LimestoneMeta.PEDESTAL_3];
        if (meta == LimestoneMeta.PEDESTAL_4) return side == 2 ? icons[LimestoneMeta.PILLAR_2] : side == 3 ? icons[LimestoneMeta.BORDERED] : side == 0 || side == 1 ? icons[LimestoneMeta.PEDESTAL_1] : icons[LimestoneMeta.PEDESTAL_4];
        if (meta == LimestoneMeta.PEDESTAL_5) return side == 5 ? icons[LimestoneMeta.PILLAR_2] : side == 4 ? icons[LimestoneMeta.BORDERED] : icons[LimestoneMeta.PEDESTAL_3];
        if (meta == LimestoneMeta.PEDESTAL_6) return side == 4 ? icons[LimestoneMeta.PILLAR_2] : side == 5 ? icons[LimestoneMeta.BORDERED] : icons[LimestoneMeta.PEDESTAL_4];
        if (meta == LimestoneMeta.CHISELED) return side < 2 ? icons[LimestoneMeta.BORDERED] : super.getIcon(side, meta);
        return super.getIcon(side, meta);
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < LimestoneMeta.PILLAR_1) return super.getPickBlock(target, world, x, y, z);
        if (meta < LimestoneMeta.PEDESTAL_1) return new ItemStack(this, 1, LimestoneMeta.PILLAR_1);
        return new ItemStack(this, 1, LimestoneMeta.PEDESTAL_1);
    }

    @Override
    public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
        return false;
    }

    @Override
    public boolean isActive(int meta) {
        if (meta == LimestoneMeta.RAW) return true;
        else if (Modules.isActive(Modules.aesthetics)) return !(meta == LimestoneMeta.PILLAR_2 || meta == LimestoneMeta.PILLAR_3 || meta > LimestoneMeta.PEDESTAL_1);
        else return false;
    }

    @Override
    public int getMetaCount() {
        return LimestoneMeta.COUNT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        icons = new IIcon[getMetaCount() - 2];

        for (int i = 0; i < icons.length; i++) {
            String name = getName(i);
            icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + prefix + name.substring(0, 1).toUpperCase() + name.substring(1));
        }
    }
}

package joshie.mariculture.core.util;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static joshie.mariculture.MClientProxy.NO_WATER;
import static joshie.mariculture.core.lib.MaricultureInfo.MODID;
import static net.minecraft.block.BlockLiquid.LEVEL;

public class BlockAquatic<E extends Enum<E> & IStringSerializable, B extends BlockAquatic> extends BlockMCEnum<E, B> {
    public BlockAquatic(Class<E> clazz) {
        super(Material.WATER, clazz, MCTab.getExploration());
        setSoundType(SoundType.PLANT);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if(property == null) return new BlockStateContainer(this, LEVEL, temporary);
        return new BlockStateContainer(this, LEVEL, property);
    }

    @Deprecated
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isFullCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels(Item item, String name) {
        ModelLoader.setCustomStateMapper(this, NO_WATER);

        for (E e: values) {
            ModelLoader.setCustomModelResourceLocation(item, e.ordinal(),
                    new ModelResourceLocation(new ResourceLocation(MODID, e.getClass().getSimpleName().toLowerCase() + "_" + e.getName()), "inventory"));
        }
    }
}

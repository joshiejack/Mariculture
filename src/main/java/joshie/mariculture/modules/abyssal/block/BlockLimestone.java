package joshie.mariculture.modules.abyssal.block;

import joshie.mariculture.helpers.StringHelper;
import joshie.mariculture.modules.abyssal.block.BlockLimestone.Type;
import joshie.mariculture.util.BlockMCEnum;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.lib.CreativeOrder.LIMESTONE;
import static joshie.mariculture.lib.MaricultureInfo.MODID;
import static joshie.mariculture.lib.MaricultureInfo.MODPREFIX;
import static joshie.mariculture.modules.abyssal.block.BlockLimestone.Type.*;
import static net.minecraft.util.EnumFacing.*;

public class BlockLimestone extends BlockMCEnum<Type, BlockLimestone> {
    public enum Type implements IStringSerializable {
        RAW, SMOOTH, BRICK, SMALL_BRICK, THIN_BRICK, BORDERED, CHISELED, PILLAR_1(true, false), PILLAR_2(true, false), PILLAR_3(true, false),
        PEDESTAL_1(false, true), PEDESTAL_2(false, true), PEDESTAL_3(false, true), PEDESTAL_4(false, true), PEDESTAL_5(false, true), PEDESTAL_6(false, true);

        private final boolean isPillar;
        private final boolean isPedestal;

        private Type() {
            isPillar = false;
            isPedestal = false;
        }

        private Type (boolean isPillar, boolean isPedestal) {
            this.isPillar = isPillar;
            this.isPedestal = isPedestal;
        }

        @Override
        public String getName() {
            return toString().toLowerCase();
        }
    }

    public BlockLimestone() {
        super(Material.ROCK, Type.class);
    }

    @Deprecated
    @Override
    public float getBlockHardness(IBlockState state, World worldIn, BlockPos pos) {
        switch (getEnumFromState(state)) {
            case RAW:
                return 0.75F;
            case SMOOTH:
                return 1F;
            case SMALL_BRICK:
                return 1.75F;
            case THIN_BRICK:
                return 2F;
            default:
                return 1.5F;
        }
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        Type type = getEnumFromMeta(meta);
        if (type.isPillar) return facing == DOWN || facing == UP ? getStateFromEnum(PILLAR_1) : facing == NORTH || facing == SOUTH ? getStateFromEnum(PILLAR_2) : getStateFromEnum(PILLAR_3);
        else if (type.isPedestal) {
            if (hitY >= 0.85F) return getStateFromEnum(PEDESTAL_1);
            else if (hitY <= 0.15F) return getStateFromEnum(PEDESTAL_2);
            else return getStateFromMeta(facing.ordinal() + PEDESTAL_1.ordinal());
        } else return getStateFromMeta(meta);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        Type type = getEnumFromState(state);
        return !type.isPillar && !type.isPedestal;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        Type type = getEnumFromState(state);
        if (type.isPillar) return new ItemStack(this, 1, PILLAR_1.ordinal());
        else if (type.isPedestal) return new ItemStack(this, 1, PEDESTAL_1.ordinal());
        else return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, com.google.common.base.Predicate<IBlockState> target) {
        return target.apply(Blocks.CLAY.getDefaultState());
    }

    @Override
    public boolean isInCreative(Type type) {
        return type.ordinal() <= PILLAR_1.ordinal() || type.ordinal() == PEDESTAL_1.ordinal();
    }

    @Override
    protected String getNameFromStack(ItemStack stack) {
        Type type = getEnumFromStack(stack);
        if (type.isPillar) return "pillar";
        else if (type.isPedestal) return "pedestal";
        else return super.getNameFromStack(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = MODPREFIX + getUnlocalizedName();
        String name = getNameFromStack(stack);
        return StringHelper.localize(unlocalized + "." + name);
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return LIMESTONE + stack.getItemDamage();
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ResourceLocation getResourceForEnum(Type type) {
        if (type.isPillar) return new ResourceLocation(MODID, getUnlocalizedName().replace(MODID + ".", "") + "_pillar");
        else if (type.isPedestal) return new ResourceLocation(MODID, getUnlocalizedName().replace(MODID + ".", "") + "_pedestal");
        else return new ResourceLocation(MODID, getUnlocalizedName().replace(MODID + ".", "") + "_" + type.getName());
    }
}

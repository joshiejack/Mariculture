package joshie.mariculture.modules.aquaculture.block;

import joshie.mariculture.core.helpers.EntityHelper;
import joshie.mariculture.core.helpers.StringHelper;
import joshie.mariculture.core.helpers.TileHelper;
import joshie.mariculture.core.util.BlockAquatic;
import joshie.mariculture.modules.aquaculture.AquacultureAPI;
import joshie.mariculture.modules.aquaculture.block.BlockOyster.Oyster;
import joshie.mariculture.modules.aquaculture.tile.TileOyster;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

import static joshie.mariculture.MClientProxy.NO_WATER;
import static joshie.mariculture.core.lib.MaricultureInfo.MODPREFIX;
import static net.minecraft.block.BlockHorizontal.FACING;
import static net.minecraft.block.BlockLiquid.LEVEL;

public class BlockOyster extends BlockAquatic<Oyster, BlockOyster> {
    private static final AxisAlignedBB BOUNDING_BOX = null;

    public BlockOyster() {
        super(Oyster.class);
        setHardness(1F);
        setSoundType(SoundType.SAND);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if(property == null) return new BlockStateContainer(this, LEVEL, FACING, temporary);
        return new BlockStateContainer(this, LEVEL, FACING, property);
    }

    @Deprecated
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.15D, 0.0D, 0.15D, 0.85D, 0.8D, 0.85D);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(FACING)).getHorizontalIndex();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        ItemStack stack = TileHelper.getStackInSlot(world.getTileEntity(pos), 0);
        spawnAsEntity(world, pos, stack);
        super.breakBlock(world, pos, state);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, EntityHelper.getFacingFromEntity(entity)));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        Oyster oyster = getEnumFromState(getActualState(state, world, pos));
        if (oyster == Oyster.EMPTY && heldItem != null && AquacultureAPI.isSand(heldItem)) {
            ItemStack held = TileHelper.getItemHandler(world.getTileEntity(pos)).insertItem(0, heldItem, false);
            if (!player.capabilities.isCreativeMode) player.setHeldItem(hand, held);
            return true;
        } else if (oyster == Oyster.PEARL || oyster == Oyster.SAND) {
            ItemStack result = TileHelper.getItemHandler(world.getTileEntity(pos)).extractItem(0, 1, false);
            if (result != null) {
                ItemHandlerHelper.giveItemToPlayer(player, result);
                return true;
            }
        }

        return false;
    }

    @Deprecated
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        ItemStack stack = TileHelper.getStackInSlot(world.getTileEntity(pos), 0);
        if (stack == null) return state.withProperty(property, Oyster.EMPTY);
        else if (AquacultureAPI.isSand(stack)) return state.withProperty(property, Oyster.SAND);
        else return state.withProperty(property, Oyster.PEARL);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileOyster();
    }

    @Override
    public boolean isInCreative(Oyster oyster) {
        return oyster == Oyster.EMPTY;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = MODPREFIX + getUnlocalizedName();
        return StringHelper.localize(unlocalized);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels(Item item, String name) {
        ModelLoader.setCustomStateMapper(this, NO_WATER);

        for (Oyster oyster: values) {
            ModelLoader.setCustomModelResourceLocation(item, oyster.ordinal(), new ModelResourceLocation(getRegistryName(), "inventory"));
        }
    }

    //Oyster Stages
    public enum Oyster implements IStringSerializable {
        EMPTY, SAND, PEARL;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}

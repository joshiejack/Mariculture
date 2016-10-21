package joshie.mariculture.modules.diving.block;

import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.core.util.block.BlockMCEnum;
import joshie.mariculture.modules.diving.block.BlockDiving.DivingBlock;
import joshie.mariculture.modules.diving.tile.TileAirPump;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockDiving extends BlockMCEnum<DivingBlock, BlockDiving> {
    public BlockDiving() {
        super(Material.IRON, DivingBlock.class, MCTab.getExploration());
        setHardness(1F);
        setSoundType(SoundType.ANVIL);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (getEnumFromState(state) == DivingBlock.AIR_PUMP) {
            TileAirPump pump = (TileAirPump) world.getTileEntity(pos);
            return pump.onActivated(player);
        }

        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return getEnumFromState(state) == DivingBlock.AIR_PUMP;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileAirPump();
    }

    public enum DivingBlock implements IStringSerializable {
        AIR_PUMP;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}

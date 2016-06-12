package joshie.mariculture.modules.abyssal.block;

import joshie.mariculture.modules.abyssal.Abyssal;
import joshie.mariculture.modules.abyssal.block.BlockLimestoneSlab.Type;
import joshie.mariculture.util.BlockSlabMC;
import joshie.mariculture.util.ItemSlabMC;
import joshie.mariculture.util.MCItem;
import joshie.mariculture.util.MCTab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumMap;

import static joshie.mariculture.modules.abyssal.block.BlockLimestoneSlab.Type.*;

public abstract class BlockLimestoneSlab extends BlockSlabMC<Type, BlockLimestoneSlab> {
    public enum Type implements IStringSerializable {
        RAW, SMOOTH, BRICK, SMALL_BRICK, THIN_BRICK, BORDERED;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }

    private static EnumMap<Type, IBlockState> original = new EnumMap<>(Type.class);
    static {
        original.put(RAW, Abyssal.LIMESTONE.getStateFromEnum(BlockLimestone.Type.RAW));
        original.put(SMOOTH, Abyssal.LIMESTONE.getStateFromEnum(BlockLimestone.Type.SMOOTH));
        original.put(BRICK, Abyssal.LIMESTONE.getStateFromEnum(BlockLimestone.Type.BRICK));
        original.put(SMALL_BRICK, Abyssal.LIMESTONE.getStateFromEnum(BlockLimestone.Type.SMALL_BRICK));
        original.put(THIN_BRICK, Abyssal.LIMESTONE.getStateFromEnum(BlockLimestone.Type.THIN_BRICK));
        original.put(BORDERED, Abyssal.LIMESTONE.getStateFromEnum(BlockLimestone.Type.BORDERED));
    }

    public BlockLimestoneSlab() {
        super(Material.ROCK, Type.class);
        IBlockState iblockstate = this.blockState.getBaseState();
        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }

        setDefaultState(iblockstate.withProperty(property, RAW));
        setCreativeTab(MCTab.getTab("core"));
    }

    @Override
    public float getBlockHardness(IBlockState state, World worldIn, BlockPos pos) {
        return original.get(getEnumFromState(state)).getBlockHardness(worldIn, pos);
    }

    @Override
    public MCItem getItemBlock() {
        single = Abyssal.LIMESTONE_SLAB; //Assign the single block
        return new ItemSlabMC(this, Abyssal.LIMESTONE_SLAB, Abyssal.LIMESTONE_SLAB_DOUBLE);
    }

    public static class Half extends BlockLimestoneSlab {
        @Override
        public boolean isDouble() {
            return false;
        }
    }

    public static class Double extends BlockLimestoneSlab {
        @Override
        public boolean isDouble() {
            return true;
        }
    }
}

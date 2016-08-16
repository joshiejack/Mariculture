package joshie.mariculture.modules.sealife.blocks;

import joshie.mariculture.core.util.block.BlockAquatic;
import joshie.mariculture.modules.sealife.blocks.BlockPlant.Plant;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

import static joshie.mariculture.modules.sealife.blocks.BlockPlant.Plant.GRASS_TOP;

public class BlockPlant extends BlockAquatic<Plant, BlockPlant> {
    public static final Set<Block> FLOOR_BLOCKS = new HashSet<>();

    public BlockPlant() {
        super(Plant.class);
        FLOOR_BLOCKS.add(Blocks.DIRT);
        FLOOR_BLOCKS.add(Blocks.GRAVEL);
        FLOOR_BLOCKS.add(Blocks.SAND);
    }

    @Override
    protected String getNameFromStack(ItemStack stack) {
        return getEnumFromStack(stack).unlocalized;
    }

    @Override
    public boolean isInCreative(Plant plant) {
        return plant.isTop; //Only add plants that are their top half in to the creative menu
    }

    private boolean canSustainPlant(IBlockState below, Plant plant) {
        if (plant.isGrass() && below.getBlock() == this && getEnumFromState(below).isGrass()) return true;
        if (plant.isKelp() && below.getBlock() == this && getEnumFromState(below).isKelp()) return true;
        return FLOOR_BLOCKS.contains(below.getBlock());
    }

    @Override
    public boolean canPlaceBlockAt(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos, EnumFacing side) {
        if (worldIn.getBlockState(pos.up()).getMaterial() != Material.WATER) return false;
        Plant plant = getEnumFromStack(stack);
        if (plant == GRASS_TOP && worldIn.getBlockState(pos.down(2)).getBlock() == this) return false;
        return canSustainPlant(worldIn.getBlockState(pos.down()), plant);
    }

    public enum Plant implements IStringSerializable {
        GRASS_SHORT("grass", false), GRASS_DOUBLE("grass", false), GRASS_TOP("grass", true),
        KELP_BOTTOM("kelp", false), KELP_MIDDLE("kelp", false), KELP_TOP("kelp", true);

        private final String unlocalized;
        private final boolean isTop;

        Plant(String unlocalized, boolean isTop) {
            this.unlocalized = unlocalized;
            this.isTop = isTop;
        }

        public boolean isGrass() {
            return this == GRASS_SHORT || this == GRASS_DOUBLE || this == GRASS_TOP;
        }

        public boolean isKelp() {
            return this == KELP_BOTTOM || this == KELP_MIDDLE || this == KELP_TOP;
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}

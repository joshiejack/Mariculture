package joshie.mariculture.modules.fishery.block;

import joshie.mariculture.core.util.MCTab;
import joshie.mariculture.core.util.block.BlockMCEnum;
import joshie.mariculture.modules.fishery.block.BlockFishery.FishBlock;
import joshie.mariculture.modules.fishery.tile.TileGutter;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockFishery extends BlockMCEnum<FishBlock, BlockFishery> {
    public BlockFishery() {
        super(Material.WOOD, FishBlock.class, MCTab.getFishery());
        setHardness(0.5F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return getEnumFromState(state) == FishBlock.GUTTER;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileGutter();
    }

    public enum FishBlock implements IStringSerializable {
        GUTTER, BARREL;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}

package mariculture.core.blocks;

import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public abstract class BlockFunctionalMulti extends BlockFunctional {
	public BlockFunctionalMulti(Material material) {
		super(material);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileMultiBlock) {
			((TileMultiBlock)tile).onBlockPlaced();
		}
		
		super.onBlockAdded(world, x, y, z);
	}
	
	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileMultiBlock) {
			((TileMultiBlock)tile).onBlockBreak();
		}
		
		super.onBlockExploded(world, x, y, z, explosion);
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BlockHelper.dropItems(world, x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileMultiBlock) {
			((TileMultiBlock)tile).onBlockBreak();
		}
		
		super.breakBlock(world, x, y, z, block, meta);
	}
}

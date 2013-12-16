package mariculture.core.blocks.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockCore extends Block {

	public BlockCore(int i, Material mat) {
		super(i, mat);
	}
	
	public void updateMaster(World world, int x, int y, int z) {
		if(world.getBlockTileEntity(x, y, z) instanceof TileMulti) {
			((TileMulti) world.getBlockTileEntity(x, y, z)).setMaster();
		}
	}
	
	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		updateMaster(world, x, y, z);
		
		super.onBlockExploded(world, x, y, z, explosion);
    }

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		updateMaster(world, x, y, z);
		
		return super.removeBlockByPlayer(world, player, x, y, z);
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)  {
		updateMaster(world, x, y, z);
		
		super.breakBlock(world, x, y, z, par5, par6);
    }
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		updateMaster(world, x, y, z);
		
		super.onBlockAdded(world, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int otherID) {
		updateMaster(world, x, y, z);
		
		super.onNeighborBlockChange(world, x, y, z, otherID);
	}
}

package mariculture.core.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRenderedMachine extends BlockFunctional {
	public BlockRenderedMachine() {
		super(Material.piston);
	}
	
	@Override
	public String getToolType(int meta) {
		return null;
	}

	@Override
	public int getToolLevel(int meta) {
		return 0;
	}

	@Override
	public boolean onBlockDropped(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return null;
	}

	@Override
	public int getMetaCount() {
		return 0;
	}
}

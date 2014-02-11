package mariculture.factory.blocks;

import mariculture.core.lib.PlansMeta;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockCustomBlock extends BlockCustomBase {
	public BlockCustomBlock(int i) {
		super(i, Material.rock);
	}

	@Override
	public int getID() {
		return PlansMeta.BLOCK;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return true;
	}
}

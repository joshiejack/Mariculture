package mariculture.factory.blocks;

import mariculture.core.lib.PlansMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

package mariculture.core.render;

import mariculture.core.lib.RenderIds;
import mariculture.core.lib.TankMeta;
import mariculture.factory.render.RenderHDFPV;
import mariculture.fishery.render.RenderFishTank;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderTanks implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int meta, int id, RenderBlocks render) {
		if (id == RenderIds.BLOCK_TANKS) {
			if(meta == TankMeta.FISH) new RenderFishTank(render).setBlock(block).render();
			if(meta == TankMeta.TANK) new RenderCopperTank(render).setBlock(block).render();
			if(meta == TankMeta.HDFPV) new RenderHDFPV(render).setBlock(block).render();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int id, RenderBlocks render) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == TankMeta.BOTTLE) {
			return new RenderVoidBottle(render).setCoords(world, x, y, z).render();
		} else if(meta == TankMeta.TANK) {
			return new RenderCopperTank(render).setCoords(world, x, y, z).render();
		} else if(meta == TankMeta.FISH) {
			return new RenderFishTank(render).setCoords(world, x, y, z).render();
		} else if (meta == TankMeta.HDFPV) {
			return new RenderHDFPV(render).setCoords(world, x, y, z).render();
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.BLOCK_TANKS;
	}
}

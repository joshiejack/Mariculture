package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.common.ForgeDirection;

public class RenderAnvil extends RenderBase {
	
	public RenderAnvil(RenderBlocks render) {
		super(render);
	}
	
	@Override
	public void renderBlock() {
		if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
			//Bottom
			setTexture(Core.oreBlocks, OresMeta.BASE_BRICK);
			renderBlock(0.05, 0, 0.1, 0.95D, 0.2D, 0.9D);
			
			//Bottomish
			setTexture(Core.oreBlocks, OresMeta.BASE_BRICK);
			renderBlock(0.15, 0.2, 0.2, 0.85D, 0.3D, 0.8D);
			
			//Middle
			setTexture(Block.netherBrick);
			renderBlock(0.25, 0.3, 0.3, 0.75D, 0.6D, 0.7D);
			setTexture(Block.netherBrick);
			renderBlock(0.05, 0.6, 0.2, 0.95D, 0.65D, 0.8D);
			
			//Secondary Head
			setTexture(Core.oreBlocks, OresMeta.BASE_BRICK);
			renderBlock(0, 0.65, 0.15, 1D, 0.95D, 0.85D);
			
			//Head
			setTexture(Block.netherBrick);
			renderBlock(0.95, 0.95, 0.15, 1D, 1D, 0.85D);
			setTexture(Block.netherBrick);
			renderBlock(0D, 0.95, 0.15D, 0.05D, 1D, 0.85D);
			setTexture(Block.netherBrick);
			renderBlock(0.05D, 0.95D, 0.8D, 0.95D, 1D, 0.85D);
			setTexture(Block.netherBrick);
			renderBlock(0.05D, 0.95D, 0.15D, 0.95D, 1D, 0.2D);
			setTexture(Block.hopperBlock);
			renderBlock(0.05D, 0.95D, 0.2D, 0.95D, 1D, 0.8D);
		} else {
			//Bottom
			setTexture(Core.oreBlocks, OresMeta.BASE_BRICK);
			renderBlock(0.1, 0, 0.05, 0.9D, 0.2D, 0.95D);
			
			//Bottomish
			setTexture(Core.oreBlocks, OresMeta.BASE_BRICK);
			renderBlock(0.2, 0.2, 0.15, 0.8D, 0.3D, 0.85D);
			
			//Middle
			setTexture(Block.netherBrick);
			renderBlock(0.3, 0.3, 0.25, 0.7D, 0.6D, 0.75D);
			setTexture(Block.netherBrick);
			renderBlock(0.2, 0.6, 0.05, 0.8D, 0.65D, 0.95D);
			
			//Secondary Head
			setTexture(Core.oreBlocks, OresMeta.BASE_BRICK);
			renderBlock(0.15, 0.65, 0, 0.85D, 0.95D, 1D);
			
			//Head
			setTexture(Block.netherBrick);
			renderBlock(0.15, 0.95, 0.95, 0.85D, 1D, 1D);
			setTexture(Block.netherBrick);
			renderBlock(0.15, 0.95, 0, 0.85D, 1D, 0.05D);
			setTexture(Block.netherBrick);
			renderBlock(0.8, 0.95, 0.05, 0.85D, 1D, 0.95D);
			setTexture(Block.netherBrick);
			renderBlock(0.15, 0.95, 0.05, 0.2D, 1D, 0.95D);
			setTexture(Block.hopperBlock);
			renderBlock(0.2, 0.95, 0.05, 0.8D, 1D, 0.95D);
		}
	}
}

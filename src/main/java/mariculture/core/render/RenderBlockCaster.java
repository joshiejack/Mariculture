package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.TileBlockCaster;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.MultiMeta;
import mariculture.core.lib.RenderMeta;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;

public class RenderBlockCaster extends RenderBase {
	public RenderBlockCaster(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {
		if(!isItem()) {
			TileBlockCaster tile = (TileBlockCaster) world.getBlockTileEntity(x, y, z);
			//Render Block in slot 1
			ItemStack stack = tile.getStackInSlot(0);
			if(stack != null && block.blocksList[stack.itemID] != null) {
				setTexture(Block.blocksList[stack.itemID], stack.getItemDamage());
				renderBlock(0.1, 0.05, 0.1, 0.9, 0.90, 0.9);
			}
			
			if(tile.getFluid() != null) {
				renderFluid(tile.getFluid(), MetalRates.BLOCK, 0.5D, 0, 0, 0);
			}
		}
		
		setTexture(Core.multi, MultiMeta.VAT);
		renderBlock(0, 0, 0, 1, 0.05, 1);
		//Sides
		setTexture(Core.rendered, RenderMeta.INGOT_CASTER);
		renderBlock(0, 0.05, 0, 1, 1, 0.1);
		renderBlock(0, 0.05, 0.9, 1, 1, 1);
		renderBlock(0, 0.05, 0.1, 0.1, 1, 0.9);
		renderBlock(0.9, 0.05, 0.1, 1, 1, 0.9);
	}
}

package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.handlers.IngotCastingHandler;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.tile.TileIngotCaster;
import net.minecraft.client.renderer.RenderBlocks;

public class RenderCaster extends RenderBase {
	public RenderCaster(){}
	
	@Override
	public void renderBlock() {
		if(!isItem()) {
			TileIngotCaster tile = (TileIngotCaster) world.getTileEntity(x, y, z);
			if(tile == null) return;
			//Render Ingot in slot 1
			if(tile.getStackInSlot(0) != null) {
				setTexture(IngotCastingHandler.getTexture(tile.getStackInSlot(0)));
				renderBlock(0.1, 0.05, 0.1, 0.4, 0.90, 0.4);
				renderBlock(0.15, 0.75, 0.15, 0.35, 0.93, 0.35);
			}
			
			//Render Ingot in slot 2
			if(tile.getStackInSlot(1) != null) {
				setTexture(IngotCastingHandler.getTexture(tile.getStackInSlot(1)));
				renderBlock(0.1, 0.05, 0.6, 0.4, 0.90, 0.9);
				renderBlock(0.15, 0.75, 0.65, 0.35, 0.93, 0.85);
			}
			
			//Render Ingot in slot 3
			if(tile.getStackInSlot(2) != null) {
				setTexture(IngotCastingHandler.getTexture(tile.getStackInSlot(2)));
				renderBlock(0.6, 0.05, 0.6, 0.9, 0.90, 0.9);
				renderBlock(0.65, 0.75, 0.65, 0.85, 0.93, 0.85);
			}
			
			//Render Ingot in slot4
			if(tile.getStackInSlot(3) != null) {
				setTexture(IngotCastingHandler.getTexture(tile.getStackInSlot(3)));
				renderBlock(0.6, 0.05, 0.1, 0.9, 0.90, 0.6);
				renderBlock(0.65, 0.75, 0.15, 0.85, 0.93, 0.65);
			}
			
			if(tile.getFluid() != null) {
				renderFluid(tile.getFluid(), MetalRates.INGOT * 4, 0.5D, 0, 0, 0);
			}
		}
		
		setTexture(Core.renderedMultiMachines, MachineRenderedMultiMeta.VAT);
		renderBlock(0, 0, 0, 1, 0.05, 1);
		//Sides
		setTexture(Core.renderedMachines, MachineRenderedMeta.INGOT_CASTER);
		renderBlock(0, 0.05, 0, 1, 1, 0.1);
		renderBlock(0, 0.05, 0.9, 1, 1, 1);
		renderBlock(0, 0.05, 0.1, 0.1, 1, 0.9);
		renderBlock(0.9, 0.05, 0.1, 1, 1, 0.9);
		
		setTexture(Core.renderedMultiMachines, MachineRenderedMultiMeta.VAT);
		//Crossbars
		renderBlock(0.4, 0.05, 0.1, 0.6, 1, 0.9);
		renderBlock(0.1, 0.05, 0.4, 0.4, 1, 0.6);
		renderBlock(0.6, 0.05, 0.4, 0.9, 1, 0.6);
	}
}

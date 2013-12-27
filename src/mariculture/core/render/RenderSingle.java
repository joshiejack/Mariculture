package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileBucket;
import mariculture.core.blocks.TileForge;
import mariculture.core.blocks.TileOyster;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.RenderIds;
import mariculture.diving.render.ModelAirPump;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.render.ModelFLUDD;
import mariculture.factory.render.ModelTurbineGas;
import mariculture.factory.render.ModelTurbineWater;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileNet;
import mariculture.fishery.blocks.TileSift;
import mariculture.fishery.render.ModelFeeder;
import mariculture.fishery.render.ModelNet;
import mariculture.fishery.render.ModelSift;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderSingle extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	private ModelBase model;
	private ResourceLocation resource;

	public RenderSingle() {}
	
	public RenderSingle(ModelBase model, ResourceLocation resource) {
		this.model = model;
		this.resource = resource;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		if (tileEntity instanceof TileAirPump) {
			this.bindTexture(resource);
			((ModelAirPump) model).render((TileAirPump) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileOyster) {
			this.bindTexture(resource);
			((ModelOyster) model).render((TileOyster) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileSift) {
			this.bindTexture(resource);
			((ModelSift) model).render((TileSift) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileNet) {
			this.bindTexture(resource);
			((ModelNet) model).render((TileNet) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileFeeder) {
			this.bindTexture(resource);
			((ModelFeeder) model).render((TileFeeder) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileTurbineWater) {
			this.bindTexture(resource);
			((ModelTurbineWater) model).render((TileTurbineWater) tileEntity, x, y, z);
		}
		
		if (tileEntity instanceof TileTurbineGas) {
			this.bindTexture(resource);
			((ModelTurbineGas) model).render((TileTurbineGas) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileFLUDDStand) {
			this.bindTexture(resource);
			((ModelFLUDD) model).render((TileFLUDDStand) tileEntity, x, y, z);
		}
	}


	private void renderBucket(TileBucket tile, IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block) {
		render.renderAllFaces = true;
		render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
		if(world.getBlockTileEntity(x - 1, y - 1, z) instanceof TileForge) {
			render.setRenderBounds(0, -0.1, 0.05, 0.2, 0.5, 0.2);
			render.renderStandardBlock(block, x, y, z);
			render.setRenderBounds(0, -0.1, 0.8, 0.2, 0.5, 0.95);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.anvil.getIcon(0, 0));
			render.setRenderBounds(0.075, 0.4, 0.2, 0.125, 0.45, 0.8);
			render.renderStandardBlock(block, x, y, z);
		} else if(world.getBlockTileEntity(x + 1, y - 1, z) instanceof TileForge) {
			render.setRenderBounds(0.8, -0.1, 0.8, 1, 0.5, 0.95);
			render.renderStandardBlock(block, x, y, z);
			render.setRenderBounds(0.8, -0.1, 0.05, 1, 0.5, 0.2);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.anvil.getIcon(0, 0));
			render.setRenderBounds(0.875, 0.4, 0.2, 0.925, 0.45, 0.8);
			render.renderStandardBlock(block, x, y, z);
			
			render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
			render.setRenderBounds(0.8, 0.3, 0.4, 1.0, 0.55, 0.6);
			render.renderStandardBlock(block, x, y, z);
		}else if(world.getBlockTileEntity(x, y - 1, z + 1) instanceof TileForge) {
			render.setRenderBounds(0.8, -0.1, 0.8, 0.95, 0.5, 1);
			render.renderStandardBlock(block, x, y, z);
			render.setRenderBounds(0.05, -0.1, 0.8, 0.2, 0.5, 1);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.anvil.getIcon(0, 0));
			render.setRenderBounds(0.2, 0.4, 0.875, 0.8, 0.45, 0.925);
			render.renderStandardBlock(block, x, y, z);
		} else if(world.getBlockTileEntity(x, y - 1, z - 1) instanceof TileForge) {
			render.setRenderBounds(0.8, -0.1, 0, 0.95, 0.5, 0.2);
			render.renderStandardBlock(block, x, y, z);
			render.setRenderBounds(0.05, -0.1, 0, 0.2, 0.5, 0.2);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.anvil.getIcon(0, 0));
			render.setRenderBounds(0.2, 0.4, 0.075, 0.8, 0.45, 0.125);
			render.renderStandardBlock(block, x, y, z);
		} else {
			render.setRenderBounds(0.4, -0.1, 0.4, 0.6, 0.5, 0.6);
			render.renderStandardBlock(block, x, y, z);
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false;
		System.out.println("rerendered");
	}
	

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks render) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		TileEntity tile = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileBucket) {
			renderBucket((TileBucket) tile, world, x, y, z, render, Block.stone);
		}
		
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return RenderIds.BLOCK_SINGLE;
	}
}
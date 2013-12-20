package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.TileForge;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.RenderIds;
import mariculture.diving.TileAirCompressor;
import mariculture.diving.TileAirCompressorPower;
import mariculture.diving.render.ModelAirCompressor;
import mariculture.diving.render.ModelAirCompressorPower;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderDouble extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

	private ModelBase model;
	private ResourceLocation resource;

	public RenderDouble() {}
	
	public RenderDouble(ModelBase model, ResourceLocation resource) {
		this.model = model;
		this.resource = resource;
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		if (tileEntity instanceof TileAirCompressor) {
			this.bindTexture(resource);
			((ModelAirCompressor) model).render((TileAirCompressor) tileEntity, x, y, z, this);
		}

		if (tileEntity instanceof TilePressureVessel) {
			this.bindTexture(resource);
			((ModelAirCompressor) model).render((TilePressureVessel) tileEntity, x, y, z, this);
		}

		if (tileEntity instanceof TileAirCompressorPower) {
			this.bindTexture(resource);
			((ModelAirCompressorPower) model).render((TileAirCompressorPower) tileEntity, x, y, z);
		}
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks render) {
		
	}
	
	public boolean doExtend(TileEntity tile) {
		return tile instanceof TileForge || tile instanceof TileLiquifier;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		TileEntity tile = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileForge) {
			render.renderAllFaces = true;
			
			//Top
			render.setOverrideBlockTexture(Block.anvil.getIcon(0, 0));
			render.setRenderBounds(0, 0.8, 0, 1, 0.85, 1);
			render.renderStandardBlock(Block.coalBlock, x, y, z);
			render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.BASE_BRICK));
			render.setRenderBounds(0, 0.7, 0, 1, 0.8, 1);
			render.renderStandardBlock(Block.coalBlock, x, y, z);
			
			double xStart = (doExtend(world.getBlockTileEntity(x - 1, y, z)))? 0: 0.25;
			double xEnd = (doExtend(world.getBlockTileEntity(x + 1, y, z)))? 1: 0.75;
			double zStart = (doExtend(world.getBlockTileEntity(x, y, z - 1)))? 0: 0.25;
			double zEnd = (doExtend(world.getBlockTileEntity(x, y, z + 1)))? 1: 0.75;
			
			render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
			render.setRenderBounds(xStart, 0.5, zStart, xEnd, 0.7, zEnd);
			render.renderStandardBlock(Block.stone, x, y, z);
			render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.BASE_BRICK));
			render.setRenderBounds(xStart, 0.1, zStart, xEnd, 0.5, zEnd);
			render.renderStandardBlock(Block.stone, x, y, z);
			render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
			render.setRenderBounds(xStart, 0.0, zStart, xEnd, 0.1, zEnd);
			render.renderStandardBlock(Block.stone, x, y, z);
			
			//Top Sides
			//Right hand side
			if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TileForge)) {
				//Side
				render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
				render.setRenderBounds(0, 0.85, 0, 1, 1, 0.05);
				render.renderStandardBlock(Block.coalBlock, x, y, z);
			}
			
			//Left hand side
			if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TileForge)) {
				//Side
				render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
				render.setRenderBounds(0, 0.85, 0.95, 1, 1, 1);
				render.renderStandardBlock(Block.coalBlock, x, y, z);
			}
			
			//Top hand side
			if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TileForge)) {
				//Side
				render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
				render.setRenderBounds(0, 0.85, 0, 0.05, 1, 1);
				render.renderStandardBlock(Block.coalBlock, x, y, z);
			}
			
			//Bottom hand side
			if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TileForge)) {
				//Side
				render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
				render.setRenderBounds(0.95, 0.85, 0, 1, 1, 1);
				render.renderStandardBlock(Block.coalBlock, x, y, z);
			}
			
			//Liquid
			TileForge forge = (TileForge) tile;
			if(forge != null) {
				if(forge.getFluid() != null) {
					if(forge.getFluid().amount > 0) {
						FluidStack fluidStack = forge.getFluid();
						Fluid fluid = fluidStack.getFluid();
						render.setRenderBounds(0.05, 0.95, 0.05, 0.95, 0.98, 0.95);
						render.setOverrideBlockTexture(fluid.getStillIcon());
						render.renderStandardBlock(Block.coalBlock, x, y, z);
					}
				}
				
				if(forge.getStackInSlot(0) != null) {
					//renderItem(forge, forge.getStackInSlot(0));
				}
			}
			
			render.clearOverrideBlockTexture();
			render.renderAllFaces = false;
		}
		
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return RenderIds.BLOCK_DOUBLE;
	}
}
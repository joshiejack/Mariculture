package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.TileForge;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.lib.DoubleMeta;
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

		if (tileEntity instanceof TileAirCompressorPower) {
			this.bindTexture(resource);
			((ModelAirCompressorPower) model).render((TileAirCompressorPower) tileEntity, x, y, z);
		}
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks render) {
		
	}
	
	public boolean doExtendForge(TileEntity tile) {
		return tile instanceof TileForge || tile instanceof TileLiquifier;
	}
	
	public void renderForge(TileForge tile, IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block) {
		render.renderAllFaces = true;
		
		//Top
		render.setOverrideBlockTexture(Block.anvil.getIcon(0, 0));
		render.setRenderBounds(0, 0.8, 0, 1, 0.85, 1);
		render.renderStandardBlock(block, x, y, z);
		render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.BASE_BRICK));
		render.setRenderBounds(0, 0.7, 0, 1, 0.8, 1);
		render.renderStandardBlock(block, x, y, z);
		
		double xStart = (doExtendForge(world.getBlockTileEntity(x - 1, y, z)))? 0: 0.25;
		double xEnd = (doExtendForge(world.getBlockTileEntity(x + 1, y, z)))? 1: 0.75;
		double zStart = (doExtendForge(world.getBlockTileEntity(x, y, z - 1)))? 0: 0.25;
		double zEnd = (doExtendForge(world.getBlockTileEntity(x, y, z + 1)))? 1: 0.75;
		
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
			render.renderStandardBlock(block, x, y, z);
		}
		
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TileForge)) {
			//Side
			render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
			render.setRenderBounds(0, 0.85, 0.95, 1, 1, 1);
			render.renderStandardBlock(block, x, y, z);
		}
		
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TileForge)) {
			//Side
			render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
			render.setRenderBounds(0, 0.85, 0, 0.05, 1, 1);
			render.renderStandardBlock(block, x, y, z);
		}
		
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TileForge)) {
			//Side
			render.setOverrideBlockTexture(Block.netherBrick.getIcon(0, 0));
			render.setRenderBounds(0.95, 0.85, 0, 1, 1, 1);
			render.renderStandardBlock(block, x, y, z);
		}
		
		//Liquid
		TileForge forge = (TileForge) world.getBlockTileEntity(tile.mstr.x, tile.mstr.y, tile.mstr.z);
		if(forge != null) {
			if(forge.getFluid() != null) {
				if(forge.getFluid().amount > 0) {
					FluidStack fluidStack = forge.getFluid();
					Fluid fluid = fluidStack.getFluid();
					double xFluidStart = (doExtendForge(world.getBlockTileEntity(x - 1, y, z)))? 0: 0.05;
					double xFluidEnd = (doExtendForge(world.getBlockTileEntity(x + 1, y, z)))? 1: 0.95;
					double zFluidStart = (doExtendForge(world.getBlockTileEntity(x, y, z - 1)))? 0: 0.05;
					double zFluidEnd = (doExtendForge(world.getBlockTileEntity(x, y, z + 1)))? 1: 0.95;
					
					render.setRenderBounds(xFluidStart, 0.95, zFluidStart, xFluidEnd, 0.98, zFluidEnd);
					render.setOverrideBlockTexture(fluid.getStillIcon());
					render.renderStandardBlock(block, x, y, z);
				}
			}
			
			if(forge.getStackInSlot(0) != null) {
				//renderItem(forge, forge.getStackInSlot(0));
			}
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false;
	}
	
	public boolean doExtendVessel(TileEntity tile) {
		return tile instanceof TilePressureVessel;
	}
	
	private void renderSide(IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block, double yStart, double yEnd) {
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0, yStart, 0.045, 1, yEnd, 0.01);
			render.renderStandardBlock(block, x, y, z);
		}
				
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0, yStart, 0.955, 1, yEnd, 0.99);
			render.renderStandardBlock(block, x, y, z);
		}
				
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0.01, yStart, 0, 0.045, yEnd, 1);
			render.renderStandardBlock(block, x, y, z);
		}
				
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0.955, yStart, 0, 0.99, yEnd, 1);
			render.renderStandardBlock(block, x, y, z);
		}
	}
	
	private void renderVessel(IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block) {
		render.renderAllFaces = true;
		
		double xStart = (doExtendVessel(world.getBlockTileEntity(x - 1, y, z)))? 0: 0.05;
		double xEnd = (doExtendVessel(world.getBlockTileEntity(x + 1, y, z)))? 1: 0.95;
		double zStart = (doExtendVessel(world.getBlockTileEntity(x, y, z - 1)))? 0: 0.05;
		double zEnd = (doExtendVessel(world.getBlockTileEntity(x, y, z + 1)))? 1: 0.95;
		double yStart = (doExtendVessel(world.getBlockTileEntity(x, y - 1, z)))? 0: 0.2;
		double yEnd = (doExtendVessel(world.getBlockTileEntity(x, y + 1, z)))? 1: 0.95;
		
		render.setOverrideBlockTexture(Core.doubleBlock.getBlockTexture(world, x, y, z, 0));
		render.setRenderBounds(xStart, yStart, zStart, xEnd, yEnd, zEnd);
		render.renderStandardBlock(Block.stone, x, y, z);
		
		//Top Sides
		//Right hand side
		if(!(world.getBlockTileEntity(x, y + 1, z) instanceof TilePressureVessel)) {
			renderSide(world, x, y, z, render, block, 0.93, 0.96);
		}
		
		if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel)) {
			renderSide(world, x, y, z, render, block, 0.2, 0.24);
		}
		
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0.95, yStart, 0, 1, yEnd, 0.05);
			render.renderStandardBlock(block, x, y, z);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
				render.setRenderBounds(0.6, 0, 0.1, 0.9, 0.2, 0.4);
				render.renderStandardBlock(block, x, y, z);
			}
		}
				
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0, yStart, 0.95, 0.05, yEnd, 1);
			render.renderStandardBlock(block, x, y, z);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
				render.setRenderBounds(0.1, 0, 0.6, 0.4, 0.2, 0.9);
				render.renderStandardBlock(block, x, y, z);
			}
		}
				
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0, yStart, 0, 0.05, yEnd, 0.05);
			render.renderStandardBlock(block, x, y, z);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
				render.setRenderBounds(0.1, 0, 0.1, 0.4, 0.2, 0.4);
				render.renderStandardBlock(block, x, y, z);
			}
		}
				
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x , y, z + 1) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0.95, yStart, 0.95, 1, yEnd, 1);
			render.renderStandardBlock(block, x, y, z);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
				render.setRenderBounds(0.6, 0, 0.6, 0.9, 0.2, 0.9);
				render.renderStandardBlock(block, x, y, z);
			}
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		TileEntity tile = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileForge) {
			renderForge((TileForge)tile, world, x, y, z, render, block.stone);
		} else if (tile instanceof TilePressureVessel) {
			renderVessel(world, x, y, z, render, block.stone);
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
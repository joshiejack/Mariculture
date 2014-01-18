package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockDouble;
import mariculture.core.blocks.TileVat;
import mariculture.core.helpers.RenderHelper;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.RenderIds;
import mariculture.diving.TileAirCompressor;
import mariculture.diving.render.RenderCompressorBase;
import mariculture.diving.render.RenderCompressorTop;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderDouble implements ISimpleBlockRenderingHandler {
	public RenderHelper helper;
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks render) {
		if(meta == DoubleMeta.VAT) {
			new RenderVat(render).render();
		} else if (meta == DoubleMeta.COMPRESSOR_BASE) {
			new RenderCompressorBase(render).render();
		} else if(meta == DoubleMeta.COMPRESSOR_TOP) {
			new RenderCompressorTop(render).render();
		}
	}
	
	private void renderCompressorTop(IBlockAccess world, TileAirCompressor tile, ForgeDirection facing) {		
		
	}
	
	public boolean doExtendVessel(TileEntity tile) {
		return tile instanceof TilePressureVessel;
	}
	
	private void renderSide(IBlockAccess world, int x, int y, int z, double yStart, double yEnd) {
		//Set Texture
		helper.setTexture(Block.blockLapis);
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel))
			helper.renderBlock(0, yStart, 0.045, 1, yEnd, 0.01);
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel))
			helper.renderBlock(0, yStart, 0.955, 1, yEnd, 0.99);	
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel))
			helper.renderBlock(0.01, yStart, 0, 0.045, yEnd, 1);
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel))
			helper.renderBlock(0.955, yStart, 0, 0.99, yEnd, 1);
	}
	
	private void renderVessel(IBlockAccess world, int x, int y, int z) {
		double xStart = (doExtendVessel(world.getBlockTileEntity(x - 1, y, z)))? 0: 0.05;
		double xEnd = (doExtendVessel(world.getBlockTileEntity(x + 1, y, z)))? 1: 0.95;
		double zStart = (doExtendVessel(world.getBlockTileEntity(x, y, z - 1)))? 0: 0.05;
		double zEnd = (doExtendVessel(world.getBlockTileEntity(x, y, z + 1)))? 1: 0.95;
		double yStart = (doExtendVessel(world.getBlockTileEntity(x, y - 1, z)))? 0: 0.2;
		double yEnd = (doExtendVessel(world.getBlockTileEntity(x, y + 1, z)))? 1: 0.95;
		
		helper.setTexture(Core.doubleBlock.getBlockTexture(world, x, y, z, 0));
		helper.renderBlock(xStart, yStart, zStart, xEnd, yEnd, zEnd);
		
		//Top Sides
		//Right hand side
		if(!(world.getBlockTileEntity(x, y + 1, z) instanceof TilePressureVessel))
			renderSide(world, x, y, z, 0.93, 0.96);
		if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel))
			renderSide(world, x, y, z, 0.2, 0.24);
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel)) {
			//Side
			helper.setTexture(Block.blockLapis);
			helper.renderBlock(0.95, yStart, 0, 1, yEnd, 0.05);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) && !world.isAirBlock(x, y - 1, z)) {
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.6, 0, 0.1, 0.9, 0.2, 0.4);
			}
		}
				
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel)) {
			//Side
			helper.setTexture(Block.blockLapis);
			helper.renderBlock(0, yStart, 0.95, 0.05, yEnd, 1);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.1, 0, 0.6, 0.4, 0.2, 0.9);
			}
		}
				
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel)) {
			//Side
			helper.setTexture(Block.blockLapis);
			helper.renderBlock(0, yStart, 0, 0.05, yEnd, 0.05);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.1, 0, 0.1, 0.4, 0.2, 0.4);
			}
		}
				
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x , y, z + 1) instanceof TilePressureVessel)) {
			//Side
			helper.setTexture(Block.blockLapis);
			helper.renderBlock(0.95, yStart, 0.95, 1, yEnd, 1);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.6, 0, 0.6, 0.9, 0.2, 0.9);
			}
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		helper = new RenderHelper(render, world, x, y, z);
		
		render.renderAllFaces = true;
		TileEntity tile = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileAirCompressor) {
			TileAirCompressor compressor = (TileAirCompressor) tile;
			ForgeDirection facing = compressor.facing;
			int meta = world.getBlockMetadata(x, y, z);
			if(meta == DoubleMeta.COMPRESSOR_BASE) {
				new RenderCompressorBase(render).setCoords(world, x, y, z).setDir(((TileAirCompressor) tile).facing).render();
			} else {
				new RenderCompressorTop(render).setCoords(world, x, y, z).setDir(((TileAirCompressor) tile).facing).render();
			}
		} else if (tile instanceof TilePressureVessel) {
			renderVessel(world, x, y, z);
		} else if (tile instanceof TileVat) {
			new RenderVat(render).setCoords(world, x, y, z).setDir(((TileVat) tile).facing).render();
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false;
		
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.BLOCK_DOUBLE;
	}
}
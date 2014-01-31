package mariculture.core.render;

import mariculture.core.blocks.TileVat;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.RenderIds;
import mariculture.diving.TileAirCompressor;
import mariculture.diving.render.RenderCompressorBase;
import mariculture.diving.render.RenderCompressorTop;
import mariculture.factory.blocks.TilePressureVessel;
import mariculture.factory.render.RenderPressureVessel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderDouble implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks render) {
		if(meta == DoubleMeta.VAT) {
			new RenderVat(render).render();
		} else if (meta == DoubleMeta.COMPRESSOR_BASE) {
			new RenderCompressorBase(render).render();
		} else if(meta == DoubleMeta.COMPRESSOR_TOP) {
			new RenderCompressorTop(render).render();
		} else if(meta == DoubleMeta.PRESSURE_VESSEL) {
			new RenderPressureVessel(render).render();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {	
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TilePressureVessel) {
			return new RenderPressureVessel(render).setCoords(world, x, y, z).render();
		} else if (tile instanceof TileVat) {
			return new RenderVat(render).setCoords(world, x, y, z).setDir(((TileVat)tile).facing).render();
		} else if (tile instanceof TileAirCompressor) {
			if(world.getBlockMetadata(x, y, z) == DoubleMeta.COMPRESSOR_BASE) {
				return new RenderCompressorBase(render).setCoords(world, x, y, z).setDir(((TileAirCompressor)tile).facing).render();
			} else {
				return new RenderCompressorTop(render).setCoords(world, x, y, z).setDir(((TileAirCompressor)tile).facing).render();
			}
		}
		/*
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
			new RenderPressureVessel(render).setCoords(world, x, y, z).render();
		} else if (tile instanceof TileVat) {
			new RenderVat(render).setCoords(world, x, y, z).setDir(((TileVat) tile).facing).render();
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false; */
		
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
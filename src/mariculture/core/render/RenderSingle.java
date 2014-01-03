package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileBucket;
import mariculture.core.blocks.TileForge;
import mariculture.core.blocks.TileOyster;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.SingleMeta;
import mariculture.diving.render.ModelAirPump;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileGeyser;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.render.ModelFLUDD;
import mariculture.factory.render.ModelTurbineGas;
import mariculture.factory.render.ModelTurbineWater;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileSift;
import mariculture.fishery.render.ModelFeeder;
import mariculture.fishery.render.ModelSift;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

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
	}
	
	private void renderNet(IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block) {
		render.setOverrideBlockTexture(Core.oysterBlock.getIcon(0, BlockOyster.NET));
		render.setRenderBounds(0, -0.115, 0, 1, -0.05, 1);
		render.renderStandardBlock(block, x, y, z);
	}
	
	private void renderGeyser(TileGeyser tile, IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block) {
		if(tile.orientation == ForgeDirection.NORTH) {
			render.setOverrideBlockTexture(Block.hopperBlock.getIcon(0, 0));
			render.setRenderBounds(0.1, 0.1, 0.85, 0.9, 0.9, 1);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.stoneSingleSlab.getIcon(0, 0));
			render.setRenderBounds(0.25, 0.25, 0.76, 0.75, 0.75, 0.85);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.waterStill.getIcon(0, 0));
			render.setRenderBounds(0.35, 0.35, 0.75, 0.65, 0.65, 0.76);
			render.renderStandardBlock(block, x, y, z);
		} else if(tile.orientation == ForgeDirection.SOUTH) {
			render.setOverrideBlockTexture(Block.hopperBlock.getIcon(0, 0));
			render.setRenderBounds(0.1, 0.1, 0, 0.9, 0.9, 0.15);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.stoneSingleSlab.getIcon(0, 0));
			render.setRenderBounds(0.25, 0.25, 0.15, 0.75, 0.75, 0.24);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.waterStill.getIcon(0, 0));
			render.setRenderBounds(0.35, 0.35, 0.24, 0.65, 0.65, 0.25);
			render.renderStandardBlock(block, x, y, z);
		} else if (tile.orientation == ForgeDirection.WEST) {
			render.setOverrideBlockTexture(Block.hopperBlock.getIcon(0, 0));
			render.setRenderBounds(0.85, 0.1, 0.1, 1, 0.9, 0.9);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.stoneSingleSlab.getIcon(0, 0));
			render.setRenderBounds(0.76, 0.25, 0.25, 0.85, 0.75, 0.75);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.waterStill.getIcon(0, 0));
			render.setRenderBounds(0.75, 0.35, 0.35, 0.76, 0.65, 0.65);
			render.renderStandardBlock(block, x, y, z);
		} else if(tile.orientation == ForgeDirection.EAST) {
			render.setOverrideBlockTexture(Block.hopperBlock.getIcon(0, 0));
			render.setRenderBounds(0, 0.1, 0.1, 0.15, 0.9, 0.9);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.stoneSingleSlab.getIcon(0, 0));
			render.setRenderBounds(0.15, 0.25, 0.25, 0.24, 0.75, 0.75);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.waterStill.getIcon(0, 0));
			render.setRenderBounds(0.24, 0.35, 0.35, 0.25, 0.65, 0.65);
			render.renderStandardBlock(block, x, y, z);
		} else if(tile.orientation == ForgeDirection.UP) {
			render.setOverrideBlockTexture(Block.hopperBlock.getIcon(0, 0));
			render.setRenderBounds(0.1, 0, 0.1, 0.9, 0.15, 0.9);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.stoneSingleSlab.getIcon(0, 0));
			render.setRenderBounds(0.25, 0.15, 0.25, 0.75, 0.24, 0.75);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.waterStill.getIcon(0, 0));
			render.setRenderBounds(0.35, 0.24, 0.35, 0.65, 0.25, 0.65);
			render.renderStandardBlock(block, x, y, z);
		} else if(tile.orientation == ForgeDirection.DOWN) {
			render.setOverrideBlockTexture(Block.hopperBlock.getIcon(0, 0));
			render.setRenderBounds(0.1, 0.85, 0.1, 0.9, 1, 0.9);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.stoneSingleSlab.getIcon(0, 0));
			render.setRenderBounds(0.25, 0.76, 0.25, 0.75, 0.85, 0.75);
			render.renderStandardBlock(block, x, y, z);
			render.setOverrideBlockTexture(Block.waterStill.getIcon(0, 0));
			render.setRenderBounds(0.35, 0.75, 0.35, 0.65, 0.76, 0.65);
			render.renderStandardBlock(block, x, y, z);
		}
	}
	

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks render) {
		if(block.blockID == Core.singleBlocks.blockID && meta == SingleMeta.GEYSER) {
			renderGeyser(render, Block.stone, meta);
		}
	}
	
	public static void renderBlockAsItem(RenderBlocks render, Icon icon, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		render.renderMinX = minX;
	    render.renderMinY = minY;
	    render.renderMinZ = minZ;
	    render.renderMaxX = maxX;
	    render.renderMaxY = maxY;
	    render.renderMaxZ = maxZ;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, -1.0F, 0.0F);
	    render.renderFaceYNeg(Block.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 1.0F, 0.0F);
	    render.renderFaceYPos(Block.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, -1.0F);
	    render.renderFaceZNeg(Block.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, 1.0F);
	    render.renderFaceZPos(Block.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
	    render.renderFaceXNeg(Block.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(1.0F, 0.0F, 0.0F);
	    render.renderFaceXPos(Block.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	}
	
	public static void renderGeyser (RenderBlocks render, Block block, int meta) {
        render.uvRotateEast = 0;
        render.uvRotateWest = 0;
        render.uvRotateSouth = 0;
        render.uvRotateNorth = 0;
        render.uvRotateTop = 0;
        render.uvRotateBottom = 0;
        renderBlockAsItem(render, Block.hopperBlock.getIcon(0, 0), 0, 0, 0, 1, 0.1D, 1);
        renderBlockAsItem(render, Block.stoneSingleSlab.getIcon(0, 0), 0.15D, 0.1D, 0.15D, 0.85D, 0.15D, 0.85D);
        renderBlockAsItem(render, Block.waterStill.getIcon(0, 0), 0.25D, 0.15D, 0.25D, 0.75D, 0.16D, 0.75D);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		render.renderAllFaces = true;
		TileEntity tile = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileBucket) {
			renderBucket((TileBucket) tile, world, x, y, z, render, Block.stone);
		}
		
		if(tile instanceof TileGeyser) {
			renderGeyser((TileGeyser) tile, world, x, y, z, render, Block.stone);
		}
				
		if(world.getBlockId(x, y, z) == Core.oysterBlock.blockID && world.getBlockMetadata(x, y, z) == BlockOyster.NET) {
			renderNet(world, x, y, z, render, Block.stone);
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
		return RenderIds.BLOCK_SINGLE;
	}
}
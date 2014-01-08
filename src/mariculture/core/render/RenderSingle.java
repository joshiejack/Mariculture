package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileIngotCaster;
import mariculture.core.blocks.TileOyster;
import mariculture.core.handlers.IngotCastingHandler;
import mariculture.core.helpers.RenderHelper;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.MetalRates;
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

	public RenderHelper helper;
	public RenderAnvil anvil;
	
	//Models
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		if (tileEntity instanceof TileAirPump) {
			bindTexture(resource);
			((ModelAirPump) model).render((TileAirPump) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileOyster) {
			bindTexture(resource);
			((ModelOyster) model).render((TileOyster) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileSift) {
			bindTexture(resource);
			((ModelSift) model).render((TileSift) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileFeeder) {
			bindTexture(resource);
			((ModelFeeder) model).render((TileFeeder) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileTurbineWater) {
			bindTexture(resource);
			((ModelTurbineWater) model).render((TileTurbineWater) tileEntity, x, y, z);
		}
		
		if (tileEntity instanceof TileTurbineGas) {
			bindTexture(resource);
			((ModelTurbineGas) model).render((TileTurbineGas) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileFLUDDStand) {
			bindTexture(resource);
			((ModelFLUDD) model).render((TileFLUDDStand) tileEntity, x, y, z);
		}
	}
	
	//Net
	private void renderNet(IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block) {
		render.setOverrideBlockTexture(Core.oysterBlock.getIcon(0, BlockOyster.NET));
		render.setRenderBounds(0, -0.115, 0, 1, -0.05, 1);
		render.renderStandardBlock(block, x, y, z);
	}
	
	//Geyser
	private void renderGeyser(TileGeyser tile, IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block) {
		if(tile.orientation == ForgeDirection.NORTH) {
			helper.setTexture(Block.hopperBlock);
			helper.renderBlock(0.1, 0.1, 0.85, 0.9, 0.9, 1);
			helper.setTexture(Block.stoneSingleSlab);
			helper.renderBlock(0.25, 0.25, 0.76, 0.75, 0.75, 0.85);
			helper.setTexture(Block.waterStill);
			helper.renderBlock(0.35, 0.35, 0.75, 0.65, 0.65, 0.76);
		} else if(tile.orientation == ForgeDirection.SOUTH) {
			helper.setTexture(Block.hopperBlock);
			helper.renderBlock(0.1, 0.1, 0, 0.9, 0.9, 0.15);
			helper.setTexture(Block.stoneSingleSlab);
			helper.renderBlock(0.25, 0.25, 0.15, 0.75, 0.75, 0.24);
			helper.setTexture(Block.waterStill);
			helper.renderBlock(0.35, 0.35, 0.24, 0.65, 0.65, 0.25);
		} else if (tile.orientation == ForgeDirection.WEST) {
			helper.setTexture(Block.hopperBlock);
			helper.renderBlock(0.85, 0.1, 0.1, 1, 0.9, 0.9);
			helper.setTexture(Block.stoneSingleSlab);
			helper.renderBlock(0.76, 0.25, 0.25, 0.85, 0.75, 0.75);
			helper.setTexture(Block.waterStill);
			helper.renderBlock(0.75, 0.35, 0.35, 0.76, 0.65, 0.65);
		} else if(tile.orientation == ForgeDirection.EAST) {
			helper.setTexture(Block.hopperBlock);
			helper.renderBlock(0, 0.1, 0.1, 0.15, 0.9, 0.9);
			helper.setTexture(Block.stoneSingleSlab);
			helper.renderBlock(0.15, 0.25, 0.25, 0.24, 0.75, 0.75);
			helper.setTexture(Block.waterStill);
			helper.renderBlock(0.24, 0.35, 0.35, 0.25, 0.65, 0.65);
		} else if(tile.orientation == ForgeDirection.UP) {
			helper.setTexture(Block.hopperBlock);
			helper.renderBlock(0.1, 0, 0.1, 0.9, 0.15, 0.9);
			helper.setTexture(Block.stoneSingleSlab);
			helper.renderBlock(0.25, 0.15, 0.25, 0.75, 0.24, 0.75);
			helper.setTexture(Block.waterStill);
			helper.renderBlock(0.35, 0.24, 0.35, 0.65, 0.25, 0.65);
		} else if(tile.orientation == ForgeDirection.DOWN) {
			helper.setTexture(Block.hopperBlock);
			helper.renderBlock(0.1, 0.85, 0.1, 0.9, 1, 0.9);
			helper.setTexture(Block.stoneSingleSlab);
			helper.renderBlock(0.25, 0.76, 0.25, 0.75, 0.85, 0.75);
			helper.setTexture(Block.waterStill);
			helper.renderBlock(0.35, 0.75, 0.35, 0.65, 0.76, 0.65);
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

	public void renderCaster(IBlockAccess world, int x, int y, int z) {
		TileIngotCaster tile = (TileIngotCaster) world.getBlockTileEntity(x, y, z);
		//Render Ingot in slot 1
		if(tile.getStackInSlot(0) != null) {
			helper.setTexture(IngotCastingHandler.getTexture(tile.getStackInSlot(0)));
			helper.renderBlock(0.1, 0.05, 0.1, 0.4, 0.75, 0.4);
			helper.renderBlock(0.15, 0.75, 0.15, 0.35, 0.78, 0.35);
		}
		
		//Render Ingot in slot 2
		if(tile.getStackInSlot(1) != null) {
			helper.setTexture(IngotCastingHandler.getTexture(tile.getStackInSlot(1)));
			helper.renderBlock(0.1, 0.05, 0.6, 0.4, 0.75, 0.9);
			helper.renderBlock(0.15, 0.75, 0.65, 0.35, 0.78, 0.85);
		}
		
		//Render Ingot in slot 3
		if(tile.getStackInSlot(2) != null) {
			helper.setTexture(IngotCastingHandler.getTexture(tile.getStackInSlot(2)));
			helper.renderBlock(0.6, 0.05, 0.6, 0.9, 0.75, 0.9);
			helper.renderBlock(0.65, 0.75, 0.65, 0.85, 0.78, 0.85);
		}
		
		//Render Ingot in slot4
		if(tile.getStackInSlot(3) != null) {
			helper.setTexture(IngotCastingHandler.getTexture(tile.getStackInSlot(3)));
			helper.renderBlock(0.6, 0.05, 0.1, 0.9, 0.75, 0.6);
			helper.renderBlock(0.65, 0.75, 0.15, 0.85, 0.78, 0.65);
		}
		
		if(tile.getFluid() != null) {
			helper.renderWorldBlock(tile.getFluid(), MetalRates.INGOT * 4, 0.4D, 0, 0, 0);
		}
		
		helper.setTexture(Core.doubleBlock, DoubleMeta.VAT);
		helper.renderBlock(0, 0, 0, 1, 0.05, 1);
		//Sides
		helper.setTexture(Core.singleBlocks, SingleMeta.INGOT_CASTER);
		helper.renderBlock(0, 0.05, 0, 1, 0.85, 0.1);
		helper.renderBlock(0, 0.05, 0.9, 1, 0.85, 1);
		helper.renderBlock(0, 0.05, 0.1, 0.1, 0.85, 0.9);
		helper.renderBlock(0.9, 0.05, 0.1, 1, 0.85, 0.9);
		
		helper.setTexture(Core.doubleBlock, DoubleMeta.VAT);
		//Crossbars
		helper.renderBlock(0.4, 0.05, 0.1, 0.6, 0.85, 0.9);
		helper.renderBlock(0.1, 0.05, 0.4, 0.4, 0.85, 0.6);
		helper.renderBlock(0.6, 0.05, 0.4, 0.9, 0.85, 0.6);
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		helper = new RenderHelper(render, world, x, y, z);
		
		render.renderAllFaces = true;
		TileEntity tile = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileGeyser)
			renderGeyser((TileGeyser) tile, world, x, y, z, render, Block.stone);
		if(world.getBlockId(x, y, z) == Core.oysterBlock.blockID && world.getBlockMetadata(x, y, z) == BlockOyster.NET)
			renderNet(world, x, y, z, render, Block.stone);
		if(tile instanceof TileIngotCaster) {
			renderCaster(world, x, y, z);
		}
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4) {
			if(anvil == null)
				anvil = new RenderAnvil(helper);
			anvil.renderBlockAnvilMetadata(x, y, z, meta - 7);
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
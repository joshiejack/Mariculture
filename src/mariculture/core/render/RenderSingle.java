package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileIngotCaster;
import mariculture.core.blocks.TileOyster;
import mariculture.core.helpers.ItemRenderHelper;
import mariculture.core.helpers.RenderHelper;
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
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
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
	
	public ItemRenderHelper itemHelper;

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks render) {
		itemHelper = new ItemRenderHelper(render);
		if(block.blockID == Core.singleBlocks.blockID) {
			if(meta == SingleMeta.GEYSER)
				renderGeyserItem();
			if(meta == SingleMeta.ANVIL_1)
				new RenderAnvil(render).render();
			if(meta == SingleMeta.INGOT_CASTER)
				new RenderCaster(render).render();
		}
	}
	
	public void renderGeyserItem() {
		itemHelper.start();
		itemHelper.setTexture(Block.hopperBlock);
		itemHelper.renderAsItem(0D, 0D, 0D, 1D, 0.1D, 1D);
		itemHelper.setTexture(Block.stoneSingleSlab);
		itemHelper.renderAsItem(0.15D, 0.1D, 0.15D, 0.85D, 0.15D, 0.85D);
		itemHelper.setTexture(Block.waterStill);
		itemHelper.renderAsItem(0.25D, 0.15D, 0.25D, 0.75D, 0.16D, 0.75D);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        itemHelper.end();
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
			new RenderCaster(render).setCoords(world, x, y, z).render();
		}
		
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4) {
			int facing = meta - 7;
			RenderBase anvil = new RenderAnvil(render).setCoords(world, x, y, z);
			if(facing == 3)
				anvil.setDir(ForgeDirection.SOUTH).render();
			if(facing == 2)
				anvil.setDir(ForgeDirection.WEST).render();
			if(facing == 1)
				anvil.setDir(ForgeDirection.NORTH).render();
			if(facing == 0)
				anvil.setDir(ForgeDirection.EAST).render();
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
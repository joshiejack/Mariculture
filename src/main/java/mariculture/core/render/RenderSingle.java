package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileAnvil;
import mariculture.core.blocks.TileIngotCaster;
import mariculture.core.blocks.TileOldOyster;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.SingleMeta;
import mariculture.diving.render.ModelAirPump;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileGeyser;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineHand;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.render.ModelFLUDD;
import mariculture.factory.render.ModelTurbineGas;
import mariculture.factory.render.ModelTurbineHand;
import mariculture.factory.render.ModelTurbineWater;
import mariculture.factory.render.RenderGeyser;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileSift;
import mariculture.fishery.render.ModelFeeder;
import mariculture.fishery.render.ModelSift;
import mariculture.fishery.render.RenderNet;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderSingle extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	private ModelBase model;
	private ResourceLocation resource;

	public RenderSingle() {}
	
	public RenderSingle(ModelBase model, ResourceLocation resource) {
		this.model = model;
		this.resource = resource;
	}
	
	//Models
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		if (tileEntity instanceof TileAirPump) {
			bindTexture(resource);
			((ModelAirPump) model).render((TileAirPump) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileOldOyster) {
			bindTexture(resource);
			((ModelOyster) model).render((TileOldOyster) tileEntity, x, y, z);
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
		
		if (tileEntity instanceof TileTurbineHand) {
			bindTexture(resource);
			((ModelTurbineHand) model).render((TileTurbineHand) tileEntity, x, y, z);
		}

		if (tileEntity instanceof TileFLUDDStand) {
			bindTexture(resource);
			((ModelFLUDD) model).render((TileFLUDDStand) tileEntity, x, y, z);
		}
	}
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks render) {
		if(block == Core.singleBlocks) {
			if(meta == SingleMeta.ANVIL_1)
				new RenderAnvil(render).render();
			if(meta == SingleMeta.INGOT_CASTER)
				new RenderCaster(render).render();
			if(meta == SingleMeta.GEYSER)
				new RenderGeyser(render).render();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		TileEntity tile = world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(tile instanceof TileGeyser) {
			return new RenderGeyser(render).setCoords(world, x, y, z).setDir(((TileGeyser)tile).orientation).render();
		} else if (tile instanceof TileIngotCaster) {
			return new RenderCaster(render).setCoords(world, x, y, z).render();
		} else if (world.getBlock(x, y, z) == Core.oysterBlock && meta == BlockOyster.NET) {
			return new RenderNet(render).setCoords(world, x, y, z).render();
		} else if (tile instanceof TileAnvil) {
			ForgeDirection facing = ((meta - 7) == 3)? ForgeDirection.SOUTH: ((meta - 7) == 2)? 
					ForgeDirection.WEST: ((meta - 7) == 1)? ForgeDirection.NORTH: ForgeDirection.EAST;
			return new RenderAnvil(render).setCoords(world, x, y, z).setDir(facing).render();
		}
		
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.BLOCK_SINGLE;
	}
}
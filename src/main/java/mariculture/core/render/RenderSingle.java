package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileAnvil;
import mariculture.core.blocks.TileBlockCaster;
import mariculture.core.blocks.TileIngotCaster;
import mariculture.core.blocks.TileOyster;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.RenderMeta;
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
import net.minecraftforge.common.ForgeDirection;
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
		if(block.blockID == Core.rendered.blockID) {
			if(meta == RenderMeta.ANVIL_1)
				new RenderAnvil(render).render();
			if(meta == RenderMeta.INGOT_CASTER)
				new RenderIngotCaster(render).render();
			if(meta == RenderMeta.BLOCK_CASTER)
				new RenderBlockCaster(render).render();
			if(meta == RenderMeta.GEYSER)
				new RenderGeyser(render).render();
		} else if (block.blockID == Core.oyster.blockID) {
			if(meta < BlockOyster.NET) {
				new RenderOyster(render).render();
			}
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(tile instanceof TileGeyser) {
			return new RenderGeyser(render).setCoords(world, x, y, z).setDir(((TileGeyser)tile).orientation).render();
		} else if (tile instanceof TileIngotCaster) {
			return new RenderIngotCaster(render).setCoords(world, x, y, z).render();
		} else if (tile instanceof TileBlockCaster) {
			return new RenderBlockCaster(render).setCoords(world, x, y, z).render();
		} else if (world.getBlockId(x, y, z) == Core.oyster.blockID && meta == BlockOyster.NET) {
			return new RenderNet(render).setCoords(world, x, y, z).render();
		} else if (tile instanceof TileAnvil) {
			ForgeDirection facing = ((meta - 7) == 3)? ForgeDirection.SOUTH: ((meta - 7) == 2)? 
					ForgeDirection.WEST: ((meta - 7) == 1)? ForgeDirection.NORTH: ForgeDirection.EAST;
			return new RenderAnvil(render).setCoords(world, x, y, z).setDir(facing).render();
		} else if (tile instanceof TileOyster) {
			ForgeDirection facing = (meta == 3)? ForgeDirection.SOUTH: ((meta) == 2)? 
					ForgeDirection.WEST: (meta == 1)? ForgeDirection.NORTH: ForgeDirection.EAST;
			return new RenderOyster(render).setCoords(world, x, y, z).setDir(facing).render();
		}
		
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
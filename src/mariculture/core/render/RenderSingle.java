package mariculture.core.render;

import mariculture.core.blocks.TileAirPump;
import mariculture.core.blocks.TileOyster;
import mariculture.diving.render.ModelAirPump;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.render.ModelFLUDD;
import mariculture.factory.render.ModelTurbineWater;
import mariculture.factory.render.ModelTurbineGas;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileNet;
import mariculture.fishery.blocks.TileSift;
import mariculture.fishery.render.ModelFeeder;
import mariculture.fishery.render.ModelNet;
import mariculture.fishery.render.ModelSift;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderSingle extends TileEntitySpecialRenderer {
	private final ModelBase model;
	private final ResourceLocation resource;

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
}
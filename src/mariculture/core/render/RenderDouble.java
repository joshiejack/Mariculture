package mariculture.core.render;

import mariculture.diving.TileAirCompressor;
import mariculture.diving.TileAirCompressorPower;
import mariculture.diving.render.ModelAirCompressor;
import mariculture.diving.render.ModelAirCompressorPower;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderDouble extends TileEntitySpecialRenderer {

	private final ModelBase model;
	private final ResourceLocation resource;

	public RenderDouble(final ModelBase model, final ResourceLocation resource) {
		this.model = model;
		this.resource = resource;
	}

	@Override
	public void renderTileEntityAt(final TileEntity tileEntity, final double x, final double y, final double z, final float tick) {
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
}
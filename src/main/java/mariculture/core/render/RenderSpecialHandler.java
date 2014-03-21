package mariculture.core.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderSpecialHandler extends TileEntitySpecialRenderer {
	private ResourceLocation resource;
	private IModelMariculture model;
	
	public RenderSpecialHandler(IModelMariculture model, ResourceLocation resource) {
		this.resource = resource;
		this.model = model;
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
		bindTexture(resource);
		model.render(tile, x, y, z);
	}
}

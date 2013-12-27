package mariculture.transport.render;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderSpeedBoatItem implements IItemRenderer {
	private static final ResourceLocation resource = new ResourceLocation("mariculture:textures/entity/speedboat.png");
	private ModelSpeedBoat modelBoat;
	private static final float scale = (float) (1.0 / 20.0);

	public RenderSpeedBoatItem() {
		this.modelBoat = new ModelSpeedBoat();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
		modelBoat.renderInventory(type, scale);
	}
}

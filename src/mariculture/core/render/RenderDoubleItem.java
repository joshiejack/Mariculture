package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.lib.DoubleMeta;
import mariculture.diving.render.ModelAirCompressor;
import mariculture.diving.render.ModelAirCompressorPower;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderDoubleItem implements IItemRenderer {
	private static final float scale = (float) (1.0 / 16.0);
	private static final ResourceLocation COMPRESSOR = new ResourceLocation("mariculture", "textures/blocks/air_compressor_texture.png");
	private static final ResourceLocation PRESSURE_VESSEL = new ResourceLocation("mariculture", "textures/blocks/pressure_vessel_texture.png");
	private static final ResourceLocation COMPRESSOR_POWER = new ResourceLocation("mariculture", "textures/blocks/air_compressor_power_texture.png");
	private final ModelAirCompressorPower power = new ModelAirCompressorPower(scale);
	private final ModelAirCompressor compressor = new ModelAirCompressor(scale);

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
		if (item.itemID == Core.doubleBlock.blockID && item.getItemDamage() == DoubleMeta.AIR_COMPRESSOR) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(COMPRESSOR);
			compressor.renderInventory(type);
		}

		if (item.itemID == Core.doubleBlock.blockID && item.getItemDamage() == DoubleMeta.PRESSURE_VESSEL) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(PRESSURE_VESSEL);
			compressor.renderInventory(type);
		}

		if (item.itemID == Core.doubleBlock.blockID && item.getItemDamage() == DoubleMeta.AIR_COMPRESSOR_POWER) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(COMPRESSOR_POWER);
			power.renderInventory(type);
		}
	}
}

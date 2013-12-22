package mariculture.core.gui.feature;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import mariculture.core.Mariculture;
import mariculture.core.gui.GuiMariculture;

public class Feature {
	protected static ResourceLocation texture = new ResourceLocation(Mariculture.modid, "textures/gui/gui_elements.png");
	protected TextureManager tm;
	
	public Feature() {
		tm = Minecraft.getMinecraft().renderEngine;
	}

	public void draw(GuiMariculture gui, int x, int y) {
		tm.bindTexture(texture);
	}
	
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		return;
	}
}

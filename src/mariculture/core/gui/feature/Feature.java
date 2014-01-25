package mariculture.core.gui.feature;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import mariculture.Mariculture;
import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;

public class Feature {
	public static final ResourceLocation texture = new ResourceLocation(Mariculture.modid, "textures/gui/gui_elements.png");
	protected TextureManager tm;
	
	public Feature() {
		tm = Minecraft.getMinecraft().renderEngine;
	}

	public void draw(GuiMariculture gui, int x, int y, int mouseX, int mouseY) {
		tm.bindTexture(texture);
	}
	
	public void addTooltip(List tooltip, int mouseX, int mouseY) {
		return;
	}

	public void mouseClicked(int mouseX, int mouseY) {
		return;
	}
	
	public void addLine(String start, int i, String note, List tooltip) {
		String line = start + "." + note.toLowerCase().replaceAll("_", "\\.") + ".text" + (i + 1);
		if(!line.equals(StatCollector.translateToLocal(line))) {
			tooltip.add(StatCollector.translateToLocal(line));
		}
	}
}

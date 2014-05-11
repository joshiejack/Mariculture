package mariculture.fishery.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class ScannerFont extends FontRenderer {
	public ScannerFont(GameSettings settings, ResourceLocation resource, TextureManager tm, boolean unicode) {
		super(settings, resource, tm, unicode);
	}
}

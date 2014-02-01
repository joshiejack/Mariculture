package mariculture.core.guide;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/** 
 * Takes care of loading and drawing previews to the screen.
 * @author dayanto
 */
public class PagePicture {
	private File file;
	private BufferedImage image;
	private DynamicTexture previewTexture;
	private ResourceLocation resourceLocation;
	private TextureManager textureManager;
	
	public PagePicture(TextureManager textureManager, File file) {
		this.textureManager = textureManager;
		this.file = file;
	}
	
	/**
	 * Attempts to load the image. Returns whether it was successful or not.
	 */
	public boolean loadPreview() {
		try {
			image = ImageIO.read(file);
			previewTexture = new DynamicTexture(image);
			resourceLocation = textureManager.getDynamicTextureLocation("preivew", previewTexture);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void drawCenteredImage(int x, int y, int width, int height) {
		if(previewTexture == null) {
			boolean successful = loadPreview();
			if(!successful) return;
		}
		
		drawImage(x + (width - image.getWidth()) / 2, y + (height - image.getHeight()) / 2, image.getWidth(), image.getHeight());
	}
	
	private void drawImage(int xPos, int yPos, int width, int height) {
		previewTexture.updateDynamicTexture();
		Tessellator tessellator = Tessellator.instance;
		textureManager.bindTexture(resourceLocation);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(xPos        , yPos + height, 0, 0.0, 1.0);
		tessellator.addVertexWithUV(xPos + width, yPos + height, 0, 1.0, 1.0);
		tessellator.addVertexWithUV(xPos + width, yPos         , 0, 1.0, 0.0);
		tessellator.addVertexWithUV(xPos        , yPos         , 0, 0.0, 0.0);
		tessellator.draw();
	}
}
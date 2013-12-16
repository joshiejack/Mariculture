package mariculture.fishery.render;

import mariculture.fishery.blocks.TileNet;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

public class ModelNet extends ModelBase {
	private ModelRenderer net;
	private final float scale;

	public ModelNet(float scale) {
		this.scale = scale;
		textureWidth = 64;
		textureHeight = 32;

		net = new ModelRenderer(this, 0, 0);
		net.addBox(-8F, 0F, -8F, 16, 1, 16);
		net.setRotationPoint(0F, 0F, 0F);
		net.setTextureSize(64, 32);
		net.mirror = true;
	}

	public void render(TileNet sift, double x, double y, double z) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslated(x + 0.5F, y - 0.05, z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);

		net.render((float) (1.0 / 16.0));

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	public void renderInventory(ItemRenderType type) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glRotatef(180, 0F, 0F, 1F);
		GL11.glRotatef(90, 0F, 1F, 0F);
		switch (type) {
		case INVENTORY:
			GL11.glScalef(25F, 25F, 25F);
			break;
		default:
			GL11.glScalef(1.3F, 1.3F, 1.3F);
			break;
		}
		GL11.glTranslatef(0.1F, -0.35F, 0.2F);

		net.render(scale);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}

package mariculture.fishery.render;

import mariculture.fishery.blocks.TileFeeder;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

public class ModelFeeder extends ModelBase {
	// fields
	private ModelRenderer basket;
	private final float scale;

	public ModelFeeder(float scale) {

		this.scale = scale;
		textureWidth = 64;
		textureHeight = 32;

		basket = new ModelRenderer(this, 0, 0);
		basket.addBox(-8F, 0F, -8F, 16, 16, 16);
		basket.setRotationPoint(0F, 0F, 0F);
		basket.setTextureSize(64, 32);
		basket.mirror = true;
	}

	public void render(TileFeeder tile, double x, double y, double z) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslated(x + 0.5F, y + 1F, z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);

		basket.render((float) (1.0 / 16.0));

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	public void renderInventory(ItemRenderType type) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		switch (type) {
		case INVENTORY:
			GL11.glRotatef(35, 1, 0, 0);
			GL11.glRotatef(45, 0, -5, 0);
			GL11.glScalef(12F, 12F, 12F);
			GL11.glTranslatef(0F, -0.05F, -0.9F);
			break;
		case ENTITY:
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glTranslatef(0F, -0.5F, 0F);
			break;
		default:
			GL11.glRotatef(35, 1, 0, 0);
			GL11.glRotatef(45, 0, -5, 0);
			GL11.glRotatef(180, 1, 0, 0);
			GL11.glTranslatef(0.4F, 0F, -0.1F);
			break;
		}

		basket.render((float) (1.0 / 20.0));

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}

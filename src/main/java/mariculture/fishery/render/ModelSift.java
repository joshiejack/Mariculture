package mariculture.fishery.render;

import mariculture.fishery.blocks.TileSift;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

public class ModelSift extends ModelBase {
	private ModelRenderer edge1;
	private ModelRenderer edge2;
	private ModelRenderer edge3;
	private ModelRenderer edge4;
	private ModelRenderer netting;
	private ModelRenderer leg1;
	private ModelRenderer leg2;
	private ModelRenderer leg3;
	private ModelRenderer leg4;
	private ModelRenderer base;
	private ModelRenderer tray1;
	private ModelRenderer tray2;
	private ModelRenderer tray3;
	private ModelRenderer tray4;
	private float scale;

	public ModelSift(float scale) {

		this.scale = scale;
		textureWidth = 128;
		textureHeight = 128;

		edge1 = new ModelRenderer(this, 0, 23);
		edge1.addBox(0F, 0F, 0F, 32, 4, 2);
		edge1.setRotationPoint(-8F, 8F, -7F);
		edge1.setTextureSize(128, 128);
		edge1.mirror = true;
		edge2 = new ModelRenderer(this, 0, 23);
		edge2.addBox(0F, 0F, 0F, 32, 4, 2);
		edge2.setRotationPoint(-8F, 8F, 15F);
		edge2.setTextureSize(128, 128);
		edge2.mirror = true;
		edge3 = new ModelRenderer(this, 0, 29);
		edge3.addBox(0F, 0F, 0F, 2, 4, 20);
		edge3.setRotationPoint(22F, 8F, -5F);
		edge3.setTextureSize(128, 128);
		edge3.mirror = true;
		edge4 = new ModelRenderer(this, 0, 29);
		edge4.addBox(0F, 0F, 0F, 2, 4, 20);
		edge4.setRotationPoint(-8F, 8F, -5F);
		edge4.setTextureSize(128, 128);
		edge4.mirror = true;
		netting = new ModelRenderer(this, 0, 0);
		netting.addBox(0F, 0F, 0F, 30, 1, 22);
		netting.setRotationPoint(-7F, 10F, -6F);
		netting.setTextureSize(128, 128);
		netting.mirror = true;
		leg1 = new ModelRenderer(this, 68, 23);
		leg1.addBox(0F, 0F, 0F, 2, 13, 2);
		leg1.setRotationPoint(20F, 11F, 13F);
		leg1.setTextureSize(128, 128);
		leg1.mirror = true;
		leg2 = new ModelRenderer(this, 68, 23);
		leg2.addBox(0F, 0F, 0F, 2, 13, 2);
		leg2.setRotationPoint(-6F, 11F, 13F);
		leg2.setTextureSize(128, 128);
		leg2.mirror = true;
		leg3 = new ModelRenderer(this, 68, 23);
		leg3.addBox(0F, 0F, 0F, 2, 13, 2);
		leg3.setRotationPoint(-6F, 11F, -5F);
		leg3.setTextureSize(128, 128);
		leg3.mirror = true;
		leg4 = new ModelRenderer(this, 68, 23);
		leg4.addBox(0F, 0F, 0F, 2, 13, 2);
		leg4.setRotationPoint(20F, 11F, -5F);
		leg4.setTextureSize(128, 128);
		leg4.mirror = true;
		/** Bottom Tray **/
		tray1 = new ModelRenderer(this, 0, 23);
		tray1.addBox(0F, 0F, 0F, 32, 4, 2);
		tray1.setRotationPoint(-8F, 20F, -7F);
		tray1.setTextureSize(128, 128);
		tray1.mirror = true;
		tray2 = new ModelRenderer(this, 0, 23);
		tray2.addBox(0F, 0F, 0F, 32, 4, 2);
		tray2.setRotationPoint(-8F, 20F, 15F);
		tray2.setTextureSize(128, 128);
		tray2.mirror = true;
		tray3 = new ModelRenderer(this, 0, 29);
		tray3.addBox(0F, 0F, 0F, 2, 4, 20);
		tray3.setRotationPoint(22F, 20F, -5F);
		tray3.setTextureSize(128, 128);
		tray3.mirror = true;
		tray4 = new ModelRenderer(this, 0, 29);
		tray4.addBox(0F, 0F, 0F, 2, 4, 20);
		tray4.setRotationPoint(-8F, 20F, -5F);
		tray4.setTextureSize(128, 128);
		tray4.mirror = true;
		base = new ModelRenderer(this, 0, 53);
		base.addBox(0F, 0F, 0F, 30, 1, 22);
		base.setRotationPoint(-7F, 22F, -6F);
		base.setTextureSize(128, 128);
		base.mirror = true;
	}

	public void render(TileSift sift, double x, double y, double z) {

		int meta = sift.getBlockMetadata();

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		if (meta == 1 || meta == 3) {
			GL11.glTranslated(x + 0.9F, y + 1.2F, z + 0.25F);
			GL11.glRotatef(180, 0F, 0F, 1F);
		} else {
			GL11.glTranslated(x + 0.8F, y + 1.2F, z + 1.05F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(90, 0F, 1F, 0F);
		}

		edge1.render(scale);
		edge2.render(scale);
		edge3.render(scale);
		edge4.render(scale);
		netting.render(scale);

		leg1.render(scale);
		leg2.render(scale);
		leg3.render(scale);
		leg4.render(scale);

		if (meta > 1) {
			base.render(scale);
			tray1.render(scale);
			tray2.render(scale);
			tray3.render(scale);
			tray4.render(scale);
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	public void renderInventory(ItemRenderType type) {
		this.scale = (float) (1.0 / 1.0);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		switch (type) {
		case INVENTORY:
			GL11.glRotatef(35, 1, 0, 0);
			GL11.glRotatef(45, 0, -5, 0);
			GL11.glScalef(0.38F, 0.38F, 0.38F);
			GL11.glTranslatef(6F, 11F, -21F);
			break;
		case ENTITY:
			GL11.glScalef(0.03F, 0.03F, 0.03F);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glTranslatef(0F, -15F, 0F);
			break;
		default:
			GL11.glRotatef(35, 1, 0, 0);
			GL11.glRotatef(45, 0, -5, 0);
			GL11.glRotatef(180, 1, 0, 0);
			GL11.glScalef(0.04F, 0.04F, 0.04F);
			GL11.glTranslatef(5F, -15F, 5F);
			break;
		}

		edge1.render(scale);
		edge2.render(scale);
		edge3.render(scale);
		edge4.render(scale);
		netting.render(scale);

		leg1.render(scale);
		leg2.render(scale);
		leg3.render(scale);
		leg4.render(scale);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}

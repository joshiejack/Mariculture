package mariculture.sealife.render;

import mariculture.sealife.EntityHammerhead;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHammerhead extends Render {
	private static final ResourceLocation resource = new ResourceLocation("mariculture:textures/entity/hammerhead.png");
	private ModelHammerhead model;

	public RenderHammerhead() {
		this.shadowSize = 0.5F;
		this.model = new ModelHammerhead();
	}

	public void render(EntityHammerhead entity, double x, double y, double z, float yaw, float tickTime) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated((float) x, (float) y + 1F, (float) z);
		GL11.glRotatef(180, 0F, 0F, 1F);
		GL11.glRotatef(180.0F + yaw, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(5F, 5F, 5F);

		this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float tickTime) {
		this.render((EntityHammerhead) entity, x, y, z, yaw, tickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return resource;
	}
}

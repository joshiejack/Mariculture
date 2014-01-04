package mariculture.diving.render;

import mariculture.diving.TileAirCompressor;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelAirCompressorPower extends ModelAirCompressor {
	private ModelRenderer baseEdge2;
	private ModelRenderer monitorEMPTY;
	private ModelRenderer baseEdge1;
	private ModelRenderer motor1Fan;
	private ModelRenderer motor2Top;
	private ModelRenderer motor1Base;
	private ModelRenderer motor1Mid;
	private ModelRenderer motor1Top;
	private ModelRenderer fanClip;
	private ModelRenderer monitorStand;
	private ModelRenderer base;
	private ModelRenderer motor2Bottom;
	private ModelRenderer clipFront;
	private ModelRenderer fan;
	private ModelRenderer monitorRED;
	private ModelRenderer monitorORANGE;
	private ModelRenderer monitorYELLOW;
	private ModelRenderer monitorGREEN;

	private float scale;

	public ModelAirCompressorPower() {
		textureWidth = 64;
		textureHeight = 64;
	}

	public void renderInventory(ItemRenderType type) {
		this.scale = (float) (1.0 / 16.0);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		switch (type) {
		case INVENTORY:
			GL11.glRotatef(35, 1, 0, 0);
			GL11.glRotatef(45, 0, -5, 0);
			GL11.glScalef(10F, 10F, 10F);
			GL11.glTranslatef(0F, 0F, -1.2F);
			break;
		default:
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(90, 0F, 1F, 0F);
			GL11.glTranslatef(0.15F, -0.3F, 0.1F);
			break;
		}

	/*	baseEdge2.render(scale);
		monitorEMPTY.render(scale);
		baseEdge1.render(scale);
		motor1Fan.render(scale);
		motor2Top.render(scale);
		motor1Base.render(scale);
		motor1Mid.render(scale);
		motor1Top.render(scale);
		fanClip.render(scale);
		monitorStand.render(scale);
		base.render(scale);
		motor2Bottom.render(scale);
		clipFront.render(scale);
		fan.render(scale); */

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}

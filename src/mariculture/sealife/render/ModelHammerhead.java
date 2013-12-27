package mariculture.sealife.render;

import mariculture.sealife.EntityHammerhead;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

public class ModelHammerhead extends ModelBase {
	private ModelRenderer Head;
	private ModelRenderer Body;

	public ModelHammerhead() {
		textureWidth = 128;
		textureHeight = 128;

		Head = new ModelRenderer(this, 0, 0);
	    Head.addBox(0F, 0F, 0F, 3, 1, 2);
	    Head.setRotationPoint(-1F, 0F, -2F);
	    Head.setTextureSize(128, 128);
	    Head.mirror = true;
	    setRotation(Head, 0F, 0F, 0F);
	    Body = new ModelRenderer(this, 0, 0);
	    Body.addBox(0F, 0F, 0F, 1, 1, 8);
	    Body.setRotationPoint(0F, 0F, 0F);
	    Body.setTextureSize(128, 128);
	    Body.mirror = true;
	    setRotation(Body, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Head.render(f5);
		Body.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	private void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
	}
}

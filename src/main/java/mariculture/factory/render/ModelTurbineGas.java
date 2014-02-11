package mariculture.factory.render;

import mariculture.factory.blocks.TileTurbineBase.EnergyStage;
import mariculture.factory.blocks.TileTurbineGas;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelTurbineGas extends ModelBase {
	private final float scale;
	private ModelRenderer RotorSide4;
	private ModelRenderer RotorBottom;
	private ModelRenderer Top;
	private ModelRenderer RotorSide1;
	private ModelRenderer RotorSide2;
	private ModelRenderer RotorSide3;
	private ModelRenderer Base;
	private ModelRenderer StaticPole;
	private ModelRenderer Blade1;
	private ModelRenderer MovingPole;
	private ModelRenderer Blade2;
	private ModelRenderer Blade3;
	private ModelRenderer Blade4;

	public ModelTurbineGas(float scale) {
		this.scale = scale;
		textureWidth = 128;
	    textureHeight = 128;
	    
	    RotorSide4 = new ModelRenderer(this, 0, 41);
	    RotorSide4.addBox(-7F, -12F, 7F, 14, 9, 1);
	    RotorSide4.setRotationPoint(0F, 0F, 0F);
	    RotorSide4.setTextureSize(128, 128);
	    RotorSide4.mirror = true;
	    setRotation(RotorSide4, 0F, 0F, 0F);
	    RotorBottom = new ModelRenderer(this, 4, 2);
	    RotorBottom.addBox(-7F, -3F, -7F, 14, 1, 14);
	    RotorBottom.setRotationPoint(0F, 0F, 0F);
	    RotorBottom.setTextureSize(128, 128);
	    RotorBottom.mirror = true;
	    setRotation(RotorBottom, 0F, 0F, 0F);
	    Top = new ModelRenderer(this, 69, 29);
	    Top.addBox(-4F, -14F, -4F, 8, 1, 8);
	    Top.setRotationPoint(0F, -1F, 0F);
	    Top.setTextureSize(128, 128);
	    Top.mirror = true;
	    setRotation(Top, 0F, 0F, 0F);
	    RotorSide1 = new ModelRenderer(this, 0, 41);
	    RotorSide1.addBox(-8F, -12F, -7F, 1, 9, 14);
	    RotorSide1.setRotationPoint(0F, 0F, 0F);
	    RotorSide1.setTextureSize(128, 128);
	    RotorSide1.mirror = true;
	    setRotation(RotorSide1, 0F, 0F, 0F);
	    RotorSide2 = new ModelRenderer(this, 0, 41);
	    RotorSide2.addBox(7F, -12F, -7F, 1, 9, 14);
	    RotorSide2.setRotationPoint(0F, 0F, 0F);
	    RotorSide2.setTextureSize(128, 128);
	    RotorSide2.mirror = true;
	    setRotation(RotorSide2, 0F, 0F, 0F);
	    RotorSide3 = new ModelRenderer(this, 0, 41);
	    RotorSide3.addBox(-7F, -12F, -8F, 14, 9, 1);
	    RotorSide3.setRotationPoint(0F, 0F, 0F);
	    RotorSide3.setTextureSize(128, 128);
	    RotorSide3.mirror = true;
	    setRotation(RotorSide3, 0F, 0F, 0F);
	    Base = new ModelRenderer(this, 0, 17);
	    Base.addBox(-8F, 0F, -8F, 16, 1, 16);
	    Base.setRotationPoint(0F, 0F, 0F);
	    Base.setTextureSize(128, 128);
	    Base.mirror = true;
	    setRotation(Base, 0F, 0F, 0F);
	    StaticPole = new ModelRenderer(this, 0, 33);
	    StaticPole.addBox(-2F, -2F, -2F, 4, 2, 4);
	    StaticPole.setRotationPoint(0F, 0F, 0F);
	    StaticPole.setTextureSize(128, 128);
	    StaticPole.mirror = true;
	    setRotation(StaticPole, 0F, 0F, 0F);
	    Blade1 = new ModelRenderer(this, 36, 51);
	    Blade1.addBox(-1F, -9F, -6F, 2, 1, 12);
	    Blade1.setRotationPoint(0F, 0F, 0F);
	    Blade1.setTextureSize(128, 128);
	    Blade1.mirror = true;
	    setRotation(Blade1, 0F, -0.7853982F, 0F);
	    MovingPole = new ModelRenderer(this, 47, 35);
	    MovingPole.addBox(-2F, -14F, -2F, 4, 11, 4);
	    MovingPole.setRotationPoint(0F, 0F, 0F);
	    MovingPole.setTextureSize(128, 128);
	    MovingPole.mirror = true;
	    setRotation(MovingPole, 0F, 0F, 0F);
	    Blade2 = new ModelRenderer(this, 36, 51);
	    Blade2.addBox(-1F, -9F, -6F, 2, 1, 12);
	    Blade2.setRotationPoint(0F, 0F, 0F);
	    Blade2.setTextureSize(128, 128);
	    Blade2.mirror = true;
	    setRotation(Blade2, 0F, 0F, 0F);
	    Blade3 = new ModelRenderer(this, 36, 51);
	    Blade3.addBox(-1F, -9F, -6F, 2, 1, 12);
	    Blade3.setRotationPoint(0F, 0F, 0F);
	    Blade3.setTextureSize(128, 128);
	    Blade3.mirror = true;
	    setRotation(Blade3, 0F, 1.570796F, 0F);
	    Blade4 = new ModelRenderer(this, 36, 51);
	    Blade4.addBox(-1F, -9F, -6F, 2, 1, 12);
	    Blade4.setRotationPoint(0F, 0F, 0F);
	    Blade4.setTextureSize(128, 128);
	    Blade4.mirror = true;
	    setRotation(Blade4, 0F, 0.7853982F, 0F);
	}
	
	public void render(TileTurbineGas tile, double x, double y, double z) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		ForgeDirection facing = tile.direction;
		if (facing == ForgeDirection.DOWN) {
			GL11.glTranslated(x + 0.5F, y + 0.89F, z + 0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
		} else if (facing == ForgeDirection.EAST) {
			GL11.glTranslated(x + 0.11F, y + 0.497F, z + 0.5F);
			GL11.glRotatef(-90, 0F, 0F, 1F);
		} else if (facing == ForgeDirection.NORTH) {
			GL11.glTranslated(x + 0.5F, y + 0.497F, z + 0.89F);
			GL11.glRotatef(-90, 1F, 0F, 0F);
		} else if (facing == ForgeDirection.WEST) {
			GL11.glTranslated(x + 0.89F, y + 0.497F, z + 0.5F);
			GL11.glRotatef(90, 0F, 0F, 1F);
		} else if (facing == ForgeDirection.SOUTH) {
			GL11.glTranslated(x + 0.5F, y + 0.497F, z + 0.11);
			GL11.glRotatef(90, 1F, 0F, 0F);
		} else if (facing == ForgeDirection.UP) {
			GL11.glTranslated(x + 0.5F, y + 0.1F, z + 0.5F);
			GL11.glRotatef(90, 0F, 1F, 0F);
		}

		GL11.glRotatef(180, 0F, 0F, 1F);
		GL11.glScalef(1.2F, 1.2F, 1.2F);

		Base.render(scale);
		StaticPole.render(scale);
		int xCoord = 16;
		int yCoord = 72;
		if (tile.energyStage == EnergyStage.GREEN) {
			xCoord = 0;
			yCoord = 72;
		} else if (tile.energyStage == EnergyStage.YELLOW) {
			xCoord = 47;
			yCoord = 39;
		} else if(tile.energyStage == EnergyStage.ORANGE) {
			xCoord = 48;
			yCoord = 72;
		} else if (tile.energyStage == EnergyStage.RED) {
			xCoord = 32;
			yCoord = 72;
		}

		MovingPole = new ModelRenderer(this, xCoord, yCoord);
		MovingPole = new ModelRenderer(this, xCoord, yCoord);
	    MovingPole.addBox(-2F, -14F, -2F, 4, 11, 4);
	    MovingPole.setRotationPoint(0F, 0F, 0F);
	    MovingPole.setTextureSize(128, 128);
	    MovingPole.mirror = true;
	    setRotation(MovingPole, 0F, 0F, 0F);

		float angle = (float) tile.getAngle();
		float angle_external = (float) tile.getExternalAngle();

		RotorBottom.rotateAngleY = angle_external;
		RotorSide1.rotateAngleY = angle_external;
		RotorSide2.rotateAngleY = angle_external;
		RotorSide3.rotateAngleY = angle_external;
		RotorSide4.rotateAngleY = angle_external;
		MovingPole.rotateAngleY = angle;
		Blade1.rotateAngleY = angle;
		Blade2.rotateAngleY = angle + 45;
		Blade3.rotateAngleY = angle + 90;
		Blade4.rotateAngleY = angle + 135;

		RotorBottom.render(scale);
		RotorSide1.render(scale);
		RotorSide2.render(scale);
		RotorSide3.render(scale);
		RotorSide4.render(scale);
		MovingPole.render(scale);
		Blade1.render(scale);
		Blade2.render(scale);
		Blade3.render(scale);
		Blade4.render(scale);

		Top.render(scale);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void renderInventory(final ItemRenderType type) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		switch (type) {
		case INVENTORY:
			GL11.glScalef(16F, 16F, 16F);
			GL11.glTranslatef(0.5F, 0.86F, 0F);
			break;
		case ENTITY:
			GL11.glRotatef(180, 0, 0, 1);
			break;
		default:
			GL11.glRotatef(35, 1, 0, 0);
			GL11.glRotatef(45, 0, -5, 0);
			GL11.glRotatef(180, 1, 0, 0);
			break;
		}
		Base.render(scale);
		StaticPole.render(scale);
		RotorBottom.render(scale);
		RotorSide1.render(scale);
		RotorSide2.render(scale);
		RotorSide3.render(scale);
		RotorSide4.render(scale);
		MovingPole.render(scale);
		Blade1.render(scale);
		Blade2.render(scale);
		Blade3.render(scale);
		Blade4.render(scale);
		Top.render(scale);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}

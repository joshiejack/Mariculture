package mariculture.diving.render;

import mariculture.core.lib.PowerStages;
import mariculture.diving.TileAirCompressor;
import mariculture.diving.TileAirCompressorPower;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelAirCompressorPower extends ModelBase {
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

	public ModelAirCompressorPower(final float scale) {

		this.scale = scale;
		textureWidth = 64;
		textureHeight = 64;

		baseEdge2 = new ModelRenderer(this, 27, 46);
		baseEdge2.addBox(-4F, 10F, -7F, 1, 2, 15);
		baseEdge2.setRotationPoint(0F, 0F, 0F);
		baseEdge2.setTextureSize(64, 64);
		baseEdge2.mirror = true;
		monitorEMPTY = new ModelRenderer(this, 50, 0);
		monitorEMPTY.addBox(-2F, 6F, 12F, 4, 3, 3);
		monitorEMPTY.setRotationPoint(0F, 0F, 0F);
		monitorEMPTY.setTextureSize(64, 64);
		monitorEMPTY.mirror = true;
		baseEdge1 = new ModelRenderer(this, 27, 46);
		baseEdge1.addBox(3F, 10F, -7F, 1, 2, 15);
		baseEdge1.setRotationPoint(0F, 0F, 0F);
		baseEdge1.setTextureSize(64, 64);
		baseEdge1.mirror = true;
		motor1Fan = new ModelRenderer(this, 40, 0);
		motor1Fan.addBox(-2F, 0F, 0F, 4, 3, 1);
		motor1Fan.setRotationPoint(0F, -1F, 0F);
		motor1Fan.setTextureSize(64, 64);
		motor1Fan.mirror = true;
		motor2Top = new ModelRenderer(this, 0, 22);
		motor2Top.addBox(-2F, 4F, 2F, 4, 2, 3);
		motor2Top.setRotationPoint(0F, 0F, 0F);
		motor2Top.setTextureSize(64, 64);
		motor2Top.mirror = true;
		motor1Base = new ModelRenderer(this, 21, 37);
		motor1Base.addBox(-2F, 4F, -5F, 4, 6, 5);
		motor1Base.setRotationPoint(0F, 0F, 0F);
		motor1Base.setTextureSize(64, 64);
		motor1Base.mirror = true;
		motor1Mid = new ModelRenderer(this, 23, 28);
		motor1Mid.addBox(-1F, 1F, -4F, 2, 3, 3);
		motor1Mid.setRotationPoint(0F, 0F, 0F);
		motor1Mid.setTextureSize(64, 64);
		motor1Mid.mirror = true;
		motor1Top = new ModelRenderer(this, 22, 21);
		motor1Top.addBox(-2F, 0F, -5F, 4, 1, 5);
		motor1Top.setRotationPoint(0F, 0F, 0F);
		motor1Top.setTextureSize(64, 64);
		motor1Top.mirror = true;
		fanClip = new ModelRenderer(this, 40, 13);
		fanClip.addBox(-3F, 3F, -3F, 2, 1, 1);
		fanClip.setRotationPoint(0F, 0F, 0F);
		fanClip.setTextureSize(64, 64);
		fanClip.mirror = true;
		monitorStand = new ModelRenderer(this, 58, 6);
		monitorStand.addBox(-1F, 9F, 13F, 2, 3, 1);
		monitorStand.setRotationPoint(0F, 0F, 0F);
		monitorStand.setTextureSize(64, 64);
		monitorStand.mirror = true;
		base = new ModelRenderer(this, 0, 48);
		base.addBox(-3F, 10F, -7F, 6, 1, 15);
		base.setRotationPoint(0F, 0F, 0F);
		base.setTextureSize(64, 64);
		base.mirror = true;
		motor2Bottom = new ModelRenderer(this, 0, 27);
		motor2Bottom.addBox(-3F, 6F, 1F, 6, 4, 5);
		motor2Bottom.setRotationPoint(0F, 0F, 0F);
		motor2Bottom.setTextureSize(64, 64);
		motor2Bottom.mirror = true;
		clipFront = new ModelRenderer(this, 32, 0);
		clipFront.addBox(-1F, 11F, -14F, 2, 2, 1);
		clipFront.setRotationPoint(0F, -1F, 0F);
		clipFront.setTextureSize(64, 64);
		clipFront.mirror = true;
		fan = new ModelRenderer(this, 40, 21);
		fan.addBox(-2F, -5.5F, -5.5F, 1, 11, 11);
		fan.setRotationPoint(-2F, 3F, -2.5F);
		fan.setTextureSize(64, 64);
		fan.mirror = true;

		monitorRED = new ModelRenderer(this, 15, 0);
		monitorRED.addBox(-2F, 6F, 12F, 4, 3, 3);
		monitorRED.setRotationPoint(0F, 0F, 0F);
		monitorRED.setTextureSize(64, 64);
		monitorRED.mirror = true;

		monitorORANGE = new ModelRenderer(this, 27, 4);
		monitorORANGE.addBox(-2F, 6F, 12F, 4, 3, 3);
		monitorORANGE.setRotationPoint(0F, 0F, 0F);
		monitorORANGE.setTextureSize(64, 64);
		monitorORANGE.mirror = true;

		monitorYELLOW = new ModelRenderer(this, 25, 11);
		monitorYELLOW.addBox(-2F, 6F, 12F, 4, 3, 3);
		monitorYELLOW.setRotationPoint(0F, 0F, 0F);
		monitorYELLOW.setTextureSize(64, 64);
		monitorYELLOW.mirror = true;

		monitorGREEN = new ModelRenderer(this, 36, 18);
		monitorGREEN.addBox(-2F, 6F, 12F, 4, 3, 3);
		monitorGREEN.setRotationPoint(0F, 0F, 0F);
		monitorGREEN.setTextureSize(64, 64);
		monitorGREEN.mirror = true;
	}

	private void faceDirection(final ForgeDirection direction, final double x, final double y, final double z) {
		if (direction == ForgeDirection.WEST) {
			GL11.glRotatef(180, 0F, 0F, 1F);
		} else if (direction == ForgeDirection.SOUTH) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(270, 0F, 1F, 0F);
		}
	}

	public void render(final TileAirCompressorPower tile, final double x, final double y, final double z) {
		final World world = tile.worldObj;

		boolean renderSouth = false;
		boolean renderWest = false;
		boolean renderNull = false;
		boolean renderSingle = false;

		if (world.getBlockTileEntity(tile.xCoord + 1, tile.yCoord, tile.zCoord) instanceof TileAirCompressorPower) {
			if (world.getBlockTileEntity(tile.xCoord + 1, tile.yCoord - 1, tile.zCoord) instanceof TileAirCompressor) {
				renderSouth = true;
			}
		} else if (world.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord + 1) instanceof TileAirCompressorPower) {
			if (world.getBlockTileEntity(tile.xCoord, tile.yCoord - 1, tile.zCoord + 1) instanceof TileAirCompressor) {
				renderWest = true;
			}
		} else {
			if ((world.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord + 1) instanceof TileAirCompressorPower)
					|| world.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord - 1) instanceof TileAirCompressorPower
					|| world.getBlockTileEntity(tile.xCoord + 1, tile.yCoord, tile.zCoord) instanceof TileAirCompressorPower
					|| world.getBlockTileEntity(tile.xCoord - 1, tile.yCoord, tile.zCoord) instanceof TileAirCompressorPower) {
				renderNull = true;
			}
		}

		if ((!(world.getBlockTileEntity(tile.xCoord + 1, tile.yCoord, tile.zCoord) instanceof TileAirCompressorPower)
				&& !(world.getBlockTileEntity(tile.xCoord - 1, tile.yCoord, tile.zCoord) instanceof TileAirCompressorPower)
				&& !(world.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord + 1) instanceof TileAirCompressorPower) && !(world
					.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord - 1) instanceof TileAirCompressorPower))
				|| !(world.getBlockTileEntity(tile.xCoord, tile.yCoord - 1, tile.zCoord) instanceof TileAirCompressor)) {
			renderSingle = true;
		}

		if ((renderWest || renderSouth || renderSingle) && !renderNull) {
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);

			if (renderSouth) {
				GL11.glTranslated(x + 1F, y + 0.7F, z + 0.5F);
				faceDirection(ForgeDirection.SOUTH, x, y, z);
			} else if (renderWest) {
				GL11.glTranslated(x + 0.5F, y + 0.7F, z + 1F);
				faceDirection(ForgeDirection.WEST, x, y, z);
			} else if (renderSingle) {
				GL11.glTranslated(x + 0.5F, y + 0.7F, z + 0.5F);
				faceDirection(ForgeDirection.WEST, x, y, z);
			} else {
				GL11.glTranslated(x + 0.5F, y + 0.7F, z + 0.5F);
				faceDirection(ForgeDirection.WEST, x, y, z);
			}

			baseEdge2.render(scale);

			final TileAirCompressorPower master = (TileAirCompressorPower) tile.getMasterBlock();
			if (master != null) {
				switch (master.getStage()) {
				case PowerStages.RED:
					monitorRED.render(scale);
					break;
				case PowerStages.ORANGE:
					monitorORANGE.render(scale);
					break;
				case PowerStages.YELLOW:
					monitorYELLOW.render(scale);
					break;
				case PowerStages.GREEN:
					monitorGREEN.render(scale);
					break;
				default:
					monitorEMPTY.render(scale);
				}
			}

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

			final float fanAngle = (float) tile.getWheelAngle();

			fan.rotateAngleX = fanAngle;

			fan.render(scale);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	public void renderInventory(final ItemRenderType type) {
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

		baseEdge2.render(scale);
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
		fan.render(scale);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}

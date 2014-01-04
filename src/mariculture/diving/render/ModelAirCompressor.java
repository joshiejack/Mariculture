package mariculture.diving.render;

import java.util.HashMap;

import mariculture.core.blocks.base.TileMultiBlock.MultiPart;
import mariculture.core.lib.DoubleMeta;
import mariculture.diving.Diving;
import mariculture.diving.TileAirCompressor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelAirCompressor extends ModelBase {
	/** AIR COMPRESSOR BASE **/
	private static final ResourceLocation COMPRESSOR_BAR = new ResourceLocation("mariculture", "textures/blocks/compressor_bar.png");
	private ModelRenderer head;
	private ModelRenderer leg;
	private ModelRenderer leg2;
	private ModelRenderer body;
	private static ModelRenderer bar7;
	private static ModelRenderer bar6;
	private static ModelRenderer bar5;
	private static ModelRenderer bar4;
	private static ModelRenderer bar3;
	private static ModelRenderer bar2;
	private static ModelRenderer bar1;
	private static ModelRenderer bar0;
	/** AIR COMPRESSOR BASE END , TOP BEGIN **/
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
	/** END AIR COMPRESSOR TOP **/
	
	
	protected final float scale = (float) (1.0 / 16.0);
	public ModelAirCompressor() {		
		textureWidth = 64;
		textureHeight = 64;
		
		/** AIR COMPRESSOR BASE RENDERER STUFFS **/
		head = new ModelRenderer(this, 20, 0);
		head.addBox(-6F, -3F, -9F, 12, 10, 1);
		head.setRotationPoint(0F, 0F, 0F);
		head.setTextureSize(64, 64);
		head.mirror = true;
		leg = new ModelRenderer(this, 0, 0);
		leg.addBox(-4F, 8F, -5F, 8, 4, 2);
		leg.setRotationPoint(0F, 0F, 0F);
		leg.setTextureSize(64, 64);
		leg.mirror = true;
		leg2 = new ModelRenderer(this, 0, 0);
		leg2.addBox(-4F, 8F, 4F, 8, 4, 2);
		leg2.setRotationPoint(0F, 0F, 0F);
		leg2.setTextureSize(64, 64);
		leg2.mirror = true;
		body = new ModelRenderer(this, 0, 11);
		body.addBox(-7F, -4F, -8F, 14, 12, 16);
		body.setRotationPoint(0F, 0F, 0F);
		body.setTextureSize(64, 64);
		body.mirror = true;
		bar7 = new ModelRenderer(this, 0, 0);
		bar7.addBox(-8F, -2F, -6F, 1, 8, 28);
		bar7.setRotationPoint(0F, 0F, 0F);
		bar7.setTextureSize(64, 64);
		bar7.mirror = true;
		bar6 = new ModelRenderer(this, 0, 8);
		bar6.addBox(-8F, -2F, -6F, 1, 8, 28);
		bar6.setRotationPoint(0F, 0F, 0F);
		bar6.setTextureSize(64, 64);
		bar6.mirror = true;
		bar5 = new ModelRenderer(this, 0, 16);
		bar5.addBox(-8F, -2F, -6F, 1, 8, 28);
		bar5.setRotationPoint(0F, 0F, 0F);
		bar5.setTextureSize(64, 64);
		bar5.mirror = true;
		bar4 = new ModelRenderer(this, 0, 24);
		bar4.addBox(-8F, -2F, -6F, 1, 8, 28);
		bar4.setRotationPoint(0F, 0F, 0F);
		bar4.setTextureSize(64, 64);
		bar4.mirror = true;
		bar3 = new ModelRenderer(this, 0, 0);
		bar3.addBox(7F, -2F, -6F, 1, 8, 28);
		bar3.setRotationPoint(0F, 0F, 0F);
		bar3.setTextureSize(64, 64);
		bar3.mirror = true;
		bar2 = new ModelRenderer(this, 0, 8);
		bar2.addBox(7F, -2F, -6F, 1, 8, 28);
		bar2.setRotationPoint(0F, 0F, 0F);
		bar2.setTextureSize(64, 64);
		bar2.mirror = true;
		bar1 = new ModelRenderer(this, 0, 16);
		bar1.addBox(7F, -2F, -6F, 1, 8, 28);
		bar1.setRotationPoint(0F, 0F, 0F);
		bar1.setTextureSize(64, 64);
		bar1.mirror = true;
		bar0 = new ModelRenderer(this, 0, 24);
		bar0.addBox(7F, -2F, -6F, 1, 8, 28);
		bar0.setRotationPoint(0F, 0F, 0F);
		bar0.setTextureSize(64, 64);
		bar0.mirror = true;
		/** END BASE; BEGIN TOP **/
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
		/** END TOP **/
	}

	/** Base Facing Talk **/
	public void faceBase(ForgeDirection direction, double x, double y, double z) {
		if (direction == ForgeDirection.WEST) {
			GL11.glRotatef(180, 0F, 0F, 1F);
		} else if (direction == ForgeDirection.EAST) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(180, 0F, 1F, 0F);

		} else if (direction == ForgeDirection.SOUTH) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(270, 0F, 1F, 0F);
		} else if (direction == ForgeDirection.NORTH) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(90, 0F, 1F, 0F);
		}
	}
	
	/** Top Facing Talk **/
	public void faceTop(ForgeDirection direction, double x, double y, double z) {
		if (direction == ForgeDirection.WEST) {
			GL11.glRotatef(180, 0F, 0F, 1F);
		} else if (direction == ForgeDirection.SOUTH) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(270, 0F, 1F, 0F);
		}
	}


	public void render(TileAirCompressor tile, double x, double y, double z) {
		if(tile.getBlockMetadata() == DoubleMeta.COMPRESSOR_BASE)
			renderBase(tile, x, y, z);
		if(tile.getBlockMetadata() == DoubleMeta.COMPRESSOR_TOP)
			renderTop(tile, x, y, z);
	}

	private void renderBase(TileAirCompressor tile, double x, double y, double z) {
		String key = tile.xCoord + " ~ " + tile.yCoord + " ~ " + tile.zCoord;		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslated(x + 0.5F, y + 0.7F, z + 0.5F);

		World world = tile.worldObj;
		boolean foundSecondHalf = false;

		if(tile.master != null) {
			if(!Diving.facingList.containsKey(key)) {
				TileAirCompressor master = (TileAirCompressor) world.getBlockTileEntity(tile.master.xCoord, tile.master.yCoord, tile.master.zCoord);
				for(MultiPart part: master.getSlaves()) {
					if(tile.xCoord == part.xCoord && tile.yCoord == tile.yCoord && tile.zCoord == tile.zCoord) {
						
						faceBase(part.facing, x, y, z);
						Diving.facingList.put(key, part.facing);
						
						foundSecondHalf = true;
						break;
					}
				}
				
				if(tile.isMaster()) {
					faceBase(tile.master.facing, x, y, z);
					Diving.facingList.put(key, tile.master.facing);
					foundSecondHalf = true;
				}
			} else {
				faceBase((ForgeDirection)Diving.facingList.get(key), x, y, z);
				foundSecondHalf = true;
			}
		}

		if (!foundSecondHalf) {
			GL11.glRotatef(180, 0F, 0F, 1F);
		}

		if (foundSecondHalf) {
			head.render(scale);
			leg.render(scale);
			body.render(scale);
		} else {
			leg.render(scale);
			leg2.render(scale);
			body.render(scale);
		}

		Minecraft.getMinecraft().getTextureManager().bindTexture(COMPRESSOR_BAR);

		if (tile instanceof TileAirCompressor && tile.master != null) {
			TileAirCompressor real = (TileAirCompressor) tile.worldObj
														 	.getBlockTileEntity(tile.master.xCoord, tile.master.yCoord, tile.master.zCoord);

			if (real != null) {

			}
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
	
	private void renderTop(TileAirCompressor tile, double x, double y, double z) {
		World world = tile.worldObj;
		String key = tile.xCoord + " ~ " + tile.yCoord + " ~ " + tile.zCoord;
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslated(x + 1F, y + 0.7F, z + 0.5F);
		
		if(tile.master != null) {
			if(!Diving.facingList.containsKey(key)) {
				TileAirCompressor master = (TileAirCompressor) world.getBlockTileEntity(tile.master.xCoord, tile.master.yCoord, tile.master.zCoord);
				for(MultiPart part: master.getSlaves()) {
					if(tile.xCoord == part.xCoord && tile.yCoord == tile.yCoord && tile.zCoord == tile.zCoord) {
						
						faceTop(part.facing, x, y, z);
						Diving.facingList.put(key, part.facing);
						break;
					}
				}
				
				if(tile.isMaster()) {
					faceTop(tile.master.facing, x, y, z);
					Diving.facingList.put(key, tile.master.facing);
				}
			} else {
				faceTop((ForgeDirection)Diving.facingList.get(key), x, y, z);
			}
		}

		baseEdge2.render(scale);

			/*TileAirCompressorPower master = (TileAirCompressorPower) tile;
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
			} */

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

		float fanAngle = (float) tile.getWheelAngle();
		fan.rotateAngleX = fanAngle;
		fan.render(scale);
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
			GL11.glScalef(10F, 10F, 10F);
			GL11.glTranslatef(0F, 0.2F, -1.2F);
			break;
		default:
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(90, 0F, 1F, 0F);
			// GL11.glTranslatef(0.15F, -0.3F, 0.1F);
			break;
		}

		head.render(scale);
		body.render(scale);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}

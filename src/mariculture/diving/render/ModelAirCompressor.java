package mariculture.diving.render;

import mariculture.core.blocks.base.TileMulti;
import mariculture.core.render.RenderDouble;
import mariculture.diving.TileAirCompressor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class ModelAirCompressor extends ModelBase {
	private static final ResourceLocation COMPRESSOR_BAR = new ResourceLocation("mariculture",
			"textures/blocks/compressor_bar.png");

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
	private final float scale;

	public ModelAirCompressor(final float scale) {

		this.scale = scale;
		textureWidth = 64;
		textureHeight = 64;

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
	}

	private void faceDirection(final ForgeDirection direction, final double x, final double y, final double z) {
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

	public void render(TileMulti tile, double x, double y, double z, RenderDouble render) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTranslated(x + 0.5F, y + 0.7F, z + 0.5F);

		final World world = tile.worldObj;
		boolean foundSecondHalf = false;

		/*
		if (world.getBlockTileEntity(tile.xCoord + 1, tile.yCoord, tile.zCoord) instanceof TileDoubleBlock) {
			faceDirection(ForgeDirection.SOUTH, x, y, z);
			foundSecondHalf = true;
		}

		if (world.getBlockTileEntity(tile.xCoord - 1, tile.yCoord, tile.zCoord) instanceof TileDoubleBlock) {
			faceDirection(ForgeDirection.NORTH, x, y, z);
			foundSecondHalf = true;
		}

		if (world.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord + 1) instanceof TileDoubleBlock) {
			faceDirection(ForgeDirection.WEST, x, y, z);
			foundSecondHalf = true;
		}

		if (world.getBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord - 1) instanceof TileDoubleBlock) {
			faceDirection(ForgeDirection.EAST, x, y, z);
			foundSecondHalf = true;
		} */

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

		if (tile instanceof TileAirCompressor) {
			TileAirCompressor real = (TileAirCompressor) tile.worldObj.getBlockTileEntity(tile.mstr.x, tile.mstr.y, tile.mstr.z);

			if (real != null) {
				switch (real.getBarLength()) {
				case 7:
					bar7.render(scale);
					break;
				case 6:
					bar6.render(scale);
					break;
				case 5:
					bar5.render(scale);
					break;
				case 4:
					bar4.render(scale);
					break;
				case 3:
					bar3.render(scale);
					break;
				case 2:
					bar2.render(scale);
					break;
				case 1:
					bar1.render(scale);
					break;
				case 0:
					bar0.render(scale);
					break;
				}
			}
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	public void renderInventory(final ItemRenderType type) {
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

	private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}

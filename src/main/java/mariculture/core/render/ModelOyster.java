package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.TileOyster;
import mariculture.core.lib.PearlColor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

public class ModelOyster extends ModelBase {
	private static final ResourceLocation SAND = new ResourceLocation("mariculture", "textures/blocks/oyster_sand.png");

	private ModelRenderer base1;
	private ModelRenderer base2;
	private ModelRenderer lid1;
	private ModelRenderer base3;
	private ModelRenderer base4;
	private ModelRenderer base5;
	private ModelRenderer lid2;
	private ModelRenderer lid3;
	private ModelRenderer lid4;
	private ModelRenderer lid5;
	private ModelRenderer lid6;
	private ModelRenderer base6;

	private ModelRenderer pearl;
	private final float scale;

	public ModelOyster(float scale) {
		this.scale = scale;
		textureWidth = 128;
		textureHeight = 64;

		base1 = new ModelRenderer(this, 0, 32);
		base1.addBox(-4F, 0F, -8F, 12, 1, 14);
		base1.setRotationPoint(2F, -1F, 0F);
		base1.setTextureSize(64, 64);
		base1.mirror = true;
		setRotation(base1, 0F, 0F, 3.141593F);
		base2 = new ModelRenderer(this, 0, 14);
		base2.addBox(-8F, 0F, -8F, 1, 2, 16);
		base2.setRotationPoint(15F, -4F, -1F);
		base2.setTextureSize(64, 64);
		base2.mirror = true;
		setRotation(base2, 0F, 0F, 0F);
		lid1 = new ModelRenderer(this, 34, 30);
		lid1.addBox(-8F, 0F, -16F, 14, 1, 1);
		lid1.setRotationPoint(1F, -2F, 7F);
		lid1.setTextureSize(64, 64);
		lid1.mirror = true;
		setRotation(lid1, -0.9666439F, 0F, 0F);
		base3 = new ModelRenderer(this, 34, 30);
		base3.addBox(-8F, 0F, -8F, 14, 1, 1);
		base3.setRotationPoint(1F, -3F, -1F);
		base3.setTextureSize(64, 64);
		base3.mirror = true;
		setRotation(base3, 0F, 0F, 0F);
		base4 = new ModelRenderer(this, 34, 30);
		base4.addBox(-8F, 0F, -8F, 14, 1, 1);
		base4.setRotationPoint(1F, -4F, -2F);
		base4.setTextureSize(64, 64);
		base4.mirror = true;
		setRotation(base4, 0F, 0F, 0F);
		base5 = new ModelRenderer(this, 0, 14);
		base5.addBox(-8F, 0F, -8F, 1, 2, 16);
		base5.setRotationPoint(0F, -4F, -1F);
		base5.setTextureSize(64, 64);
		base5.mirror = true;
		setRotation(base5, 0F, 0F, 0F);
		//
		lid2 = new ModelRenderer(this, 28, 19);
		lid2.addBox(-4F, -1F, -12F, 8, 1, 10);
		lid2.setRotationPoint(0F, -3F, 8F);
		lid2.setTextureSize(64, 64);
		lid2.mirror = true;
		setRotation(lid2, -0.9666439F, 0F, 0F);
		lid3 = new ModelRenderer(this, 0, 0);
		lid3.addBox(-9F, 0F, -15F, 1, 1, 13);
		lid3.setRotationPoint(1F, -2F, 7F);
		lid3.setTextureSize(64, 64);
		lid3.mirror = true;
		setRotation(lid3, -0.9666439F, 0F, 0F);
		lid4 = new ModelRenderer(this, 0, 0);
		lid4.addBox(7F, 0F, -15F, 1, 1, 13);
		lid4.setRotationPoint(0F, -2F, 7F);
		lid4.setTextureSize(64, 64);
		lid4.mirror = true;
		setRotation(lid4, -0.9666439F, 0F, 0F);
		lid5 = new ModelRenderer(this, 0, 47);
		lid5.addBox(-7F, 0F, -16F, 14, 1, 16);
		lid5.setRotationPoint(0F, -2F, 8F);
		lid5.setTextureSize(64, 64);
		lid5.mirror = true;
		setRotation(lid5, -0.9666439F, 0F, 0F);
		lid6 = new ModelRenderer(this, 0, 32);
		lid6.addBox(-6F, -1F, -15F, 12, 1, 14);
		lid6.setRotationPoint(0F, -2F, 8F);
		lid6.setTextureSize(64, 64);
		lid6.mirror = true;
		setRotation(lid6, -0.9666439F, 0F, 0F);
		base6 = new ModelRenderer(this, 1, 47);
		base6.addBox(-7F, 0F, -7F, 14, 1, 15);
		base6.setRotationPoint(0F, -2F, 0F);
		base6.setTextureSize(64, 64);
		base6.mirror = true;
		setRotation(base6, 3.141593F, 0F, 0F);
	}

	public void render(final TileOyster oyster, final double x, final double y, final double z) {
		int direction = oyster.getBlockMetadata();

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y - 0.05F, (float) z + 0.5F);
		if (direction == 0) {
			GL11.glRotatef(180, 0F, 0F, 1F);
		} else if (direction == 1) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(180, 0F, 1F, 0F);

		} else if (direction == 2) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(270, 0F, 1F, 0F);
		} else {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(90, 0F, 1F, 0F);
		}

		GL11.glPushMatrix();

		base1.render(scale);
		base2.render(scale);
		lid1.render(scale);
		base3.render(scale);
		base4.render(scale);
		base5.render(scale);
		lid2.render(scale);
		lid3.render(scale);
		lid4.render(scale);
		lid5.render(scale);
		lid6.render(scale);
		base6.render(scale);

		if (oyster.getCurrentPearl() != null) {
			if (oyster.getCurrentPearl().getItem() == Core.pearls) {
				int damage = oyster.getCurrentPearl().getItemDamage();

				if (damage > -1) {
					int posX = 0;
					int posY = 0;

					if (damage == PearlColor.WHITE) {
						posX = 0;
						posY = 22;
					}

					if (damage == PearlColor.BLACK) {
						posX = 0;
						posY = 14;
					}

					if (damage == PearlColor.ORANGE) {
						posX = 0;
						posY = 55;
					}

					if (damage == PearlColor.SILVER) {
						posX = 16;
						posY = 0;
					}

					if (damage == PearlColor.GREEN) {
						posX = 22;
						posY = 21;
					}

					if (damage == PearlColor.YELLOW) {
						posX = 0;
						posY = 47;
					}

					if (damage == PearlColor.RED) {
						posX = 44;
						posY = 47;
					}

					if (damage == PearlColor.GOLD) {
						posX = 44;
						posY = 55;
					}

					if (damage == PearlColor.BROWN) {
						posX = 32;
						posY = 8;
					}

					if (damage == PearlColor.PURPLE) {
						posX = 48;
						posY = 8;
					}

					if (damage == PearlColor.BLUE) {
						posX = 48;
						posY = 0;
					}

					if (damage == PearlColor.PINK) {
						posX = 32;
						posY = 0;
					}

					renderPearl(posX, posY);
				}
			} else {
				if(oyster.getCurrentPearl().getItem() == Items.ender_pearl) {
					renderPearl(68, 0);
				} else {
					GL11.glPushMatrix();
					GL11.glScalef(0.6F, 0.6F, 0.6F);
					GL11.glTranslatef(0F, -0.11F, 0F);
					renderPearl(44, 38);
					GL11.glPopMatrix();
				}
			}
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	public void renderPearl(int x, int y) {
		pearl = new ModelRenderer(this, x, y);
		pearl.addBox(-7F, 0F, -8F, 4, 4, 4);
		pearl.setRotationPoint(5F, -6F, 6F);
		pearl.setTextureSize(64, 64);
		pearl.mirror = true;
		setRotation(pearl, 0F, 0F, 0F);
		pearl.render(scale);
	}

	public void renderInventory(final ItemRenderType type) {
		GL11.glPushMatrix();

		switch (type) {
		case INVENTORY:
			GL11.glRotated(-40, 1, 0, 0);
			GL11.glRotatef(75, 0, 5, 0);
			GL11.glRotatef(-75, 0, -5, 0);
			GL11.glScalef(13F, 13F, 13F);
			GL11.glTranslatef(-1F, 0.3F, -0.5F);
			break;
		case ENTITY:
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0.15F, 0.15F, 0.2F);
			break;
		default:
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glRotatef(180, 0F, 1F, 0F);
			GL11.glTranslatef(0.25F, -0.1F, 0.1F);
			break;
		}

		GL11.glPushMatrix();

		base1.render(scale);
		base2.render(scale);
		lid1.render(scale);
		base3.render(scale);
		base4.render(scale);
		base5.render(scale);

		pearl = new ModelRenderer(this, 0, 22);
		pearl.addBox(-7F, 0F, -8F, 4, 4, 4);
		pearl.setRotationPoint(5F, -6F, 6F);
		pearl.setTextureSize(64, 64);
		pearl.mirror = true;
		setRotation(pearl, 0F, 0F, 0F);

		pearl.render(scale);
		lid2.render(scale);
		lid3.render(scale);
		lid4.render(scale);
		lid5.render(scale);
		lid6.render(scale);
		base6.render(scale);

		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}

	private void setRotation(final ModelRenderer model, final float x, final float y, final float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}

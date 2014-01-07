package mariculture.core.helpers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class RenderHelper {
	static final double RENDER_OFFSET = 0.0010000000474974513D;
	static final float LIGHT_Y_NEG = 0.5F;
	static final float LIGHT_Y_POS = 1.0F;
	static final float LIGHT_XZ_NEG = 0.8F;
	static final float LIGHT_XZ_POS = 0.6F;

	RenderBlocks render;
	IBlockAccess world;
	int x;
	int y;
	int z;

	public RenderHelper(RenderBlocks render, IBlockAccess world, int x, int y,
			int z) {
		this.world = world;
		this.render = render;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setTexture(Icon icon) {
		render.setOverrideBlockTexture(icon);
	}

	public void setTexture(Block block) {
		render.setOverrideBlockTexture(block.getIcon(0, 0));
	}

	public void setTexture(Block block, int meta) {
		render.setOverrideBlockTexture(block.getIcon(0, meta));
	}

	public void renderBlock(double x, double y, double z, double x2, double y2,
			double z2) {
		render.setRenderBounds(x, y, z, x2, y2, z2);
		render.renderStandardBlock(Block.stone, this.x, this.y, this.z);
	}

	public RenderBlocks getRenderer() {
		return this.render;
	}

	public void renderWorldBlock(FluidStack fluid, int max, double scale, int xPlus, int yPlus, int zPlus) {
		int x2 = x + xPlus;
		int y2 = y + yPlus;
		int z2 = z + zPlus;
		
		Tessellator tessellator = Tessellator.instance;
		int color = Block.stone.colorMultiplier(world, x2, y2, z2);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		
		double extra = (((double)fluid.amount)/max) * scale;
		double height = 0.4D + extra;
		Icon iconStill = fluid.getFluid().getIcon();

		height += RENDER_OFFSET;

		double u1, u2, u3, u4, v1, v2, v3, v4;
		u2 = iconStill.getInterpolatedU(0.0D);
		v2 = iconStill.getInterpolatedV(0.0D);
		u1 = u2;
		v1 = iconStill.getInterpolatedV(16.0D);
		u4 = iconStill.getInterpolatedU(16.0D);
		v4 = v1;
		u3 = u4;
		v3 = v2;

		tessellator.setBrightness(200);
		tessellator.setColorOpaque_F(LIGHT_Y_POS * red, LIGHT_Y_POS * green, LIGHT_Y_POS * blue);
		tessellator.addVertexWithUV(x2 + 0, y2 + height, z2 + 0, u2, v2);
		tessellator.addVertexWithUV(x2 + 0, y2 + height, z2 + 1, u1, v1);
		tessellator.addVertexWithUV(x2 + 1, y2 + height, z2 + 1, u4, v4);
		tessellator.addVertexWithUV(x2 + 1, y2 + height, z2 + 0, u3, v3);

		render.renderMinY = 0;
		render.renderMaxY = 1;
	}

	public void renderFluid(Fluid fluid, double yStart, double yEnd) {
		Icon icon = fluid.getIcon();
		Tessellator tessellator = Tessellator.instance;
		Block block = Block.stone;
		int color = block.colorMultiplier(world, x, y, z);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;

		boolean rendered = false;
		rendered = true;
		yEnd -= RENDER_OFFSET;

		double u1, u2, u3, u4, v1, v2, v3, v4;

		u2 = icon.getInterpolatedU(8.0F + (-0.5F) * 16.0F);
		v2 = icon.getInterpolatedV(8.0F + (-0.5F) * 16.0F);
		u1 = icon.getInterpolatedU(8.0F + (-0.5F) * 16.0F);
		v1 = icon.getInterpolatedV(8.0F + (0.5F) * 16.0F);
		u4 = icon.getInterpolatedU(8.0F + (0.5F) * 16.0F);
		v4 = icon.getInterpolatedV(8.0F + (0.5F) * 16.0F);
		u3 = icon.getInterpolatedU(8.0F + (0.5F) * 16.0F);
		v3 = icon.getInterpolatedV(8.0F + (-0.5F) * 16.0F);
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y,
				z));
		tessellator.setColorOpaque_F(LIGHT_Y_POS * red, LIGHT_Y_POS * green,
				LIGHT_Y_POS * blue);
		tessellator.addVertexWithUV(x + 0, y + yEnd, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 0, y + yEnd, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 1, y + yEnd, z + 1, u4, v4);
		tessellator.addVertexWithUV(x + 1, y + yEnd, z + 0, u3, v3);

		rendered = true;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x,
				y - 1, z));
		tessellator.setColorOpaque_F(LIGHT_Y_NEG * red, LIGHT_Y_NEG * green,
				LIGHT_Y_NEG * blue);
		render.renderFaceYNeg(block, x, y + RENDER_OFFSET, z, icon);

		for (int side = 0; side < 4; ++side) {
			int x2 = x;
			int z2 = z;

			switch (side) {
			case 0:
				--z2;
				break;
			case 1:
				++z2;
				break;
			case 2:
				--x2;
				break;
			case 3:
				++x2;
				break;
			}

			rendered = true;

			double ty1;
			double tx1;
			double ty2;
			double tx2;
			double tz1;
			double tz2;

			if (side == 0) {
				ty1 = yEnd;
				ty2 = yEnd;
				tx1 = x;
				tx2 = x + 1;
				tz1 = z + RENDER_OFFSET;
				tz2 = z + RENDER_OFFSET;
			} else if (side == 1) {
				ty1 = yEnd;
				ty2 = yEnd;
				tx1 = x + 1;
				tx2 = x;
				tz1 = z + 1 - RENDER_OFFSET;
				tz2 = z + 1 - RENDER_OFFSET;
			} else if (side == 2) {
				ty1 = yEnd;
				ty2 = yEnd;
				tx1 = x + RENDER_OFFSET;
				tx2 = x + RENDER_OFFSET;
				tz1 = z + 1;
				tz2 = z;
			} else {
				ty1 = yEnd;
				ty2 = yEnd;
				tx1 = x + 1 - RENDER_OFFSET;
				tx2 = x + 1 - RENDER_OFFSET;
				tz1 = z;
				tz2 = z + 1;
			}

			float u1Flow = icon.getInterpolatedU(0.0D);
			float u2Flow = icon.getInterpolatedU(16.0D);
			float v1Flow = icon.getInterpolatedV((1.0D - ty1) * 16.0D * 0.5D);
			float v2Flow = icon.getInterpolatedV((1.0D - ty2) * 16.0D * 0.5D);
			float v3Flow = icon.getInterpolatedV(8.0D);
			tessellator.setBrightness(block.getMixedBrightnessForBlock(world,
					x2, y, z2));
			float sideLighting = 1.0F;

			if (side < 2) {
				sideLighting = LIGHT_XZ_NEG;
			} else {
				sideLighting = LIGHT_XZ_POS;
			}

			tessellator.setColorOpaque_F(LIGHT_Y_POS * sideLighting * red,
					LIGHT_Y_POS * sideLighting * green, LIGHT_Y_POS
							* sideLighting * blue);

			tessellator.addVertexWithUV(tx1, y + ty1, tz1, u1Flow, v1Flow);
			tessellator.addVertexWithUV(tx2, y + ty2, tz2, u2Flow, v2Flow);
			tessellator.addVertexWithUV(tx2, y + yStart, tz2, u2Flow, v3Flow);
			tessellator.addVertexWithUV(tx1, y + yStart, tz1, u1Flow, v3Flow);

		}

		render.renderMinY = 0;
		render.renderMaxY = 1;
	}

	public static boolean renderFluid(IBlockAccess world, int x, int y, int z,
			double height, Icon still, Icon flowing, RenderBlocks renderer) {
		flowing = still;
		Tessellator tessellator = Tessellator.instance;
		Block block = Block.stone;
		int color = block.colorMultiplier(world, x, y, z);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;

		boolean rendered = false;
		rendered = true;
		height -= RENDER_OFFSET;

		double u1, u2, u3, u4, v1, v2, v3, v4;

		u2 = still.getInterpolatedU(8.0F + (-0.5F) * 16.0F);
		v2 = still.getInterpolatedV(8.0F + (-0.5F) * 16.0F);
		u1 = still.getInterpolatedU(8.0F + (-0.5F) * 16.0F);
		v1 = still.getInterpolatedV(8.0F + (0.5F) * 16.0F);
		u4 = still.getInterpolatedU(8.0F + (0.5F) * 16.0F);
		v4 = still.getInterpolatedV(8.0F + (0.5F) * 16.0F);
		u3 = still.getInterpolatedU(8.0F + (0.5F) * 16.0F);
		v3 = still.getInterpolatedV(8.0F + (-0.5F) * 16.0F);
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y,
				z));
		tessellator.setColorOpaque_F(LIGHT_Y_POS * red, LIGHT_Y_POS * green,
				LIGHT_Y_POS * blue);
		tessellator.addVertexWithUV(x + 0, y + height, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 0, y + height, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 1, y + height, z + 1, u4, v4);
		tessellator.addVertexWithUV(x + 1, y + height, z + 0, u3, v3);

		rendered = true;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x,
				y - 1, z));
		tessellator.setColorOpaque_F(LIGHT_Y_NEG * red, LIGHT_Y_NEG * green,
				LIGHT_Y_NEG * blue);
		renderer.renderFaceYNeg(block, x, y + RENDER_OFFSET, z, still);

		for (int side = 0; side < 4; ++side) {
			int x2 = x;
			int z2 = z;

			switch (side) {
			case 0:
				--z2;
				break;
			case 1:
				++z2;
				break;
			case 2:
				--x2;
				break;
			case 3:
				++x2;
				break;
			}

			rendered = true;

			double ty1;
			double tx1;
			double ty2;
			double tx2;
			double tz1;
			double tz2;

			if (side == 0) {
				ty1 = height;
				ty2 = height;
				tx1 = x;
				tx2 = x + 1;
				tz1 = z + RENDER_OFFSET;
				tz2 = z + RENDER_OFFSET;
			} else if (side == 1) {
				ty1 = height;
				ty2 = height;
				tx1 = x + 1;
				tx2 = x;
				tz1 = z + 1 - RENDER_OFFSET;
				tz2 = z + 1 - RENDER_OFFSET;
			} else if (side == 2) {
				ty1 = height;
				ty2 = height;
				tx1 = x + RENDER_OFFSET;
				tx2 = x + RENDER_OFFSET;
				tz1 = z + 1;
				tz2 = z;
			} else {
				ty1 = height;
				ty2 = height;
				tx1 = x + 1 - RENDER_OFFSET;
				tx2 = x + 1 - RENDER_OFFSET;
				tz1 = z;
				tz2 = z + 1;
			}

			float u1Flow = flowing.getInterpolatedU(0.0D);
			float u2Flow = flowing.getInterpolatedU(16.0D);
			float v1Flow = flowing
					.getInterpolatedV((1.0D - ty1) * 16.0D * 0.5D);
			float v2Flow = flowing
					.getInterpolatedV((1.0D - ty2) * 16.0D * 0.5D);
			float v3Flow = flowing.getInterpolatedV(8.0D);
			tessellator.setBrightness(block.getMixedBrightnessForBlock(world,
					x2, y, z2));
			float sideLighting = 1.0F;

			if (side < 2) {
				sideLighting = LIGHT_XZ_NEG;
			} else {
				sideLighting = LIGHT_XZ_POS;
			}

			tessellator.setColorOpaque_F(LIGHT_Y_POS * sideLighting * red,
					LIGHT_Y_POS * sideLighting * green, LIGHT_Y_POS
							* sideLighting * blue);

			tessellator.addVertexWithUV(tx1, y + ty1, tz1, u1Flow, v1Flow);
			tessellator.addVertexWithUV(tx2, y + ty2, tz2, u2Flow, v2Flow);
			tessellator.addVertexWithUV(tx2, y + 0, tz2, u2Flow, v3Flow);
			tessellator.addVertexWithUV(tx1, y + 0, tz1, u1Flow, v3Flow);

		}
		renderer.renderMinY = 0;
		renderer.renderMaxY = 1;
		return rendered;
	}

	public static void renderInvBlock(RenderBlocks render, Block block, int meta) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		render.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		render.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		render.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		render.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		render.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		render.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}

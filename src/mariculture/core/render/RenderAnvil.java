package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.helpers.RenderHelper;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

public class RenderAnvil {
	public RenderBlocks renderer;
	public RenderHelper helper;

	public RenderAnvil(RenderHelper helper) {
		this.helper = helper;
		this.renderer = helper.getRenderer();
	}

	public boolean renderBlockAnvilMetadata(int par2, int par3, int par4, int par5) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(Block.anvil.getMixedBrightnessForBlock(helper.getWorld(), par2, par3, par4));
		float f = 1.0F;
		int i1 = Block.anvil.colorMultiplier(helper.getWorld(), par2, par3, par4);
		float f1 = (float) (i1 >> 16 & 255) / 255.0F;
		float f2 = (float) (i1 >> 8 & 255) / 255.0F;
		float f3 = (float) (i1 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable) {
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}

		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
		return renderBlockAnvilOrient(par2, par3, par4, par5, false);
	}

	public boolean renderBlockAnvilOrient(int par2, int par3, int par4, int par5, boolean par6) {
		int i1 = par6 ? 0 : par5 & 3;
		boolean flag1 = false;
		float f = 0.0F;

		switch (i1) {
		case 0:
			renderer.uvRotateSouth = 2;
			renderer.uvRotateNorth = 1;
			renderer.uvRotateTop = 3;
			renderer.uvRotateBottom = 3;
			break;
		case 1:
			renderer.uvRotateEast = 1;
			renderer.uvRotateWest = 2;
			renderer.uvRotateTop = 2;
			renderer.uvRotateBottom = 1;
			flag1 = true;
			break;
		case 2:
			renderer.uvRotateSouth = 1;
			renderer.uvRotateNorth = 2;
			break;
		case 3:
			renderer.uvRotateEast = 2;
			renderer.uvRotateWest = 1;
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 2;
			flag1 = true;
		}

		helper.setTexture(Core.oreBlocks, OresMeta.BASE_BRICK);
		f = renderer.renderBlockAnvilRotate((BlockAnvil) Block.anvil, par2, par3, par4, 0, f, 0.75F, 0.25F, 0.75F, flag1, par6, par5);
		helper.setTexture(Block.netherBrick);
		f = renderer.renderBlockAnvilRotate((BlockAnvil) Block.anvil, par2, par3, par4, 1, f, 0.5F, 0.0625F, 0.625F, flag1, par6, par5);
		helper.setTexture(Core.oreBlocks, OresMeta.BASE_BRICK);
		f = renderer.renderBlockAnvilRotate((BlockAnvil) Block.anvil, par2, par3, par4, 2, f, 0.25F, 0.3125F, 0.5F, flag1, par6, par5);
		helper.setTexture(Core.doubleBlock, DoubleMeta.VAT);
		renderer.renderBlockAnvilRotate((BlockAnvil) Block.anvil, par2, par3, par4, 3, f, 0.625F, 0.375F, 1.0F, flag1, par6, par5);
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		return true;
	}
}

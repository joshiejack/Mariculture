package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.lib.TankMeta;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class RenderVoidBottle extends RenderBase {
	public RenderVoidBottle(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {		
		Tessellator tessellator = Tessellator.instance;
		int color = Blocks.stone.colorMultiplier(world, x, y, z);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		
		IIcon iconStill = Core.tankBlocks.getIcon(0, TankMeta.BOTTLE);

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
		//Base
		tessellator.addVertexWithUV(x + 0, y, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 0, y, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 1, y, z + 1, u4, v4);
		tessellator.addVertexWithUV(x + 1, y, z + 0, u3, v3);
		
		//Edge 1
		tessellator.addVertexWithUV(x + 0, y, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 0, y, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 0, y + 0.3, z + 1, u4, v4);
		tessellator.addVertexWithUV(x + 0, y + 0.3, z + 0, u3, v3);
		//Edge 1 to Centre
		tessellator.addVertexWithUV(x + 0, y + 0.3, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 0, y + 0.3, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 0.3, y + 0.85, z + 0.7, u4, v4);
		tessellator.addVertexWithUV(x + 0.3, y + 0.85, z + 0.3, u3, v3);
		//Edge 1 Bottle Lid
		tessellator.addVertexWithUV(x + 0.3, y + 0.85, z + 0.3, u2, v2);
		tessellator.addVertexWithUV(x + 0.3, y + 0.85, z + 0.7, u1, v1);
		tessellator.addVertexWithUV(x + 0.3, y + 1, z + 0.7, u4, v4);
		tessellator.addVertexWithUV(x + 0.3, y + 1, z + 0.3, u3, v3);
		
		//Edge 2
		tessellator.addVertexWithUV(x + 0, y, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 1, y, z + 0, u1, v1);
		tessellator.addVertexWithUV(x + 1, y + 0.3, z + 0, u4, v4);
		tessellator.addVertexWithUV(x + 0, y + 0.3, z + 0, u3, v3);
		//Edge 2 to Centre
		tessellator.addVertexWithUV(x + 0, y + 0.3, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 1, y + 0.3, z + 0, u1, v1);
		tessellator.addVertexWithUV(x + 0.7, y + 0.85, z + 0.3, u4, v4);
		tessellator.addVertexWithUV(x + 0.3, y + 0.85, z + 0.3, u3, v3);
		//Edge 2 Bottle Lid
		tessellator.addVertexWithUV(x + 0.3, y + 0.85, z + 0.3, u2, v2);
		tessellator.addVertexWithUV(x + 0.7, y + 0.85, z + 0.3, u1, v1);
		tessellator.addVertexWithUV(x + 0.7, y + 1, z + 0.3, u4, v4);
		tessellator.addVertexWithUV(x + 0.3, y + 1, z + 0.3, u3, v3);
		
		//Edge 3
		tessellator.addVertexWithUV(x + 1, y, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 1, y, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 1, y + 0.3, z + 1, u4, v4);
		tessellator.addVertexWithUV(x + 1, y + 0.3, z + 0, u3, v3);
		//Edge 3 to Centre
		tessellator.addVertexWithUV(x + 1, y + 0.3, z + 0, u2, v2);
		tessellator.addVertexWithUV(x + 1, y + 0.3, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 0.7, y + 0.85, z + 0.7, u4, v4);
		tessellator.addVertexWithUV(x + 0.7, y + 0.85, z + 0.3, u3, v3);
		//Edge 3 Bottle Lid
		tessellator.addVertexWithUV(x + 0.7, y + 0.85, z + 0.3, u2, v2);
		tessellator.addVertexWithUV(x + 0.7, y + 0.85, z + 0.7, u1, v1);
		tessellator.addVertexWithUV(x + 0.7, y + 1, z + 0.7, u4, v4);
		tessellator.addVertexWithUV(x + 0.7, y + 1, z + 0.3, u3, v3);
		
		//Edge 4
		tessellator.addVertexWithUV(x + 0, y, z + 1, u2, v2);
		tessellator.addVertexWithUV(x + 1, y, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 1, y + 0.3, z + 1, u4, v4);
		tessellator.addVertexWithUV(x + 0, y + 0.3, z + 1, u3, v3);
		//Edge 4 to Centre
		tessellator.addVertexWithUV(x + 0, y + 0.3, z + 1, u2, v2);
		tessellator.addVertexWithUV(x + 1, y + 0.3, z + 1, u1, v1);
		tessellator.addVertexWithUV(x + 0.7, y + 0.85, z + 0.7, u4, v4);
		tessellator.addVertexWithUV(x + 0.3, y + 0.85, z + 0.7, u3, v3);
		//Edge 4 Bottle Lid
		tessellator.addVertexWithUV(x + 0.3, y + 0.85, z + 0.7, u2, v2);
		tessellator.addVertexWithUV(x + 0.7, y + 0.85, z + 0.7, u1, v1);
		tessellator.addVertexWithUV(x + 0.7, y + 1, z + 0.7, u4, v4);
		tessellator.addVertexWithUV(x + 0.3, y + 1, z + 0.7, u3, v3);

		render.renderMinY = 0;
		render.renderMaxY = 1;
	}
}

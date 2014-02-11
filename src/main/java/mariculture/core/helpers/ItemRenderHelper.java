package mariculture.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class ItemRenderHelper {
	public Block block = Blocks.stone;
	public RenderBlocks render;
	public IIcon icon;
	public int[] uv;
	
	public ItemRenderHelper(RenderBlocks render) {
		this.render = render;
	}
	
	public RenderBlocks getRenderer() {
		return render;
	}
	
	public void end() {
		render.uvRotateEast = uv[0];
        render.uvRotateWest = uv[1];
        render.uvRotateSouth = uv[2];
        render.uvRotateNorth = uv[3];
        render.uvRotateTop = uv[4];
        render.uvRotateBottom = uv[5];
	}

	public void start() {
		uv = new int[] {
			render.uvRotateEast,  render.uvRotateWest, render.uvRotateSouth,
			render.uvRotateNorth, render.uvRotateTop, render.uvRotateBottom
		};
		
		render.uvRotateEast = 0;
        render.uvRotateWest = 0;
        render.uvRotateSouth = 0;
        render.uvRotateNorth = 0;
        render.uvRotateTop = 0;
        render.uvRotateBottom = 0;
	}
	
	public void setTexture(Block block) {
		icon = block.getIcon(0, 0);
	}
	
	public void setTexture(Block block, int meta) {
		icon = block.getIcon(0, meta);
	}
	
	public void renderAsItem(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		render.renderMinX = minX;
	    render.renderMinY = minY;
	    render.renderMinZ = minZ;
	    render.renderMaxX = maxX;
	    render.renderMaxY = maxY;
	    render.renderMaxZ = maxZ;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, -1.0F, 0.0F);
	    render.renderFaceYNeg(Blocks.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 1.0F, 0.0F);
	    render.renderFaceYPos(Blocks.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, -1.0F);
	    render.renderFaceZNeg(Blocks.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, 1.0F);
	    render.renderFaceZPos(Blocks.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
	    render.renderFaceXNeg(Blocks.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(1.0F, 0.0F, 0.0F);
	    render.renderFaceXPos(Blocks.stone, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	}
}

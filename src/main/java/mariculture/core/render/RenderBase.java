package mariculture.core.render;

import java.util.HashMap;

import mariculture.core.tile.base.TileMultiBlock;
import mariculture.core.util.IFaceable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public abstract class RenderBase {
	public static HashMap<String, RenderBase> renderers = new HashMap();
	
	static final double RENDER_OFFSET = 0.0010000000474974513D;
	static final float LIGHT_Y_NEG = 0.5F;
	static final float LIGHT_Y_POS = 1.0F;
	static final float LIGHT_XZ_NEG = 0.8F;
	static final float LIGHT_XZ_POS = 0.6F;
	
	public RenderBlocks render;
	public ForgeDirection dir = ForgeDirection.UNKNOWN;
	public IBlockAccess world;
	public int x, y, z;
	public IIcon icon;
	public Block block;
	public boolean isItem;
	public int brightness = -1;
	public float rgb_red = 1.0F;
	public float rgb_green = 1.0F;
	public float rgb_blue = 1.0F;
	
	public RenderBase() {}
	//World Based Rendering
	public boolean render(RenderBlocks render, IBlockAccess world, int x, int y, int z) {
		this.isItem = false;
		this.render = render;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = world.getBlock(x, y, z);
		init();
		render();
		return true;
	}
	
	//Item Based Rendering
	public void render(RenderBlocks render, Block block) {
		this.isItem = true;
		this.render = render;
		this.block = block;
		render();
	}
	
	public boolean render() {
		if(isItem()) {
			GL11.glPushMatrix();
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
	        GL11.glTranslatef(-0.5F, -0.4F, -0.5F);
			GL11.glTranslatef(0F, -0.1F, 0F);
			renderBlock();
			GL11.glPopMatrix();
		} else {
			renderBlock();
			if(render.hasOverrideBlockTexture())
				render.clearOverrideBlockTexture();
		}
		
		return true;
	}
	
	public abstract void renderBlock();
	public void init() {
		if(world.getTileEntity(x, y, z) instanceof IFaceable) {
			this.dir = ((IFaceable)world.getTileEntity(x, y, z)).getFacing();
		}
		
		if(world.getTileEntity(x, y, z) instanceof TileMultiBlock) {
			this.dir = ((TileMultiBlock)world.getTileEntity(x, y, z)).facing;
		}
	}
	
	public boolean isItem() {
		return isItem;
	}
	
	protected void setTexture(IIcon texture) {
		icon = texture;
		if(!isItem()) render.setOverrideBlockTexture(texture);
	}
	
	protected void setTexture(ItemStack stack) {
		setTexture(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
	}
	
	protected void setTexture(Block block, int meta) {
		setTexture(block.getIcon(0, meta));
	}
	
	protected void setTexture(Block block) {
		setTexture(block, 0);
	}
	
	protected void renderBlock(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		if(isItem())
			renderItemBlock(minX, minY, minZ, maxX, maxY, maxZ);
		else
			renderWorldBlock(minX, minY, minZ, maxX,  maxY, maxZ);
	}
	
	protected void renderFace(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4) {
		//Diagonal Guessing
		Tessellator tessellator = Tessellator.instance;
        IIcon iicon = this.icon;
        if(!isItem()) tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        if(brightness > -1) {
        	tessellator.setBrightness(brightness);
        }
        
        if(!isItem()) {
        	iicon = render.getBlockIcon(block, render.blockAccess, x, y, z, 0);
        }
        
        //tessellator.setBrightness(240);
        tessellator.setColorOpaque_F(rgb_red, rgb_green, rgb_blue);
        double d0 = (double)iicon.getMinU();
        double d1 = (double)iicon.getMinV();
        double d2 = (double)iicon.getMaxU();
        double d3 = (double)iicon.getMaxV();
        double d4 = 0.0625D;
        double d5 = (double)(x + 1) + x1;
        double d6 = (double)(x + 1) + x2;
        double d7 = (double)(x + 0) + x3;
        double d8 = (double)(x + 0) + x4;
        double d9 = (double)(z + 0) + z1;
        double d10 = (double)(z + 1) + z2;
        double d11 = (double)(z + 1) + z3;
        double d12 = (double)(z + 0) + z4;
        double d13 = (double)y + d4 + y1;
        double d14 = (double)y + d4 + y2;
        double d15 = (double)y + d4 + y3;
        double d16 = (double)y + d4 + y4;

        tessellator.addVertexWithUV(d5, d13, d9, d2, d1);
        tessellator.addVertexWithUV(d6, d14, d10, d2, d3);
        tessellator.addVertexWithUV(d7, d15, d11, d0, d3);
        tessellator.addVertexWithUV(d8, d16, d12, d0, d1);
        tessellator.addVertexWithUV(d8, d16, d12, d0, d1);
        tessellator.addVertexWithUV(d7, d15, d11, d0, d3);
        tessellator.addVertexWithUV(d6, d14, d10, d2, d3);
        tessellator.addVertexWithUV(d5, d13, d9, d2, d1);
	}
	
	protected void renderFluid(FluidStack fluid, int max, double scale, int xPlus, int yPlus, int zPlus) {
		int x2 = x + xPlus;
		int y2 = y + yPlus;
		int z2 = z + zPlus;
		
		Tessellator tessellator = Tessellator.instance;
		int color = block.colorMultiplier(world, x2, y2, z2);
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		
		double extra = (((double)fluid.amount)/max) * scale;
		double height = 0.4D + extra;
		IIcon iconStill = fluid.getFluid().getIcon();

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
	
	protected void renderFluidBlock(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		render.renderAllFaces = true;
		render.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
		render.renderStandardBlock(Blocks.lava, this.x, this.y, this.z);
		render.renderAllFaces = false;
	}

	private void renderWorldBlock(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		render.renderAllFaces = true;
		render.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
		render.renderStandardBlock(block, this.x, this.y, this.z);
		render.renderAllFaces = false;
	}
	
	protected void renderAngledBlock(double x2, double y2, double z2, double x3, double y3, double z3, double x1, double y1, double z1, double x4, double y4, double z4) {
		renderAngledBlock(x2, y2, z2, x3, y3, z3, x1, y1, z1, x4, y4, z4, 0D, 0D, 0D);
	}
	
	protected void renderAngledBlock(double x2, double y2, double z2, double x3, double y3, double z3, double x1, double y1, double z1, double x4, double y4, double z4, double xDim, double height, double zDim) {
		if(icon == null) return;
		renderFace(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4);
		renderFace(x1, y1 + height, z1, x2, y2 + height, z2, x3, y3 + height, z3, x4, y4 + height, z4);
		renderFace(x1, y1, z1, x2, y2, z2, x2 + 1, y2 + height, z2, x1 + 1, y1 + height, z1);
		renderFace(x4 - 1, y4, z4, x3 - 1, y3, z3, x3, y3 + height, z3, x4, y4 + height, z4);
		renderFace(x1, y1, z1, x1, y1 + height, z1 - 1, x4, y4 + height, z4 - 1, x4, y4, z4);
		renderFace(x2, y2, z2 + 1, x2, y2 + height, z2, x3, y3 + height, z3, x3, y3, z3 + 1);
	}

	private void renderItemBlock(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		if(icon == null) return;
		render.renderMinX = minX;
	    render.renderMinY = minY;
	    render.renderMinZ = minZ;
	    render.renderMaxX = maxX;
	    render.renderMaxY = maxY;
	    render.renderMaxZ = maxZ;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, -1.0F, 0.0F);
	    render.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 1.0F, 0.0F);
	    render.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, -1.0F);
	    render.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, 1.0F);
	    render.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
	    render.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(1.0F, 0.0F, 0.0F);
	    render.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
	    tessellator.draw();
	}
}

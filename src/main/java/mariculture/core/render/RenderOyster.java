package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.TileOyster;
import mariculture.core.lib.PearlColor;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class RenderOyster extends RenderBase {
	public static IIcon lid_blur;
	public static IIcon lid;
	public static IIcon tongue;
	
	public RenderOyster() {}
	public RenderOyster(RenderBlocks render) {
		super(render);
	}
	
	public ItemStack stack;
	
	@Override
	public void init() {
		TileOyster tile = (TileOyster) world.getTileEntity(x, y, z);
		this.dir = tile.orientation;
		this.stack = tile.getStackInSlot(0);
	}
	
	@Override
	public boolean isItem() {
		return isItem;
	}

	@Override
	public void renderBlock() {	
		if(isItem) {
			GL11.glScalef(1.25F, 1.25F, 1.25F);
			GL11.glTranslatef(-0.15F, 0.05F, 0F);
		}
		
		/**
		 * setTexture(tongue);
			//Bottom of Tongue
			renderBlock(0.15, 0.1, 0.15, 0.85, 0.11, 0.9);
			//Top of Tongue
			renderAngledBlock(-0.15D, 0.55D, -0.35D, 0.2D, 0.55D, -0.35D, -0.15D, 0.05D, 0.1D, 0.2D, 0.05D, 0.1D, 0.0D, 0.005D, 0.0D);
			
			setTexture(lid);
			//Bottom 2
			renderBlock(0.1, 0.05, 0.1, 0.9, 0.1, 0.9);
			//Bottom
			renderBlock(0.2, 0.0, 0.2, 0.8, 0.05, 0.8);
			//Sides - left
			renderBlock(0.1, 0.1, 0.1, 0.15, 0.15, 0.9);
			//Sides - right
			renderBlock(0.85, 0.1, 0.1, 0.9, 0.15, 0.9);
			//Sides - back
			renderBlock(0.1, 0.1, 0.05, 0.9, 0.15, 0.1);
			//Sides - front
			renderBlock(0.15, 0.1, 0.9, 0.85, 0.15, 0.95);
			
			//Second Angle Render
			renderAngledBlock(-0.2D, 0.6D, -0.4D, 0.2D, 0.6D, -0.4D, -0.2D, 0.15D, 0.15D, 0.2D, 0.15D, 0.15D, 0.0D, 0.05D, 0.0D);
			//Third Angle Render
			renderAngledBlock(-0.3D, 0.55D, -0.5D, 0.3D, 0.55D, -0.5D, -0.3D, 0.3D, 0.25D, 0.3D, 0.3D, 0.25D, 0.0D, 0.05D, 0.0D);
			
			//Cobble x3, Bedrock x 3, Planks x 3, Sand x 3
			
			renderAngledBlock(-0.15D, 0.6D, -0.35D, 0.15D, 0.6D, -0.35D, -0.15D, 0.05D, 0.1D, 0.15D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);
			//Top
			if(!isItem) {
				renderBlock(0.15, 0.62, 0.65, 0.85, 0.67, 0.7);
			} else {
				renderBlock(0.15, 0.15, 0.0, 0.85, 0.7, 0.1);
				setTexture(tongue);
				renderBlock(0.15, 0.15, 0.1, 0.85, 0.7, 0.101);
			}
			
			setTexture(lid_blur);
			//Side 1
			renderAngledBlock(-0.1D, 0.55D, -0.35D, 0.85D, 0.55D, -0.35D, -0.1D, 0.05D, 0.1D, 0.85D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);
			//Side 2
			renderAngledBlock(-0.85D, 0.55D, -0.35D, 0.1D, 0.55D, -0.35D, -0.85D, 0.05D, 0.1D, 0.1D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);
		 */
		
		if(dir == ForgeDirection.SOUTH || isItem) {
			setTexture(tongue);
			renderBlock(0.15, 0.1, 0.15, 0.85, 0.11, 0.9);
			renderAngledBlock(-0.15D, 0.55D, -0.35D, 0.2D, 0.55D, -0.35D, -0.15D, 0.05D, 0.1D, 0.2D, 0.05D, 0.1D, 0.0D, 0.005D, 0.0D);
			
			setTexture(lid);
			renderBlock(0.1, 0.05, 0.1, 0.9, 0.1, 0.9);
			renderBlock(0.2, 0.0, 0.2, 0.8, 0.05, 0.8);
			renderBlock(0.1, 0.1, 0.1, 0.15, 0.15, 0.9);
			renderBlock(0.85, 0.1, 0.1, 0.9, 0.15, 0.9);
			renderBlock(0.1, 0.1, 0.05, 0.9, 0.15, 0.1);
			renderBlock(0.15, 0.1, 0.9, 0.85, 0.15, 0.95);
			renderAngledBlock(-0.2D, 0.6D, -0.4D, 0.2D, 0.6D, -0.4D, -0.2D, 0.15D, 0.15D, 0.2D, 0.15D, 0.15D, 0.0D, 0.05D, 0.0D);
			renderAngledBlock(-0.3D, 0.55D, -0.5D, 0.3D, 0.55D, -0.5D, -0.3D, 0.3D, 0.25D, 0.3D, 0.3D, 0.25D, 0.0D, 0.05D, 0.0D);
			renderAngledBlock(-0.15D, 0.6D, -0.35D, 0.15D, 0.6D, -0.35D, -0.15D, 0.05D, 0.1D, 0.15D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);
			
			if(!isItem) {
				renderBlock(0.15, 0.62, 0.65, 0.85, 0.67, 0.7);
			} else {
				renderBlock(0.15, 0.15, 0.0, 0.85, 0.7, 0.1);
				setTexture(tongue);
				renderBlock(0.15, 0.15, 0.1, 0.85, 0.7, 0.101);
			}
			
			setTexture(lid_blur);
			renderAngledBlock(-0.1D, 0.55D, -0.35D, 0.85D, 0.55D, -0.35D, -0.1D, 0.05D, 0.1D, 0.85D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);
			renderAngledBlock(-0.85D, 0.55D, -0.35D, 0.1D, 0.55D, -0.35D, -0.85D, 0.05D, 0.1D, 0.1D, 0.05D, 0.1D, 0.0D, 0.05D, 0.0D);
		} else if (dir == ForgeDirection.EAST) {
			// Birch >> Acacia >> Jungle >> Dark Oak
			setTexture(tongue);
			renderBlock(0.15, 0.1, 0.15, 0.9, 0.11, 0.85);
			renderAngledBlock(	-0.35D, 0.55D, -0.15D, 0.1D, 0.05D, -0.15D, -0.35D, 0.55D, 0.15D, 0.1D, 0.05D, 0.15D, 0.0D, 0.005D, 0.0D);
			setTexture(lid);
			renderAngledBlock(-0.4D, 0.6D, -0.2D, 0.1D, 0.15D, -0.2D, -0.4D, 0.6D, 0.2D, 0.1D, 0.15D, 0.2D, 0.0D, 0.05D, 0.0D);
			renderAngledBlock(-0.5D, 0.55D, -0.3D,  0.2D, 0.3D, -0.3D, -0.5D, 0.55D, 0.3D, 0.2D, 0.3D, 0.3D, 0.0D, 0.05D, 0.0D);
			renderAngledBlock(-0.35D, 0.6D, -0.15D, 0.05D, 0.05D, -0.15D, -0.35D, 0.6D, 0.15D, 0.05D, 0.05D, 0.15D, 0.0D, 0.05D, 0.0D);
			renderBlock(0.1, 0.05, 0.1, 0.9, 0.1, 0.9);
			renderBlock(0.2, 0.0, 0.2, 0.8, 0.05, 0.8);
			renderBlock(0.1, 0.1, 0.1, 0.9, 0.15, 0.15);
			renderBlock(0.1, 0.1, 0.85, 0.9, 0.15, 0.9);
			renderBlock(0.05, 0.1, 0.1, 0.1, 0.15, 0.9);
			renderBlock(0.9, 0.1, 0.15, 0.95, 0.15, 0.85);
			renderBlock(0.65, 0.62, 0.15, 0.7, 0.67, 0.85);
			setTexture(lid_blur);
			renderAngledBlock(-0.35D, 0.55D, -0.9D, 0.1D, 0.05D, -0.9D, -0.35D, 0.55D, 0.15D, 0.1D, 0.05D, 0.15D, 0.0D, 0.05D, 0.0D);
			renderAngledBlock(-0.35D, 0.55D, -0.15D, 0.1D, 0.05D, -0.15D, -0.35D, 0.55D, 0.9D, 0.1D, 0.05D, 0.9D, 0.0D, 0.05D, 0.0D);
		} else if (dir == ForgeDirection.WEST) {
			setTexture(tongue);
			renderBlock(0.05, 0.1, 0.15, 0.85, 0.11, 0.85);
			//renderAngledBlock(	-0.35D, 0.55D, -0.15D, 0.1D, 0.05D, -0.15D, -0.35D, 0.55D, 0.15D, 0.1D, 0.05D, 0.15D, 0.0D, 0.005D, 0.0D);
			setTexture(lid);
			//renderAngledBlock(-0.4D, 0.6D, -0.2D, 0.1D, 0.15D, -0.2D, -0.4D, 0.6D, 0.2D, 0.1D, 0.15D, 0.2D, 0.0D, 0.05D, 0.0D);
			//renderAngledBlock(-0.5D, 0.55D, -0.3D,  0.2D, 0.3D, -0.3D, -0.5D, 0.55D, 0.3D, 0.2D, 0.3D, 0.3D, 0.0D, 0.05D, 0.0D);
			//renderAngledBlock(-0.35D, 0.6D, -0.15D, 0.05D, 0.05D, -0.15D, -0.35D, 0.6D, 0.15D, 0.05D, 0.05D, 0.15D, 0.0D, 0.05D, 0.0D);
			renderBlock(0.1, 0.05, 0.1, 0.9, 0.1, 0.9);
			renderBlock(0.2, 0.0, 0.2, 0.8, 0.05, 0.8);
			renderBlock(0.1, 0.1, 0.1, 0.9, 0.15, 0.15);
			renderBlock(0.1, 0.1, 0.85, 0.9, 0.15, 0.9);
			renderBlock(0.05, 0.1, 0.15, 0.1, 0.15, 0.85);
			renderBlock(0.9, 0.1, 0.1, 0.95, 0.15, 0.9);
			renderBlock(0.35, 0.62, 0.15, 0.4, 0.67, 0.85);
			setTexture(lid_blur);
			//renderAngledBlock(-0.35D, 0.55D, -0.9D, 0.1D, 0.05D, -0.9D, -0.35D, 0.55D, 0.15D, 0.1D, 0.05D, 0.15D, 0.0D, 0.05D, 0.0D);
			//renderAngledBlock(-0.35D, 0.55D, -0.15D, 0.1D, 0.05D, -0.15D, -0.35D, 0.55D, 0.9D, 0.1D, 0.05D, 0.9D, 0.0D, 0.05D, 0.0D);
		}
		
		renderContents();
	}
	
	public void renderContents() {
		if(isItem) {
			setTexture(Core.pearlBlock, PearlColor.WHITE);
			renderBlock(0.4, 0.05, 0.4, 0.6, 0.25, 0.6);
		}
	}
}

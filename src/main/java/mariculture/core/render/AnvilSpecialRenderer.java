package mariculture.core.render;

import mariculture.core.tile.TileAnvil;
import mariculture.core.util.EntityFakeItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AnvilSpecialRenderer extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
		TileAnvil anvil = (TileAnvil) tile;
		GL11.glPushMatrix();
		int meta = tile.getBlockMetadata() - 7;
		float offsetX = (float)x;
		float offsetZ = (float)z;
		
		if(meta %2 == 0) {
			offsetX-=0.8F;
			offsetZ-=0.5F;
		} else {
			offsetX-=0.8F;
			offsetZ-=0.5F;
		}
		
		float offsetY = (float) (y + 0.082F);
		GL11.glTranslatef(offsetX, offsetY, offsetZ);
		ItemStack stack = anvil.getStackInSlot(0);
		if (stack != null)
			renderItem(tile.getWorldObj(), stack, meta);
		GL11.glPopMatrix();
	}

	void renderItem(World world, ItemStack stack, int meta) {
		EntityFakeItem entityitem = new EntityFakeItem(world, 0.0D, 0.0D, 0.0D, stack);
        GL11.glPushMatrix();
        GL11.glTranslatef(1.3F, 1.1F, 1F);
        GL11.glScalef(1.2F, 1.2F, 1.2F);
        if (!(stack.getItem() instanceof ItemBlock)) {
        	GL11.glRotatef(-90, 1F, 0F, 0F);
        	if(meta == 0) {
        		GL11.glTranslatef(0.1F, 0F, -0.15F);
        		GL11.glRotatef(90, 0F, 0F, 1F);
        	} else if(meta == 1) {
        		GL11.glTranslatef(0F, -0.1F, -0.15F);
        	} else if(meta == 2) {
        		GL11.glTranslatef(-0.1F, 0F, -0.15F);
        		GL11.glRotatef(-90, 0F, 0F, 1F);
        	} else if(meta == 3) {
        		GL11.glTranslatef(0F, 0.1F, -0.15F);
        		GL11.glRotatef(180, 0F, 0F, 1F);
        	}
        } else {
        	GL11.glTranslatef(0F, -0.06F, 0F);
        	if(meta == 0) {
        		GL11.glRotatef(90, 0F, 1F, 0F);
        	} else if(meta == 2) {
        		GL11.glRotatef(-90, 0F, 1F, 0F);
        	} else if(meta == 3) {
        		GL11.glRotatef(180, 0F, 1F, 0F);
        	}
        }
      
        RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);

        GL11.glPopMatrix();
	}
}

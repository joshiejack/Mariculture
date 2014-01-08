package mariculture.core.render;

import mariculture.core.blocks.TileVat;
import mariculture.core.util.EntityFakeItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VatSpecialRenderer extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
		TileVat vat = (TileVat) tile;
		World world = vat.worldObj;
		if(vat.master == null) {
			GL11.glPushMatrix();
			float offsetX = (float) (x - 0.5F);
			float offsetY = (float) (y - 0.1F);
			float offsetZ = (float) (z - 0.5F);
			GL11.glTranslatef(offsetX, offsetY, offsetZ);
	
			ItemStack stack = vat.getStackInSlot(0);
			if (stack != null)
				renderItem(world, stack);
	
			stack = vat.getStackInSlot(1);
			if (stack != null) {
				renderItem(world, stack);
			}
	
			GL11.glPopMatrix();
		} else {
			if(vat.isMaster()) {
				GL11.glPushMatrix();
				float offsetY = (float) (y - 0.1F);
				GL11.glTranslatef((float)x, offsetY, (float)z);
		
				ItemStack stack = vat.getStackInSlot(0);
				if (stack != null) {
					renderItem(world, stack);
				}
		
				stack = vat.getStackInSlot(1);
				if (stack != null)
					renderItem(world, stack);
		
				GL11.glPopMatrix();
			}
		}
	}

	void renderItem(World world, ItemStack stack) {
		EntityFakeItem item = new EntityFakeItem(world, 0.0D, 0.0D, 0.0D, stack);
		GL11.glPushMatrix();
		GL11.glTranslatef(1F, 0.675F, 1.0F);
		GL11.glScalef(1.75F, 1.75F, 1.75F);
		RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glPopMatrix();
	}
}

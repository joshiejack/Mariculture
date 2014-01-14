package mariculture.fishery.render;

import mariculture.core.util.EntityFakeItem;
import mariculture.fishery.TileFishTank;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FishTankSpecialRenderer extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
		TileFishTank tank = (TileFishTank) tile;
		World world = tank.worldObj;
		GL11.glPushMatrix();
		float offsetX = (float) (x - 0.2F);
		float offsetY = (float) (y - 0F);
		float offsetZ = (float) (z - 0.2F);
		GL11.glTranslatef(offsetX, offsetY, offsetZ);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		
		ItemStack stack = tank.getStackInSlot(0);
		if (stack != null)
			renderItem(world, stack, 1F, 0.55F, 0.5F);
	
		stack = tank.getStackInSlot(1);
		if (stack != null) {
			renderItem(world, stack, 1.9F, 0.675F, 1.3F);
		}
		
		stack = tank.getStackInSlot(2);
		if (stack != null) {
			renderItem(world, stack, 0.9F, 0.9F, 1.75F);
		}
	
		GL11.glPopMatrix();
	}

	void renderItem(World world, ItemStack stack, float x, float y, float z) {		
		EntityFakeItem item = new EntityFakeItem(world, 0.0D, 0.0D, 0.0D, stack);
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glScalef(1.75F, 1.75F, 1.75F);
		RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		GL11.glPopMatrix();
	}
}

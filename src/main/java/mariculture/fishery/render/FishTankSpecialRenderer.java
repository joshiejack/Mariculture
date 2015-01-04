package mariculture.fishery.render;

import mariculture.core.util.EntityFakeItem;
import mariculture.fishery.tile.TileFishTank;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FishTankSpecialRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
        TileFishTank tank = (TileFishTank) tile;
        World world = tank.getWorldObj();
        GL11.glPushMatrix();
        float offsetX = (float) (x - 0.2F);
        float offsetY = (float) (y - 0F);
        float offsetZ = (float) (z - 0.2F);
        GL11.glTranslatef(offsetX, offsetY, offsetZ);
        GL11.glScalef(0.5F, 0.5F, 0.5F);

        float dir = 1F;

        ItemStack stack = tank.getStackInSlot(0);
        if (stack != null) {
            renderItem(world, stack, 1F, 0.55F, 0.5F, tank.orientation);
        }

        stack = tank.getStackInSlot(1);
        if (stack != null) {
            renderItem(world, stack, 1.9F, 0.675F, 1.3F, tank.orientation);
        }

        stack = tank.getStackInSlot(2);
        if (stack != null) {
            renderItem(world, stack, 0.9F, 0.9F, 1.75F, tank.orientation);
        }

        GL11.glPopMatrix();
    }

    void renderItem(World world, ItemStack stack, float x, float y, float z, ForgeDirection dir) {
        EntityFakeItem item = new EntityFakeItem(world, 0.0D, 0.0D, 0.0D, stack);
        GL11.glPushMatrix();
        if (dir == ForgeDirection.EAST) {
            GL11.glRotatef(270, 0F, 1F, 0F);
            GL11.glTranslatef(x, y, (float) (z - 2.75));
        } else if (dir == ForgeDirection.WEST) {
            GL11.glRotatef(90, 0F, 1F, 0F);
            GL11.glTranslatef((float) (x - 2.75), y, z);
        } else if (dir == ForgeDirection.NORTH) {
            GL11.glTranslatef(x, y, z);
        } else if (dir == ForgeDirection.SOUTH) {
            GL11.glRotatef(180, 0F, 1F, 0F);
            GL11.glTranslatef((float) (x - 2.75), y, (float) (z - 2.5));
        } else if (dir == ForgeDirection.UP) {
            GL11.glRotatef(270, 0F, 1F, 0F);
            GL11.glTranslatef(x, y, (float) (z - 2.75));
        } else if (dir == ForgeDirection.DOWN) {
            GL11.glRotatef(180, 0F, 1F, 0F);
            GL11.glTranslatef((float) (x - 2.75), y, (float) (z - 2.5));
        }

        GL11.glScalef(1.75F, 1.75F, 1.75F);
        RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        GL11.glPopMatrix();
    }
}

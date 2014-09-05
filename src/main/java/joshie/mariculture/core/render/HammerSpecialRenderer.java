package joshie.mariculture.core.render;

import joshie.mariculture.core.tile.TileAutohammer;
import joshie.mariculture.core.util.EntityFakeItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HammerSpecialRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        TileAutohammer hammer = (TileAutohammer) tile;
        World world = hammer.getWorldObj();

        GL11.glPushMatrix();
        float offsetX = (float) (x + 0.2F);
        float offsetY = (float) (y - 0F);
        float offsetZ = (float) (z + 0.2F);
        GL11.glTranslatef(offsetX, offsetY, offsetZ);
        GL11.glScalef(0.8F, 0.8F, 0.8F);

        renderItem(world, hammer.getStackInSlot(0), 0.4F, 0.455F, -0.35F, hammer.angle[0], 270F);
        renderItem(world, hammer.getStackInSlot(1), 0.4F, 0.455F, 1.2F, hammer.angle[1], 90F);
        renderItem(world, hammer.getStackInSlot(2), -0.35F, 0.455F, 0.4F, hammer.angle[2], 0F);
        renderItem(world, hammer.getStackInSlot(3), 1.2F, 0.455F, 0.4F, hammer.angle[3], 180F);

        GL11.glPopMatrix();

    }

    private void renderItem(World world, ItemStack stack, float x, float y, float z, float angle, float dir) {
        if (stack != null) {
            EntityFakeItem item = new EntityFakeItem(world, 0.0D, 0.0D, 0.0D, stack);
            GL11.glPushMatrix();
            GL11.glTranslatef(x, y, z);
            GL11.glRotatef(dir, 0F, 1F, 0F);
            GL11.glRotatef(85F + angle, 0F, 0F, 1F);
            //GL11.glRotatef(dir, 1F, 0F, 0F);
            GL11.glScalef(1.8F, 1.8F, 1.8F);
            RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            GL11.glPopMatrix();
        }
    }
}

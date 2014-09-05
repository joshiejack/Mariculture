package joshie.mariculture.core.render;

import joshie.mariculture.core.tile.TileVat;
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
public class VatSpecialRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        TileVat vat = (TileVat) tile;
        World world = vat.getWorldObj();
        if (vat.master == null) {
            GL11.glPushMatrix();
            float offsetX = (float) (x - 0.2F);
            float offsetY = (float) (y - 0F);
            float offsetZ = (float) (z - 0.2F);
            GL11.glTranslatef(offsetX, offsetY, offsetZ);
            GL11.glScalef(0.7F, 0.7F, 0.7F);

            ItemStack stack = vat.getStackInSlot(0);
            if (stack != null) {
                renderItem(world, stack);
            }

            stack = vat.getStackInSlot(1);
            if (stack != null) {
                renderItem(world, stack);
            }

            GL11.glPopMatrix();
        } else if (vat.isMaster()) {
            GL11.glPushMatrix();
            float offsetX = (float) (x + 0.2F);
            float offsetY = (float) (y - 0F);
            float offsetZ = (float) (z + 0.2F);
            GL11.glTranslatef(offsetX, offsetY, offsetZ);
            GL11.glScalef(0.8F, 0.8F, 0.8F);
            ItemStack stack = vat.getStackInSlot(0);
            if (stack != null) {
                renderItem(world, stack);
            }

            stack = vat.getStackInSlot(1);
            if (stack != null) {
                renderItem(world, stack);
            }

            GL11.glPopMatrix();
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

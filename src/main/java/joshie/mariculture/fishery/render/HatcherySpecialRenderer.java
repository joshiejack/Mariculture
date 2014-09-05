package joshie.mariculture.fishery.render;

import joshie.mariculture.core.util.EntityFakeItem;
import joshie.mariculture.fishery.tile.TileHatchery;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HatcherySpecialRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        TileHatchery hatchery = (TileHatchery) tile;
        World world = hatchery.getWorldObj();

        GL11.glPushMatrix();
        float offsetX = (float) (x + 0.2F);
        float offsetY = (float) (y - 0F);
        float offsetZ = (float) (z + 0.2F);
        GL11.glTranslatef(offsetX, offsetY, offsetZ);
        GL11.glScalef(0.8F, 0.8F, 0.8F);

        renderItem(world, hatchery.getStackInSlot(0), -0.65F, -0.25F, -0.65F);
        renderItem(world, hatchery.getStackInSlot(1), -0.4F, -0.3F, -0.4F);
        renderItem(world, hatchery.getStackInSlot(2), -0.8F, 0F, -0.9F);

        GL11.glPopMatrix();

    }

    void renderItem(World world, ItemStack stack, float x, float y, float z) {
        if (stack != null) {
            EntityFakeItem item = new EntityFakeItem(world, 0.0D, 0.0D, 0.0D, stack);
            GL11.glPushMatrix();
            GL11.glTranslatef(1F, 0.675F, 1.0F);
            GL11.glTranslatef(x, y, z);
            RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            GL11.glPopMatrix();
        }
    }
}

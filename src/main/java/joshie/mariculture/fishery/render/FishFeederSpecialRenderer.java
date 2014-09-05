package joshie.mariculture.fishery.render;

import java.util.ArrayList;
import java.util.Random;

import joshie.mariculture.api.util.CachedCoords;
import joshie.mariculture.core.util.EntityFakeItem;
import joshie.mariculture.fishery.tile.TileFeeder;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class FishFeederSpecialRenderer extends TileEntitySpecialRenderer {
    private double getPos(int original, int passive) {
        return passive - original;
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        TileFeeder feeder = ((TileFeeder) tile);
        ItemStack male = feeder.getStackInSlot(feeder.male);
        ItemStack female = feeder.getStackInSlot(feeder.female);
        ArrayList<CachedCoords> coords = feeder.coords;
        if (coords == null || coords.size() <= 0) return;
        if (male != null) {
            CachedCoords c = feeder.mPos < coords.size() ? coords.get(feeder.mPos) : null;
            //Setup Random coordinate for male
            if (feeder.mTicker <= 0) {
                feeder.mPos = new Random().nextInt(coords.size());
                feeder.mTicker = 2000;
                feeder.mRot = new Random().nextInt(360);
                for (int i = 0; i < 7; i++) {
                    tile.getWorldObj().spawnParticle("splash", c.x + 0.5D, c.y + 0.85D, c.z + 0.5D, 0, 0, 0);
                }
            } else feeder.mTicker--;

            if (c != null) {
                GL11.glPushMatrix();
                double x2 = getPos(tile.xCoord, c.x);
                double y2 = getPos(tile.yCoord, c.y);
                double z2 = getPos(tile.zCoord, c.z);
                GL11.glTranslated(x + x2 + 0.5F, y + y2, z + z2 + 0.5F);
                renderItem(tile.getWorldObj(), male, (feeder.mRot -= 0.2F));

                if (((int) feeder.mRot) % 25 == 0) {
                    tile.getWorldObj().spawnParticle("bubble", c.x + 0.5D, c.y + 0.55D, c.z + 0.5D, 0, 0, 0);
                }

                GL11.glPopMatrix();
            }
        }

        if (female != null) {
            CachedCoords c = feeder.fPos < coords.size() ? coords.get(feeder.fPos) : null;
            //Setup Random coordinate for female
            if (feeder.fTicker <= 0) {
                feeder.fPos = new Random().nextInt(coords.size());
                feeder.fTicker = 2000;
                feeder.fRot = new Random().nextInt(360);
                for (int i = 0; i < 7; i++) {
                    tile.getWorldObj().spawnParticle("splash", c.x + 0.5D, c.y + 0.85D, c.z + 0.5D, 0, 0, 0);
                }
            } else feeder.fTicker--;

            if (c != null) {
                GL11.glPushMatrix();
                double x2 = getPos(tile.xCoord, c.x);
                double y2 = getPos(tile.yCoord, c.y);
                double z2 = getPos(tile.zCoord, c.z);
                GL11.glTranslated(x + x2 + 0.5F, y + y2, z + z2 + 0.5F);
                renderItem(tile.getWorldObj(), female, (feeder.fRot -= 0.2F));
                if (((int) feeder.fRot) % 25 == 0) {
                    tile.getWorldObj().spawnParticle("bubble", c.x + 0.5D, c.y + 0.55D, c.z + 0.5D, 0, 0, 0);
                }

                GL11.glPopMatrix();
            }
        }
    }

    private void renderItem(World world, ItemStack stack, double angle) {
        EntityFakeItem item = new EntityFakeItem(world, 0.0D, 0.0D, 0.0D, stack);
        GL11.glPushMatrix();
        GL11.glRotated(angle, 0F, 1F, 0F);
        GL11.glScalef(1.8F, 1.8F, 1.8F);
        GL11.glTranslatef(0F, 0.1F, 0.15F);
        RenderManager.instance.renderEntityWithPosYaw(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        GL11.glPopMatrix();
    }
}

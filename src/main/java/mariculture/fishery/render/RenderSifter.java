package mariculture.fishery.render;

import mariculture.core.Core;
import mariculture.core.lib.TickingMeta;
import mariculture.core.render.RenderBase;
import mariculture.fishery.tile.TileSifter;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class RenderSifter extends RenderBase {
    public RenderSifter() {}

    @Override
    public void renderBlock() {
        if (isItem()) {
            GL11.glTranslatef(0F, -0.1F, 0F);
        }

        TileSifter tile = isItem() ? null : ((TileSifter) world.getTileEntity(x, y, z)).getMaster();
        if (dir == ForgeDirection.UNKNOWN || isItem() || tile == null) {
            setTexture(Blocks.planks);
            if (isItem()) {
                setTexture(Core.ticking, TickingMeta.NET);
            }
            renderBlock(0, 0.7, 0, 1, 0.8, 1);
            renderBlock(0.1, 0, 0.1, 0.2, 0.8, 0.2);
            renderBlock(0.8, 0, 0.1, 0.9, 0.8, 0.2);
            renderBlock(0.1, 0, 0.8, 0.2, 0.8, 0.9);
            renderBlock(0.8, 0, 0.8, 0.9, 0.8, 0.9);
        } else if (dir == ForgeDirection.SOUTH) {
            setTexture(tile.texture);
            renderBlock(0, 0.65, 0, 0.1, 0.8, 0.85);
            renderBlock(0.9, 0.65, 0, 1, 0.8, 0.85);

            renderBlock(0.1, 0.65, 0.75, 0.9, 0.8, 0.85);

            //Legs
            renderBlock(0.1, 0, 0.65, 0.2, 0.65, 0.75);
            renderBlock(0.8, 0, 0.65, 0.9, 0.65, 0.75);
            //Net
            setTexture(Core.ticking, TickingMeta.NET);
            renderBlock(0.1, 0.65, 0, 0.9, 0.66, 0.75);

            //TRAY
            if (tile.hasInventory) {
                setTexture(tile.texture);
                renderBlock(0, 0.0, 0, 0.1, 0.1, 0.85);
                renderBlock(0.9, 0.0, 0, 1, 0.1, 0.85);
                renderBlock(0.1, 0.0, 0.75, 0.9, 0.1, 0.85);
                setTexture(Blocks.dirt);
                renderBlock(0.1, 0.0, 0, 0.9, 0.05, 0.75);
            }
        } else if (dir == ForgeDirection.NORTH) {
            setTexture(tile.texture);
            renderBlock(0, 0.65, 0.15, 0.1, 0.8, 1);
            renderBlock(0.9, 0.65, 0.15, 1, 0.8, 1);

            renderBlock(0.1, 0.65, 0.15, 0.9, 0.8, 0.25);

            //Legs
            renderBlock(0.1, 0, 0.25, 0.2, 0.65, 0.35);
            renderBlock(0.8, 0, 0.25, 0.9, 0.65, 0.35);
            //Net
            setTexture(Core.ticking, TickingMeta.NET);
            renderBlock(0.1, 0.65, 0.25, 0.9, 0.66, 1);

            //TRAY
            if (tile.hasInventory) {
                setTexture(tile.texture);
                renderBlock(0, 0.0, 0.15, 0.1, 0.1, 1);
                renderBlock(0.9, 0.0, 0.15, 1, 0.1, 1);
                renderBlock(0.1, 0.0, 0.15, 0.9, 0.1, 0.25);
                setTexture(Blocks.dirt);
                renderBlock(0.1, 0.0, 0.25, 0.9, 0.05, 1);
            }
        } else if (dir == ForgeDirection.EAST) {
            setTexture(tile.texture);
            renderBlock(0.15, 0.65, 0, 1, 0.8, 0.1);
            renderBlock(0.15, 0.65, 0.9, 1, 0.8, 1);

            renderBlock(0.15, 0.65, 0.1, 0.25, 0.8, 0.9);

            //Legs
            renderBlock(0.25, 0, 0.1, 0.35, 0.65, 0.2);
            renderBlock(0.25, 0, 0.8, 0.35, 0.65, 0.9);
            //Net
            setTexture(Core.ticking, TickingMeta.NET);
            renderBlock(0.25, 0.65, 0.1, 1, 0.66, 0.9);

            //TRAY
            if (tile.hasInventory) {
                setTexture(tile.texture);
                renderBlock(0.15, 0.0, 0, 1, 0.1, 0.1);
                renderBlock(0.15, 0.0, 0.9, 1, 0.1, 1);
                renderBlock(0.15, 0.0, 0.1, 0.25, 0.1, 0.9);
                setTexture(Blocks.dirt);
                renderBlock(0.25, 0.0, 0.1, 1, 0.05, 0.9);
            }
        } else if (dir == ForgeDirection.WEST) {
            setTexture(tile.texture);
            renderBlock(0, 0.65, 0, 0.85, 0.8, 0.1);
            renderBlock(0, 0.65, 0.9, 0.85, 0.8, 1);

            renderBlock(0.75, 0.65, 0.1, 0.85, 0.8, 0.9);

            //Legs
            renderBlock(0.65, 0, 0.1, 0.75, 0.65, 0.2);
            renderBlock(0.65, 0, 0.8, 0.75, 0.65, 0.9);
            //Net
            setTexture(Core.ticking, TickingMeta.NET);
            renderBlock(0, 0.65, 0.1, 0.75, 0.66, 0.9);

            //TRAY
            if (tile.hasInventory) {
                setTexture(tile.texture);
                renderBlock(0, 0.0, 0, 0.85, 0.1, 0.1);
                renderBlock(0, 0.0, 0.9, 0.85, 0.1, 1);
                renderBlock(0.75, 0.0, 0.1, 0.85, 0.1, 0.9);
                setTexture(Blocks.dirt);
                renderBlock(0, 0.0, 0.1, 0.75, 0.05, 0.9);
            }
        }
    }
}

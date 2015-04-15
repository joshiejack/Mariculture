package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.tile.TileVat;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderVat extends RenderBase {
    public RenderVat() {}

    @Override
    public void renderBlock() {
        setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.VAT);
        if (dir == ForgeDirection.UNKNOWN || isItem()) {
            //Edge Inner - Edge Outer
            renderBlock(0, 0.3, 0, 0.1, 0.9, 1);
            renderBlock(-0.1, 0.35, 0.1, 0, 0.85, 0.9);
            renderBlock(0.9, 0.3, 0, 1, 0.9, 1);
            renderBlock(1, 0.35, 0.1, 1.1, 0.85, 0.9);
            renderBlock(0.1, 0.3, 0, 0.9, 0.9, 0.1);
            renderBlock(0.1, 0.35, -0.1, 0.9, 0.85, 0);
            renderBlock(0.1, 0.3, 0.9, 0.9, 0.9, 1);
            renderBlock(0.1, 0.35, 1, 0.9, 0.85, 1.1);
            //Legs
            renderBlock(0.1, 0, 0.1, 0.2, 0.3, 0.2);
            renderBlock(0.8, 0, 0.1, 0.9, 0.3, 0.2);
            renderBlock(0.1, 0, 0.8, 0.2, 0.3, 0.9);
            renderBlock(0.8, 0, 0.8, 0.9, 0.3, 0.9);
            //Bottom
            renderBlock(0.2, 0.2, 0.2, 0.8, 0.3, 0.8);
            renderBlock(0.1, 0.3, 0.1, 0.9, 0.4, 0.9);
            //Liquid
            if (!isItem()) {
                TileVat tile = (TileVat) world.getTileEntity(x, y, z);
                if (tile.getFluid((byte) 1) != null) {
                    renderFluid(tile.getFluid((byte) 1), tile.max_sml, 0.46D, 0, 0, 0);
                }
                if (tile.getFluid((byte) 2) != null) {
                    renderFluid(tile.getFluid((byte) 2), tile.max_sml, 0.461D, 0, 0, 0);
                }
                if (tile.getFluid((byte) 3) != null) {
                    renderFluid(tile.getFluid((byte) 3), tile.max_sml, 0.462D, 0, 0, 0);
                }
            }

        } else if (dir == ForgeDirection.NORTH) {
            //Edge Inner - Edge Outer
            renderBlock(0.9, 0.3, 0, 1, 1, 1);
            renderBlock(1, 0.35, 0, 1.1, 0.95, 0.9);
            renderBlock(0, 0.3, 0.9, 0.9, 1, 1);
            renderBlock(0, 0.35, 1, 0.9, 0.95, 1.1);
            //Legs
            renderBlock(0.8, 0, 0.8, 0.9, 0.3, 0.9);
            //Bottom
            renderBlock(0, 0.2, 0, 0.8, 0.3, 0.8);
            renderBlock(0, 0.3, 0, 0.9, 0.4, 0.9);
        } else if (dir == ForgeDirection.EAST) {
            renderBlock(0, 0.3, 0, 0.1, 1, 1);
            renderBlock(-0.1, 0.35, 0, 0, 0.95, 0.9);
            renderBlock(0.1, 0.3, 0.9, 1, 1, 1);
            renderBlock(0.1, 0.35, 1, 1, 0.95, 1.1);
            //Legs
            renderBlock(0.1, 0, 0.8, 0.2, 0.3, 0.9);
            //Bottom
            renderBlock(0.2, 0.2, 0, 1, 0.3, 0.8);
            renderBlock(0.1, 0.3, 0, 1, 0.4, 0.9);
        } else if (dir == ForgeDirection.SOUTH) {
            renderBlock(0, 0.3, 0, 0.1, 1, 1);
            renderBlock(-0.1, 0.35, 0.1, 0, 0.95, 1);
            renderBlock(0.1, 0.3, 0, 1, 1, 0.1);
            renderBlock(0.1, 0.35, -0.1, 1, 0.95, 0);
            //Legs
            renderBlock(0.1, 0, 0.1, 0.2, 0.3, 0.2);
            //Bottom
            renderBlock(0.2, 0.2, 0.2, 1, 0.3, 1);
            renderBlock(0.1, 0.3, 0.1, 1, 0.4, 1);
            //Liquid
            TileVat tile = (TileVat) world.getTileEntity(x, y, z);
            if (tile.getFluid((byte) 1) != null) {
                renderFluid(tile.getFluid((byte) 1), tile.max_lrg, 0.56D, 0, 0, 0);
                renderFluid(tile.getFluid((byte) 1), tile.max_lrg, 0.56D, +1, 0, 0);
                renderFluid(tile.getFluid((byte) 1), tile.max_lrg, 0.56D, +1, 0, +1);
                renderFluid(tile.getFluid((byte) 1), tile.max_lrg, 0.56D, 0, 0, +1);
            }

            if (tile.getFluid((byte) 2) != null) {
                renderFluid(tile.getFluid((byte) 2), tile.max_lrg, 0.56D, 0, 0, 0);
                renderFluid(tile.getFluid((byte) 2), tile.max_lrg, 0.56D, +1, 0, 0);
                renderFluid(tile.getFluid((byte) 2), tile.max_lrg, 0.56D, +1, 0, +1);
                renderFluid(tile.getFluid((byte) 2), tile.max_lrg, 0.56D, 0, 0, +1);
            }

            if (tile.getFluid((byte) 3) != null) {
                renderFluid(tile.getFluid((byte) 3), tile.max_lrg, 0.56D, 0, 0, 0);
                renderFluid(tile.getFluid((byte) 3), tile.max_lrg, 0.56D, +1, 0, 0);
                renderFluid(tile.getFluid((byte) 3), tile.max_lrg, 0.56D, +1, 0, +1);
                renderFluid(tile.getFluid((byte) 3), tile.max_lrg, 0.56D, 0, 0, +1);
            }
        } else if (dir == ForgeDirection.WEST) {
            renderBlock(0.9, 0.3, 0, 1, 1, 1);
            renderBlock(1, 0.35, 0.1, 1.1, 0.95, 1);
            renderBlock(0, 0.3, 0, 0.9, 1, 0.1);
            renderBlock(0, 0.35, -0.1, 0.9, 0.95, 0);
            //Legs
            renderBlock(0.8, 0, 0.1, 0.9, 0.3, 0.2);
            //Bottom
            renderBlock(0, 0.2, 0.2, 0.8, 0.3, 1);
            renderBlock(0, 0.3, 0.1, 0.9, 0.4, 1);
        }
    }
}

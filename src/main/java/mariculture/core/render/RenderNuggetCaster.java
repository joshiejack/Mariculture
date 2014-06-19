package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.handlers.CastingHandler;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.tile.TileNuggetCaster;

public class RenderNuggetCaster extends RenderBase {
    public RenderNuggetCaster() {}

    public TileNuggetCaster tile;

    @Override
    public void init() {
        super.init();
        tile = (TileNuggetCaster) world.getTileEntity(x, y, z);
    }

    private void renderNugget(int slot, double xStart, double xEnd, double yStart, double yEnd) {
        if (tile.getStackInSlot(slot) != null) {
            setTexture(CastingHandler.getTexture(tile.getStackInSlot(slot)));
            renderBlock(xStart, 0.05, yStart, xEnd, 0.45, yEnd);
        }
    }

    @Override
    public void renderBlock() {
        if (!isItem()) {
            if (tile == null) return;
            //Slots 0-3
            renderNugget(0, 0.1, 0.26, 0.1, 0.26);
            renderNugget(1, 0.32, 0.47, 0.1, 0.26);
            renderNugget(2, 0.53, 0.68, 0.1, 0.26);
            renderNugget(3, 0.74, 0.9, 0.1, 0.26);
            //Slots 4-7
            renderNugget(0, 0.1, 0.26, 0.32, 0.47);
            renderNugget(1, 0.32, 0.47, 0.32, 0.47);
            renderNugget(2, 0.53, 0.68, 0.32, 0.47);
            renderNugget(3, 0.74, 0.9, 0.32, 0.47);

            //Slots 8-11
            renderNugget(0, 0.1, 0.26, 0.53, 0.68);
            renderNugget(1, 0.32, 0.47, 0.53, 0.68);
            renderNugget(2, 0.53, 0.68, 0.53, 0.68);
            renderNugget(3, 0.74, 0.9, 0.53, 0.68);

            //Slots 12-15
            renderNugget(0, 0.1, 0.26, 0.74, 0.9);
            renderNugget(1, 0.32, 0.47, 0.74, 0.9);
            renderNugget(2, 0.53, 0.68, 0.74, 0.9);
            renderNugget(3, 0.74, 0.9, 0.74, 0.9);

            if (tile.getFluid() != null) {
                renderFluid(tile.getFluid(), MetalRates.NUGGET * 16, 0.055D, 0, 0, 0);
            }
        }

        setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.VAT);
        renderBlock(0, 0, 0, 1, 0.05, 1);
        //Sides
        setTexture(Core.renderedMachines, MachineRenderedMeta.INGOT_CASTER);
        renderBlock(0, 0.05, 0, 1, 0.5, 0.1);
        renderBlock(0, 0.05, 0.9, 1, 0.5, 1);
        renderBlock(0, 0.05, 0.1, 0.1, 0.5, 0.9);
        renderBlock(0.9, 0.05, 0.1, 1, 0.5, 0.9);

        setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.VAT);
        //Crossbars
        renderBlock(0.26, 0.05, 0.1, 0.32, 0.5, 0.9);
        renderBlock(0.47, 0.05, 0.1, 0.53, 0.5, 0.9);
        renderBlock(0.68, 0.05, 0.1, 0.74, 0.5, 0.9);
        renderBlock(0.1, 0.05, 0.26, 0.9, 0.5, 0.32);
        renderBlock(0.1, 0.05, 0.47, 0.9, 0.5, 0.53);
        renderBlock(0.1, 0.05, 0.68, 0.9, 0.5, 0.74);
        //renderBlock(0.6, 0.05, 0.4, 0.9, 0.5, 0.6);
    }
}

package joshie.mariculture.core.render;

import joshie.mariculture.core.Core;
import joshie.mariculture.core.handlers.CastingHandler;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.core.lib.MachineRenderedMultiMeta;
import joshie.mariculture.core.lib.MetalRates;
import joshie.mariculture.core.tile.TileBlockCaster;
import net.minecraft.item.ItemStack;

public class RenderBlockCaster extends RenderBase {
    public RenderBlockCaster() {}

    @Override
    public void renderBlock() {
        if (!isItem()) {
            TileBlockCaster tile = (TileBlockCaster) world.getTileEntity(x, y, z);
            ItemStack stack = tile.getStackInSlot(0);
            if (stack != null) {
                setTexture(CastingHandler.getTexture(stack));
                renderBlock(0.1, 0.05, 0.1, 0.9, 0.90, 0.9);
            }

            if (tile.getFluid() != null) {
                renderFluid(tile.getFluid(), MetalRates.BLOCK, 0.5D, 0, 0, 0);
            }
        }

        setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.VAT);
        renderBlock(0, 0, 0, 1, 0.05, 1);
        // Sides
        setTexture(Core.renderedMachines, MachineRenderedMeta.INGOT_CASTER);
        renderBlock(0, 0.05, 0, 1, 1, 0.1);
        renderBlock(0, 0.05, 0.9, 1, 1, 1);
        renderBlock(0, 0.05, 0.1, 0.1, 1, 0.9);
        renderBlock(0.9, 0.05, 0.1, 1, 1, 0.9);
    }
}
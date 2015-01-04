package maritech.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockRenderedMachineMulti;
import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.render.RenderBase;
import maritech.tile.TileAirCompressor;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class RenderCompressorBase extends RenderBase {
    public RenderCompressorBase() {}

    @Override
    public void renderBlock() {
        if (isItem()) {
            GL11.glTranslatef(0F, -0.1F, 0F);
        }

        TileAirCompressor tile = (TileAirCompressor) (isItem() ? null : world.getTileEntity(x, y, z));
        if (dir == ForgeDirection.UNKNOWN || isItem()) {
            setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_BASE);
            renderBlock(0, 0.2, 0, 1, 1, 1);
            setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
            renderBlock(0.15, 0, 0.15, 0.85, 0.2, 0.85);
        } else if (dir == ForgeDirection.SOUTH) {
            setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_BASE);
            renderBlock(0.05, 0.2, 0, 0.95, 1, 0.95);
            renderBlock(0.2, 0.15, 0, 0.8, 0.2, 0.7);
            renderBlock(0.1, 1, 0, 0.9, 1.05, 0.85);
            renderBlock(0.95, 0.25, 0, 0.99, 0.95, 0.9);
            renderBlock(0.01, 0.25, 0, 0.05, 0.95, 0.9);
            renderBlock(0.05, 0.25, 0.95, 0.95, 0.95, 1);
            setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
            renderBlock(0.15, 0, 0.7, 0.85, 0.2, 0.85);
        } else if (dir == ForgeDirection.NORTH) {
            setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_BASE);
            renderBlock(0.05, 0.2, 0.05, 0.95, 1, 1);
            renderBlock(0.2, 0.15, 0.3, 0.8, 0.2, 1);
            renderBlock(0.1, 1, 0.15, 0.9, 1.05, 1);
            renderBlock(0.95, 0.25, 0.1, 0.99, 0.95, 1);
            renderBlock(0.01, 0.25, 0.1, 0.05, 0.95, 1);
            renderBlock(0.05, 0.25, 0, 0.95, 0.95, 0.05);
            setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
            renderBlock(0.15, 0, 0.15, 0.85, 0.2, 0.3);

            //I am the master of the house! Let's render the bar
            if (tile.isMaster()) {
                double start = 0.25D + (1.5D - tile.getAirStored() * 1.5D / 480);
                if (start >= 1.75D) {
                    start = 1.7499D;
                }

                setTexture(((BlockRenderedMachineMulti) Core.renderedMachinesMulti).bar1);
                renderBlock(0.99, 0.4, start, 1, 0.8, 1.75);
                //Opposite
                start = 2D - (1.75D - (1.5D - tile.getAirStored() * 1.5D / 480));
                if (start <= 0.25D) {
                    start = 0.2501D;
                }
                setTexture(Core.metals, MetalMeta.MAGNESIUM_BLOCK);
                renderBlock(0.99, 0.4, 0.25, 1, 0.8, start);

                //Side renders
                setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
                renderBlock(0.99, 0.8, 0.2, 1, 0.85, 1.8);
                renderBlock(0.99, 0.35, 0.2, 1, 0.4, 1.8);
                renderBlock(0.99, 0.4, 1.75, 1, 0.8, 1.8);
                renderBlock(0.99, 0.4, 0.2, 1, 0.8, 0.25);

                start = 1.75D - (1.5D - tile.getAirStored() * 1.5D / 480);
                if (start <= 0.25D) {
                    start = 0.2501D;
                }

                //Side 2
                setTexture(((BlockRenderedMachineMulti) Core.renderedMachinesMulti).bar1);
                renderBlock(0, 0.4, 0.25, 0.0001, 0.8, start);

                //Opposite
                start = 2D - (0.25D + (1.5D - tile.getAirStored() * 1.5D / 480));
                if (start >= 1.75D) {
                    start = 1.7499D;
                }
                setTexture(Core.metals, MetalMeta.MAGNESIUM_BLOCK);
                renderBlock(0, 0.4, start, 0.0001, 0.8, 1.75);

                //Side Renders number 2 :)
                setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
                renderBlock(0, 0.8, 0.2, 0.01, 0.85, 1.8);
                renderBlock(0, 0.35, 0.2, 0.01, 0.4, 1.8);
                renderBlock(0, 0.4, 1.75, 0.01, 0.8, 1.8);
                renderBlock(0, 0.4, 0.2, 0.01, 0.8, 0.25);
            }
        } else if (dir == ForgeDirection.EAST) {
            setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_BASE);
            renderBlock(0.05, 0.2, 0.05, 1, 1, 0.95);
            renderBlock(0.3, 0.15, 0.2, 1, 0.2, 0.8);
            renderBlock(0.1, 1, 0.15, 1, 1.05, 0.85);
            renderBlock(0.1, 0.25, 0.01, 1, 0.95, 0.05);
            renderBlock(0.1, 0.25, 0.95, 1, 0.95, 0.99);
            renderBlock(0, 0.25, 0.05, 0.05, 0.95, 0.95);
            setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
            renderBlock(0.15, 0, 0.15, 0.3, 0.2, 0.85);

            if (tile.isMaster()) {
                double start = 0.25D + (1.5D - tile.getAirStored() * 1.5D / 480);
                if (start >= 1.75D) {
                    start = 1.7499D;
                }

                setTexture(((BlockRenderedMachineMulti) Core.renderedMachinesMulti).bar1);
                renderBlock(start, 0.4, 0, 1.75, 0.8, 0.0001);
                start = 2D - (1.75D - (1.5D - tile.getAirStored() * 1.5D / 480));
                if (start <= 0.25D) {
                    start = 0.2501D;
                }
                setTexture(Core.metals, MetalMeta.MAGNESIUM_BLOCK);
                renderBlock(0.25, 0.4, 0, start, 0.8, 0.0001);

                setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
                renderBlock(0.2, 0.8, 0.99, 1.8, 0.85, 1);
                renderBlock(0.2, 0.35, 0.99, 1.8, 0.4, 1);
                renderBlock(1.75, 0.4, 0.99, 1.8, 0.8, 1);
                renderBlock(0.2, 0.4, 0.99, 0.25, 0.8, 1);

                start = 1.75D - (1.5D - tile.getAirStored() * 1.5D / 480);
                if (start <= 0.25D) {
                    start = 0.2501D;
                }

                setTexture(((BlockRenderedMachineMulti) Core.renderedMachinesMulti).bar1);
                renderBlock(0.25, 0.4, 0.9999, start, 0.8, 1);

                start = 2D - (0.25D + (1.5D - tile.getAirStored() * 1.5D / 480));
                if (start >= 1.75D) {
                    start = 1.7499D;
                }
                setTexture(Core.metals, MetalMeta.MAGNESIUM_BLOCK);
                renderBlock(start, 0.4, 0.9999, 1.75, 0.8, 1);

                setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
                renderBlock(0.2, 0.8, 0, 1.8, 0.85, 0.0001);
                renderBlock(0.2, 0.35, 0, 1.8, 0.4, 0.0001);
                renderBlock(1.75, 0.4, 0, 1.8, 0.8, 0.0001);
                renderBlock(0.2, 0.4, 0, 0.25, 0.8, 0.0001);
            }

        } else if (dir == ForgeDirection.WEST) {
            setTexture(Core.renderedMachinesMulti, MachineRenderedMultiMeta.COMPRESSOR_BASE);
            renderBlock(0, 0.2, 0.05, 0.95, 1, 0.95);
            renderBlock(0.3, 0.15, 0.2, 1, 0.2, 0.8);
            renderBlock(0, 1, 0.15, 0.9, 1.05, 0.85);
            renderBlock(0, 0.25, 0.01, 0.9, 0.95, 0.05);
            renderBlock(0, 0.25, 0.95, 0.9, 0.95, 0.99);
            renderBlock(0.95, 0.25, 0.05, 1, 0.95, 0.95);
            setTexture(Core.metals, MetalMeta.TITANIUM_BLOCK);
            renderBlock(0.7, 0, 0.15, 0.85, 0.2, 0.85);
        }
    }
}

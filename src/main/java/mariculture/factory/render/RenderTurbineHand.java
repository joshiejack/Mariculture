package mariculture.factory.render;

import mariculture.core.Core;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.render.RenderBase;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderTurbineHand extends RenderBase {
    public RenderTurbineHand() {}

    @Override
    public void renderBlock() {
        if (!isItem()) {
            if (dir == ForgeDirection.NORTH) {

            } else if (dir == ForgeDirection.EAST) {

            } else if (dir == ForgeDirection.SOUTH) {

            } else if (dir == ForgeDirection.WEST) {

            } else if (dir == ForgeDirection.UP) {
                //Base
                setTexture(Core.woods, WoodMeta.BASE_WOOD);
                renderBlock(0, 0, 0, 1, 0.1, 1);
                setTexture(Core.woods, WoodMeta.BASE_WOOD);
                renderBlock(0.1, 0.1, 0.1, 0.9, 0.15, 0.9);
                //Pole
                setTexture(Core.metals, MetalMeta.COPPER_BLOCK);
                renderBlock(0.4, 0.15, 0.4, 0.6, 0.95, 0.6);
                //Top
                setTexture(Core.woods, WoodMeta.BASE_WOOD);
                renderBlock(0.25, 0.95, 0.25, 0.75, 1, 0.75);
            } else if (dir == ForgeDirection.DOWN) {
                
            }
        }
    }
}

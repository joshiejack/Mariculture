package mariculture.factory.render;

import mariculture.core.Core;
import mariculture.core.lib.MetalMeta;
import mariculture.core.render.RenderBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderTurbineWater extends RenderBase {
    public RenderTurbineWater() {}
    
    private IIcon getColor() {
        return Blocks.gold_block.getIcon(0, 0);
    }

    @Override
    public void renderBlock() {
        if (!isItem()) {
            if (dir == ForgeDirection.NORTH) {

            } else if (dir == ForgeDirection.EAST) {

            } else if (dir == ForgeDirection.SOUTH) {

            } else if (dir == ForgeDirection.WEST) {

            } else if (dir == ForgeDirection.UP) {
                //Base
                setTexture(Core.metals, MetalMeta.ALUMINUM_BLOCK);
                renderBlock(0, 0, 0, 1, 0.1, 1);
                setTexture(Core.metals, MetalMeta.ALUMINUM_BLOCK);
                renderBlock(0.1, 0.1, 0.1, 0.9, 0.15, 0.9);
                //COLOR!!!
                setTexture(Blocks.gold_block);
                renderBlock(0.35, 0.55, 0.35, 0.65, 0.95, 0.65);
                
                //Pole
                setTexture(Blocks.stone_slab);
                renderBlock(0.4, 0.15, 0.4, 0.6, 0.55, 0.6);
                //Top
                setTexture(Core.metals, MetalMeta.ALUMINUM_BLOCK);
                renderBlock(0.25, 0.95, 0.25, 0.75, 1, 0.75);
            } else if (dir == ForgeDirection.DOWN) {
                
            }
        }
    }
}

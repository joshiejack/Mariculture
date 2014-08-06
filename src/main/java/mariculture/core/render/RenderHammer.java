package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.lib.RockMeta;
import mariculture.core.tile.TileTankBlock;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidStack;

public class RenderHammer extends RenderBase {
    public RenderHammer() {}

    @Override
    public void renderBlock() {
        setTexture(Core.rocks, RockMeta.BASE_BRICK);
        renderBlock(0.3, 0.05, 0.3, 0.7, 0.7, 0.7);
        renderBlock(0.0, 0.05, 0.0, 0.2, 0.5, 0.2);
        renderBlock(0.8, 0.05, 0.8, 1.0, 0.5, 1.0);
        renderBlock(0.0, 0.05, 0.8, 0.2, 0.5, 1.0);
        renderBlock(0.8, 0.0, 0.0, 1.0, 0.5, 0.2);
        renderBlock(0.2, 0.9, 0.2, 0.8, 1.0, 0.8);
        
        //Blob
        renderBlock(0.025, 0.05, 0.4, 0.175, 0.4, 0.6);
        renderBlock(0.825, 0.05, 0.4, 0.975, 0.4, 0.6);
        renderBlock(0.4, 0.05, 0.025, 0.6, 0.4, 0.175);
        renderBlock(0.4, 0.05, 0.825, 0.6, 0.4, 0.975);
        
        setTexture(Blocks.nether_brick);
        renderBlock(0.25, 0.7, 0.25, 0.75, 0.9, 0.75);
        renderBlock(0.0, 0.0, 0.0, 1.0, 0.05, 1.0);
        
        //Bar
        renderBlock(0.075, 0.2, 0.2, 0.125, 0.3, 0.8);
        renderBlock(0.875, 0.2, 0.2, 0.925, 0.3, 0.8);
        
        renderBlock(0.2, 0.2, 0.075, 0.8, 0.3, 0.125);
        renderBlock(0.2, 0.2, 0.875, 0.8, 0.3, 0.925);
    }
}

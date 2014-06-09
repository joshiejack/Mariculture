package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.lib.RockMeta;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderAnvil extends RenderBase {

    public RenderAnvil() {}

    @Override
    public void renderBlock() {
        if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH || isItem()) {
            //Bottom
            setTexture(Core.rocks, RockMeta.BASE_BRICK);
            renderBlock(0.05, 0, 0.1, 0.95D, 0.2D, 0.9D);

            //Bottomish
            setTexture(Core.rocks, RockMeta.BASE_BRICK);
            renderBlock(0.15, 0.2, 0.2, 0.85D, 0.3D, 0.8D);

            //Middle
            setTexture(Blocks.nether_brick);
            renderBlock(0.25, 0.3, 0.3, 0.75D, 0.6D, 0.7D);
            setTexture(Blocks.nether_brick);
            renderBlock(0.05, 0.6, 0.2, 0.95D, 0.65D, 0.8D);

            //Secondary Head
            setTexture(Core.rocks, RockMeta.BASE_BRICK);
            renderBlock(0, 0.65, 0.15, 1D, 0.95D, 0.85D);

            //Head
            setTexture(Blocks.nether_brick);
            renderBlock(0.95, 0.95, 0.15, 1D, 1D, 0.85D);
            setTexture(Blocks.nether_brick);
            renderBlock(0D, 0.95, 0.15D, 0.05D, 1D, 0.85D);
            setTexture(Blocks.nether_brick);
            renderBlock(0.05D, 0.95D, 0.8D, 0.95D, 1D, 0.85D);
            setTexture(Blocks.nether_brick);
            renderBlock(0.05D, 0.95D, 0.15D, 0.95D, 1D, 0.2D);
            setTexture(Blocks.hopper);
            renderBlock(0.05D, 0.95D, 0.2D, 0.95D, 1D, 0.8D);
        } else {
            //Bottom
            setTexture(Core.rocks, RockMeta.BASE_BRICK);
            renderBlock(0.1, 0, 0.05, 0.9D, 0.2D, 0.95D);

            //Bottomish
            setTexture(Core.rocks, RockMeta.BASE_BRICK);
            renderBlock(0.2, 0.2, 0.15, 0.8D, 0.3D, 0.85D);

            //Middle
            setTexture(Blocks.nether_brick);
            renderBlock(0.3, 0.3, 0.25, 0.7D, 0.6D, 0.75D);
            setTexture(Blocks.nether_brick);
            renderBlock(0.2, 0.6, 0.05, 0.8D, 0.65D, 0.95D);

            //Secondary Head
            setTexture(Core.rocks, RockMeta.BASE_BRICK);
            renderBlock(0.15, 0.65, 0, 0.85D, 0.95D, 1D);

            //Head
            setTexture(Blocks.nether_brick);
            renderBlock(0.15, 0.95, 0.95, 0.85D, 1D, 1D);
            setTexture(Blocks.nether_brick);
            renderBlock(0.15, 0.95, 0, 0.85D, 1D, 0.05D);
            setTexture(Blocks.nether_brick);
            renderBlock(0.8, 0.95, 0.05, 0.85D, 1D, 0.95D);
            setTexture(Blocks.nether_brick);
            renderBlock(0.15, 0.95, 0.05, 0.2D, 1D, 0.95D);
            setTexture(Blocks.hopper);
            renderBlock(0.2, 0.95, 0.05, 0.8D, 1D, 0.95D);
        }
    }
}

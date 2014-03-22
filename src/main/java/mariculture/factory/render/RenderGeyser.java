package mariculture.factory.render;

import mariculture.core.render.RenderBase;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderGeyser extends RenderBase {
	public RenderGeyser(){}

	@Override
	public void renderBlock() {
		if(dir == ForgeDirection.NORTH && !isItem()) {
			setTexture(Blocks.hopper);
			renderBlock(0.1, 0.1, 0.85, 0.9, 0.9, 1);
			setTexture(Blocks.stone_slab);
			renderBlock(0.25, 0.25, 0.76, 0.75, 0.75, 0.85);
			setTexture(Blocks.water);
			renderBlock(0.35, 0.35, 0.75, 0.65, 0.65, 0.76);
		} else if(dir == ForgeDirection.SOUTH && !isItem()) {
			setTexture(Blocks.hopper);
			renderBlock(0.1, 0.1, 0, 0.9, 0.9, 0.15);
			setTexture(Blocks.stone_slab);
			renderBlock(0.25, 0.25, 0.15, 0.75, 0.75, 0.24);
			setTexture(Blocks.water);
			renderBlock(0.35, 0.35, 0.24, 0.65, 0.65, 0.25);
		} else if (dir == ForgeDirection.WEST && !isItem()) {
			setTexture(Blocks.hopper);
			renderBlock(0.85, 0.1, 0.1, 1, 0.9, 0.9);
			setTexture(Blocks.stone_slab);
			renderBlock(0.76, 0.25, 0.25, 0.85, 0.75, 0.75);
			setTexture(Blocks.water);
			renderBlock(0.75, 0.35, 0.35, 0.76, 0.65, 0.65);
		} else if(dir == ForgeDirection.EAST && !isItem()) {
			setTexture(Blocks.hopper);
			renderBlock(0, 0.1, 0.1, 0.15, 0.9, 0.9);
			setTexture(Blocks.stone_slab);
			renderBlock(0.15, 0.25, 0.25, 0.24, 0.75, 0.75);
			setTexture(Blocks.water);
			renderBlock(0.24, 0.35, 0.35, 0.25, 0.65, 0.65);
		} else if(dir == ForgeDirection.UP && !isItem()) {
			setTexture(Blocks.hopper);
			renderBlock(0.1, 0, 0.1, 0.9, 0.15, 0.9);
			setTexture(Blocks.stone_slab);
			renderBlock(0.25, 0.15, 0.25, 0.75, 0.24, 0.75);
			setTexture(Blocks.water);
			renderBlock(0.35, 0.24, 0.35, 0.65, 0.25, 0.65);
		} else if(dir == ForgeDirection.DOWN && !isItem()) {
			setTexture(Blocks.hopper);
			renderBlock(0.1, 0.85, 0.1, 0.9, 1, 0.9);
			setTexture(Blocks.stone_slab);
			renderBlock(0.25, 0.76, 0.25, 0.75, 0.85, 0.75);
			setTexture(Blocks.water);
			renderBlock(0.35, 0.75, 0.35, 0.65, 0.76, 0.65);
		} else {
			setTexture(Blocks.hopper);
			renderBlock(0D, 0D, 0D, 1D, 0.1D, 1D);
			setTexture(Blocks.stone_slab);
			renderBlock(0.15D, 0.1D, 0.15D, 0.85D, 0.15D, 0.85D);
			setTexture(Blocks.water);
			renderBlock(0.25D, 0.15D, 0.25D, 0.75D, 0.16D, 0.75D);
		}
	}
}

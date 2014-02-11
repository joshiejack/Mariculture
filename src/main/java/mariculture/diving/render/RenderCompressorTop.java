package mariculture.diving.render;

import mariculture.core.Core;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.render.RenderBase;
import mariculture.diving.TileAirCompressor;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class RenderCompressorTop extends RenderBase {
	public RenderCompressorTop(RenderBlocks render) {
		super(render);
	}

	@Override
	public void renderBlock() {
		if(isItem()) {
			GL11.glTranslatef(0.15F, 0.05F, -0.65F);
		}
		
		setTexture(Blocks.stone);
		TileAirCompressor tile = (TileAirCompressor) (isItem() ? null: world.getTileEntity(x, y, z));
		if(dir == ForgeDirection.UNKNOWN && !isItem()) {
			setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			renderBlock(0.05, 0, 0.05, 0.95, 0.15, 0.95);
		} else if (dir == ForgeDirection.EAST) {
			if(!isItem()) {
				if(tile.master != null) {
					TileEntity te = world.getTileEntity(tile.master.xCoord, tile.master.yCoord, tile.master.zCoord);
					if(te != null && te instanceof TileAirCompressor) {
						TileAirCompressor master = (TileAirCompressor) te;
						if(master.getEnergyStored(ForgeDirection.UP) > 0 && master.storedAir < TileAirCompressor.max)
							setTexture(Blocks.cloth, 5);
						renderBlock(0.1, 0.3, 0.35, 0.4, 0.55, 0.65);
					}
				}
			}
			
			setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			renderBlock(0.4, 0, 0.25, 1.5, 0.15, 0.75);
			setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
			renderBlock(0.2, 0, 0.45, 0.3, 0.3, 0.55);
			renderBlock(0.55, 0.15, 0.35, 0.85, 0.4, 0.65);
			renderBlock(0.6, 0.4, 0.4, 0.8, 0.45, 0.6);
			renderBlock(1, 0.15, 0.35, 1.3, 0.3, 0.65);
			renderBlock(1.05, 0.3, 0.4, 1.25, 0.5, 0.6);
			renderBlock(0.95, 0.5, 0.4, 1.35, 0.6, 0.6);
			renderBlock(1.05, 0.6, 0.45, 1.25, 0.7, 0.55);
			renderBlock(1.1, 0.45, 0.6, 1.2, 0.55, 0.7);
			renderBlock(1.7, 0, 0.35, 1.8, 0.2, 0.65);
			setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_TOP);
			renderBlock(0.8, 0.15, 0.7, 1.5, 0.85, 0.75);
		} else if (dir == ForgeDirection.NORTH || isItem()) {
			if(!isItem()) {
				if(tile.master != null) {
					TileEntity te = world.getTileEntity(tile.master.xCoord, tile.master.yCoord, tile.master.zCoord);
					if(te != null && te instanceof TileAirCompressor) {
						TileAirCompressor master = (TileAirCompressor) te;
						if(master.getEnergyStored(ForgeDirection.UP) > 0 && master.storedAir < TileAirCompressor.max)
							setTexture(Blocks.cloth, 5);
						renderBlock(0.35, 0.3, 0.1, 0.65, 0.55, 0.4);
					}
				}
			}
			
			setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			renderBlock(0.25, 0, 0.4, 0.75, 0.15, 1.5);
			setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
			renderBlock(0.45, 0, 0.2, 0.55, 0.3, 0.3);
			renderBlock(0.35, 0.15, 0.55, 0.65, 0.4, 0.85);
			renderBlock(0.4, 0.4, 0.6, 0.6, 0.45, 0.8);
			renderBlock(0.35, 0.15, 1, 0.65, 0.3, 1.3);
			renderBlock(0.4, 0.3, 1.05, 0.6, 0.5, 1.25);
			renderBlock(0.4, 0.5, 0.95, 0.6, 0.6, 1.35);
			renderBlock(0.45, 0.6, 1.05, 0.55, 0.7, 1.25);
			renderBlock(0.6, 0.45, 1.1, 0.7, 0.55, 1.2);
			renderBlock(0.35, 0, 1.7, 0.65, 0.2, 1.8);
			setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_TOP);
			renderBlock(0.7, 0.15, 0.8, 0.75, 0.85, 1.5);
		}
	}
}

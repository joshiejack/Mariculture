package mariculture.factory.render;

import mariculture.core.Core;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.render.RenderBase;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderPressureVessel extends RenderBase {
	public RenderPressureVessel(RenderBlocks render) {
		super(render);
	}
	
	public boolean doExtendVessel(TileEntity tile) {
		return tile instanceof TilePressureVessel;
	}
	
	private void renderSide(IBlockAccess world, int x, int y, int z, double yStart, double yEnd) {
		//Set Texture
		setTexture(Core.doubleBlock, DoubleMeta.PRESSURE_VESSEL);
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel))
			renderBlock(0, yStart, 0.045, 1, yEnd, 0.01);
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel))
			renderBlock(0, yStart, 0.955, 1, yEnd, 0.99);	
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel))
			renderBlock(0.01, yStart, 0, 0.045, yEnd, 1);
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel))
			renderBlock(0.955, yStart, 0, 0.99, yEnd, 1);
	}

	@Override
	public void renderBlock() {
		setTexture(Core.doubleBlock, DoubleMeta.PRESSURE_VESSEL);
		
		if(isItem()) {
			renderBlock(0.1, 0.2, 0.1, 0.9, 0.9, 0.9);
		} else {
			double xStart = (doExtendVessel(world.getBlockTileEntity(x - 1, y, z)))? 0: 0.05;
			double xEnd = (doExtendVessel(world.getBlockTileEntity(x + 1, y, z)))? 1: 0.95;
			double zStart = (doExtendVessel(world.getBlockTileEntity(x, y, z - 1)))? 0: 0.05;
			double zEnd = (doExtendVessel(world.getBlockTileEntity(x, y, z + 1)))? 1: 0.95;
			double yStart = (doExtendVessel(world.getBlockTileEntity(x, y - 1, z)))? 0: 0.2;
			double yEnd = (doExtendVessel(world.getBlockTileEntity(x, y + 1, z)))? 1: 0.95;
			
			renderBlock(xStart, yStart, zStart, xEnd, yEnd, zEnd);
			
			//Top Sides
			//Right hand side
			if(!(world.getBlockTileEntity(x, y + 1, z) instanceof TilePressureVessel))
				renderSide(world, x, y, z, 0.93, 0.96);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel))
				renderSide(world, x, y, z, 0.2, 0.24);
			if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel) &&
					!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel)) {
				//Side
				setTexture(Core.doubleBlock, DoubleMeta.PRESSURE_VESSEL);
				renderBlock(0.95, yStart, 0, 1, yEnd, 0.05);
				if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) && !world.isAirBlock(x, y - 1, z)) {
					setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
					renderBlock(0.6, 0, 0.1, 0.9, 0.2, 0.4);
				}
			}
					
			//Left hand side
			if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel) &&
					!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel)) {
				//Side
				setTexture(Core.doubleBlock, DoubleMeta.PRESSURE_VESSEL);
				renderBlock(0, yStart, 0.95, 0.05, yEnd, 1);
				if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
						!world.isAirBlock(x, y - 1, z)) {
					setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
					renderBlock(0.1, 0, 0.6, 0.4, 0.2, 0.9);
				}
			}
					
			//Top hand side
			if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel) &&
					!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel)) {
				//Side
				setTexture(Core.doubleBlock, DoubleMeta.PRESSURE_VESSEL);
				renderBlock(0, yStart, 0, 0.05, yEnd, 0.05);
				if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
						!world.isAirBlock(x, y - 1, z)) {
					setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
					renderBlock(0.1, 0, 0.1, 0.4, 0.2, 0.4);
				}
			}
					
			//Bottom hand side
			if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel) &&
					!(world.getBlockTileEntity(x , y, z + 1) instanceof TilePressureVessel)) {
				//Side
				setTexture(Core.doubleBlock, DoubleMeta.PRESSURE_VESSEL);
				renderBlock(0.95, yStart, 0.95, 1, yEnd, 1);
				if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
						!world.isAirBlock(x, y - 1, z)) {
					setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
					renderBlock(0.6, 0, 0.6, 0.9, 0.2, 0.9);
				}
			}
		}
	}
}

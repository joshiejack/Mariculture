package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockDouble;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.RenderIds;
import mariculture.diving.TileAirCompressor;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderDouble implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks render) {
		
	}
	
	private void renderCompressorTop(TileAirCompressor tile, IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block, ForgeDirection facing) {
		render.renderAllFaces = true;
		
		if(facing == ForgeDirection.UNKNOWN) {
			helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			helper.renderBlock(0, 0, 0, 1, 0.15, 1);
		} else if (facing == ForgeDirection.EAST) {
			if(tile.master != null) {
				TileEntity te = world.getBlockTileEntity(tile.master.xCoord, tile.master.yCoord, tile.master.zCoord);
				if(te != null && te instanceof TileAirCompressor) {
					TileAirCompressor master = (TileAirCompressor) te;
					if(master.getEnergyStored(ForgeDirection.UP) > 0 && master.storedAir < TileAirCompressor.max)
						helper.setTexture(Block.cloth, 5);
					helper.renderBlock(0.1, 0.3, 0.35, 0.4, 0.55, 0.65);
				}
			}
			
			//Base
			helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			helper.renderBlock(0.4, 0, 0.25, 1.5, 0.15, 0.75);
			
			helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
			//Render Indicator
			helper.renderBlock(0.2, 0, 0.45, 0.3, 0.3, 0.55);
			//Left Block Base
			helper.renderBlock(0.55, 0.15, 0.35, 0.85, 0.4, 0.65);
			//Left Block Top
			helper.renderBlock(0.6, 0.4, 0.4, 0.8, 0.45, 0.6);
			//Right Block Base
			helper.renderBlock(1, 0.15, 0.35, 1.3, 0.3, 0.65);
			//Right Block Top
			helper.renderBlock(1.05, 0.3, 0.4, 1.25, 0.5, 0.6);
			//Right Block Full Top
			helper.renderBlock(0.95, 0.5, 0.4, 1.35, 0.6, 0.6);
			//Right Block Full Top Head
			helper.renderBlock(1.05, 0.6, 0.45, 1.25, 0.7, 0.55);
			//Axel
			helper.renderBlock(1.1, 0.45, 0.6, 1.2, 0.55, 0.7);
			//Little Blob
			helper.renderBlock(1.7, 0, 0.35, 1.8, 0.2, 0.65);
			//Wheel
			helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_TOP);
			helper.renderBlock(0.8, 0.15, 0.7, 1.5, 0.85, 0.75);
		} else if (facing == ForgeDirection.SOUTH) {
			if(tile.master != null) {
				TileEntity te = world.getBlockTileEntity(tile.master.xCoord, tile.master.yCoord, tile.master.zCoord);
				if(te != null && te instanceof TileAirCompressor) {
					TileAirCompressor master = (TileAirCompressor) te;
					if(master.getEnergyStored(ForgeDirection.UP) > 0 && master.storedAir < TileAirCompressor.max)
						helper.setTexture(Block.cloth, 5);
					helper.renderBlock(0.1, 0.3, 0.35, 0.4, 0.55, 0.65);
				}
			}
			
			//Base
			helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			helper.renderBlock(0.4, 0, 0.25, 1.5, 0.15, 0.75);
			
			helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
			//Render Indicator
			helper.renderBlock(0.2, 0, 0.45, 0.3, 0.3, 0.55);
			//Left Block Base
			helper.renderBlock(0.55, 0.15, 0.35, 0.85, 0.4, 0.65);
			//Left Block Top
			helper.renderBlock(0.6, 0.4, 0.4, 0.8, 0.45, 0.6);
			//Right Block Base
			helper.renderBlock(1, 0.15, 0.35, 1.3, 0.3, 0.65);
			//Right Block Top
			helper.renderBlock(1.05, 0.3, 0.4, 1.25, 0.5, 0.6);
			//Right Block Full Top
			helper.renderBlock(0.95, 0.5, 0.4, 1.35, 0.6, 0.6);
			//Right Block Full Top Head
			helper.renderBlock(1.05, 0.6, 0.45, 1.25, 0.7, 0.55);
			//Axel
			helper.renderBlock(1.1, 0.45, 0.6, 1.2, 0.55, 0.7);
			//Little Blob
			helper.renderBlock(1.7, 0, 0.35, 1.8, 0.2, 0.65);
			//Wheel
			helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_TOP);
			helper.renderBlock(0.8, 0.15, 0.7, 1.5, 0.85, 0.75);
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false;
	}
	
	public class RenderHelper {
		RenderBlocks render;
		int x;
		int y;
		int z;
		
		public RenderHelper(RenderBlocks render, int x, int y, int z) {
			this.render = render;
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public void setTexture(Icon icon) {
			render.setOverrideBlockTexture(icon);
		}
		
		public void setTexture(Block block) {
			render.setOverrideBlockTexture(block.getIcon(0, 0));
		}
		
		public void setTexture(Block block, int meta) {
			render.setOverrideBlockTexture(block.getIcon(0, meta));
		}
		
		public void renderBlock(double x, double y, double z, double x2, double y2, double z2) {
			render.setRenderBounds(x, y, z, x2, y2, z2);
			render.renderStandardBlock(Block.stone, this.x, this.y, this.z);
		}
	}
	
	public RenderHelper helper;
	private void renderCompressorBase(TileAirCompressor tile, IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block, ForgeDirection facing) {
		render.renderAllFaces = true;
		if(facing == ForgeDirection.UNKNOWN) {
			helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
			helper.renderBlock(0, 0.2, 0, 1, 1, 1);
			helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			helper.renderBlock(0.15, 0, 0.15, 0.85, 0.2, 0.85);
		} else if (facing == ForgeDirection.SOUTH) {
			helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
			//Main
			helper.renderBlock(0.05, 0.2, 0, 0.95, 1, 0.95);
			//Bottom
			helper.renderBlock(0.2, 0.15, 0, 0.8, 0.2, 0.7);
			//Top
			helper.renderBlock(0.1, 1, 0, 0.9, 1.05, 0.85);
			//Side 1
			helper.renderBlock(0.95, 0.25, 0, 0.99, 0.95, 0.9);
			//Side 2
			helper.renderBlock(0.01, 0.25, 0, 0.05, 0.95, 0.9);
			//End
			helper.renderBlock(0.05, 0.25, 0.95, 0.95, 0.95, 1);
			//Leg
			helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			helper.renderBlock(0.15, 0, 0.7, 0.85, 0.2, 0.85);
		} else if (facing == ForgeDirection.NORTH) {
			helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
			//0.95, 0.2, 0, 0.05, 1, 
			//Main
			helper.renderBlock(0.05, 0.2, 0.05, 0.95, 1, 1);
			//Bottom
			helper.renderBlock(0.2, 0.15, 0.3, 0.8, 0.2, 1);
			//Top
			helper.renderBlock(0.1, 1, 0.15, 0.9, 1.05, 1);
			//Side 1
			helper.renderBlock(0.95, 0.25, 0.1, 0.99, 0.95, 1);
			//Side 2
			helper.renderBlock(0.01, 0.25, 0.1, 0.05, 0.95, 1);
			//End
			helper.renderBlock(0.05, 0.25, 0, 0.95, 0.95, 0.05);
			//Leg
			helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			helper.renderBlock(0.15, 0, 0.15, 0.85, 0.2, 0.3);
			
			//I am the master of the house! Let's render the bar
			if(tile.isMaster()) {
				double start = (0.25D + (1.5D - (tile.getAirStored() * 1.5D)/ 480));
				if(start >= 1.75D)
					start = 1.7499D;
				
				helper.setTexture(((BlockDouble)Core.doubleBlock).bar1);
				helper.renderBlock(0.99, 0.4, start, 1, 0.8, 1.75);
				//Opposite
				start = 2D - (1.75D - (1.5D - (tile.getAirStored() * 1.5D)/ 480));
				if(start <= 0.25D)
					start = 0.2501D;
				helper.setTexture(Core.oreBlocks, OresMeta.MAGNESIUM_BLOCK);
				helper.renderBlock(0.99, 0.4, 0.25, 1, 0.8, start);
				
				//Side renders
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.99, 0.8, 0.2, 1, 0.85, 1.8);
				helper.renderBlock(0.99, 0.35, 0.2, 1, 0.4, 1.8);
				helper.renderBlock(0.99, 0.4, 1.75, 1, 0.8, 1.8);
				helper.renderBlock(0.99, 0.4, 0.2, 1, 0.8, 0.25);
				
				
				start = (1.75D - (1.5D - (tile.getAirStored() * 1.5D)/ 480));
				if(start <= 0.25D)
					start = 0.2501D;
				
				//Side 2
				helper.setTexture(((BlockDouble)Core.doubleBlock).bar1);
				helper.renderBlock(0, 0.4, 0.25, 0.0001, 0.8, start);
				
				//Opposite
				start = 2D - (0.25D + (1.5D - (tile.getAirStored() * 1.5D)/ 480));
				if(start >= 1.75D)
					start = 1.7499D;
				helper.setTexture(Core.oreBlocks, OresMeta.MAGNESIUM_BLOCK);
				helper.renderBlock(0, 0.4, start, 0.0001, 0.8, 1.75);
				
				//Side Renders number 2 :)
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0, 0.8, 0.2, 0.01, 0.85, 1.8);
				helper.renderBlock(0, 0.35, 0.2, 0.01, 0.4, 1.8);
				helper.renderBlock(0, 0.4, 1.75, 0.01, 0.8, 1.8);
				helper.renderBlock(0, 0.4, 0.2, 0.01, 0.8, 0.25);
			}
		} else if(facing == ForgeDirection.EAST) {
			// l <> r 			| u <> d |			 b <> bf
			helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
			//Main
			helper.renderBlock(0.05, 0.2, 0.05, 1, 1, 0.95);
			//Bottom
			helper.renderBlock(0.3, 0.15, 0.2, 1, 0.2, 0.8);
			//Top
			helper.renderBlock(0.1, 1, 0.15, 1, 1.05, 0.85);
			//Side 1
			helper.renderBlock(0.1, 0.25, 0.01, 1, 0.95, 0.05);
			//Side 2
			helper.renderBlock(0.1, 0.25, 0.95, 1, 0.95, 0.99);
			//End
			helper.renderBlock(0, 0.25, 0.05, 0.05, 0.95, 0.95);
			//Leg
			helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
			helper.renderBlock(0.15, 0, 0.15, 0.3, 0.2, 0.85);
			
			//I am the master of the house! Let's render the bar
						
			if(tile.master != null) {
				//System.out.println(tile.master.xCoord);
			} else {
				//System.out.println(tile.master);
			}
			
			if(tile.isMaster()) {
				double start = (0.25D + (1.5D - (tile.getAirStored() * 1.5D)/ 480));
				if(start >= 1.75D)
					start = 1.7499D;
				
				helper.setTexture(((BlockDouble)Core.doubleBlock).bar1);
				helper.renderBlock(start, 0.4, 0, 1.75, 0.8, 0.0001);
				//Opposite
				start = 2D - (1.75D - (1.5D - (tile.getAirStored() * 1.5D)/ 480));
				if(start <= 0.25D)
					start = 0.2501D;
				helper.setTexture(Core.oreBlocks, OresMeta.MAGNESIUM_BLOCK);
				helper.renderBlock(0.25, 0.4, 0, start, 0.8, 0.0001);
				
				//Side renders
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.2, 0.8, 0.99, 1.8, 0.85, 1);
				helper.renderBlock(0.2, 0.35, 0.99, 1.8, 0.4, 1);
				helper.renderBlock(1.75, 0.4, 0.99, 1.8, 0.8, 1);
				helper.renderBlock(0.2, 0.4, 0.99, 0.25, 0.8, 1);
				
				start = (1.75D - (1.5D - (tile.getAirStored() * 1.5D)/ 480));
				if(start <= 0.25D)
					start = 0.2501D;
				
				//Side 2
				helper.setTexture(((BlockDouble)Core.doubleBlock).bar1);
				helper.renderBlock(0.25, 0.4, 0.9999, start, 0.8, 1);
				
				//Opposite
				start = 2D - (0.25D + (1.5D - (tile.getAirStored() * 1.5D)/ 480));
				if(start >= 1.75D)
					start = 1.7499D;
				helper.setTexture(Core.oreBlocks, OresMeta.MAGNESIUM_BLOCK);
				helper.renderBlock(start, 0.4, 0.9999, 1.75, 0.8, 1);
				
				//Side Renders number 2 :)
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.2, 0.8, 0, 1.8, 0.85, 0.0001);
				helper.renderBlock(0.2, 0.35, 0, 1.8, 0.4, 0.0001);
				helper.renderBlock(1.75, 0.4, 0, 1.8, 0.8, 0.0001);
				helper.renderBlock(0.2, 0.4, 0, 0.25, 0.8, 0.0001);
			}
			
		} else if(facing == ForgeDirection.WEST) {
			// l <> r 			| u <> d |			 b <> bf
				helper.setTexture(Core.doubleBlock, DoubleMeta.COMPRESSOR_BASE);
				//Main
				helper.renderBlock(0, 0.2, 0.05, 0.95, 1, 0.95);
				//Bottom
				helper.renderBlock(0.3, 0.15, 0.2, 1, 0.2, 0.8);
				//Top
				helper.renderBlock(0, 1, 0.15, 0.9, 1.05, 0.85);
				//Side 1
				helper.renderBlock(0, 0.25, 0.01, 0.9, 0.95, 0.05);
				//Side 2
				helper.renderBlock(0, 0.25, 0.95, 0.9, 0.95, 0.99);
				//End
				helper.renderBlock(0.95, 0.25, 0.05, 1, 0.95, 0.95);
				//Leg
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.7, 0, 0.15, 0.85, 0.2, 0.85);
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false;
	}
	
	
	private void renderAirCompressor(TileAirCompressor tile, IBlockAccess world, int x, int y, int z, RenderBlocks render, Block stone) {
		/*ForgeDirection facing = ForgeDirection.UNKNOWN;
		
		if(tile.master != null) {			
			String key = tile.xCoord + " ~ " + tile.yCoord + " ~ " + tile.zCoord;
			
			if(!Diving.facingList.containsKey(key)) {				
				TileAirCompressor master = (TileAirCompressor) world.getBlockTileEntity(tile.master.xCoord, tile.master.yCoord, tile.master.zCoord);
				if(tile.isMaster()) {
					facing = tile.master.facing;
					Diving.facingList.put(key, tile.master.facing);
				} else if(master != null) {
					for(MultiPart part: master.getSlaves()) {
						if(x == part.xCoord && y == tile.yCoord && z == tile.zCoord) {
							facing = part.facing;
							Diving.facingList.put(key, part.facing);
							break;
						}
					}
				}
			} else {
				facing = (ForgeDirection)Diving.facingList.get(key);
			}
		} */
		
		ForgeDirection facing = tile.facing;
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == DoubleMeta.COMPRESSOR_BASE) {
			renderCompressorBase(tile, world, x, y, z, render, stone, facing);
		} else {
			renderCompressorTop(tile, world, x, y, z, render, stone, facing);
		}
	}
	
	public boolean doExtendVessel(TileEntity tile) {
		return tile instanceof TilePressureVessel;
	}
	
	private void renderSide(IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block, double yStart, double yEnd) {
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0, yStart, 0.045, 1, yEnd, 0.01);
			render.renderStandardBlock(block, x, y, z);
		}
				
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0, yStart, 0.955, 1, yEnd, 0.99);
			render.renderStandardBlock(block, x, y, z);
		}
				
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0.01, yStart, 0, 0.045, yEnd, 1);
			render.renderStandardBlock(block, x, y, z);
		}
				
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0.955, yStart, 0, 0.99, yEnd, 1);
			render.renderStandardBlock(block, x, y, z);
		}
	}
	
	private void renderVessel(IBlockAccess world, int x, int y, int z, RenderBlocks render, Block block) {
		render.renderAllFaces = true;
		
		double xStart = (doExtendVessel(world.getBlockTileEntity(x - 1, y, z)))? 0: 0.05;
		double xEnd = (doExtendVessel(world.getBlockTileEntity(x + 1, y, z)))? 1: 0.95;
		double zStart = (doExtendVessel(world.getBlockTileEntity(x, y, z - 1)))? 0: 0.05;
		double zEnd = (doExtendVessel(world.getBlockTileEntity(x, y, z + 1)))? 1: 0.95;
		double yStart = (doExtendVessel(world.getBlockTileEntity(x, y - 1, z)))? 0: 0.2;
		double yEnd = (doExtendVessel(world.getBlockTileEntity(x, y + 1, z)))? 1: 0.95;
		
		render.setOverrideBlockTexture(Core.doubleBlock.getBlockTexture(world, x, y, z, 0));
		render.setRenderBounds(xStart, yStart, zStart, xEnd, yEnd, zEnd);
		render.renderStandardBlock(Block.stone, x, y, z);
		
		//Top Sides
		//Right hand side
		if(!(world.getBlockTileEntity(x, y + 1, z) instanceof TilePressureVessel)) {
			renderSide(world, x, y, z, render, block, 0.93, 0.96);
		}
		
		if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel)) {
			renderSide(world, x, y, z, render, block, 0.2, 0.24);
		}
		
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0.95, yStart, 0, 1, yEnd, 0.05);
			render.renderStandardBlock(block, x, y, z);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
				render.setRenderBounds(0.6, 0, 0.1, 0.9, 0.2, 0.4);
				render.renderStandardBlock(block, x, y, z);
			}
		}
				
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0, yStart, 0.95, 0.05, yEnd, 1);
			render.renderStandardBlock(block, x, y, z);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
				render.setRenderBounds(0.1, 0, 0.6, 0.4, 0.2, 0.9);
				render.renderStandardBlock(block, x, y, z);
			}
		}
				
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0, yStart, 0, 0.05, yEnd, 0.05);
			render.renderStandardBlock(block, x, y, z);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
				render.setRenderBounds(0.1, 0, 0.1, 0.4, 0.2, 0.4);
				render.renderStandardBlock(block, x, y, z);
			}
		}
				
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x , y, z + 1) instanceof TilePressureVessel)) {
			//Side
			render.setOverrideBlockTexture(Block.blockLapis.getIcon(0, 0));
			render.setRenderBounds(0.95, yStart, 0.95, 1, yEnd, 1);
			render.renderStandardBlock(block, x, y, z);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				render.setOverrideBlockTexture(Core.oreBlocks.getIcon(0, OresMeta.TITANIUM_BLOCK));
				render.setRenderBounds(0.6, 0, 0.6, 0.9, 0.2, 0.9);
				render.renderStandardBlock(block, x, y, z);
			}
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		helper = new RenderHelper(render, x, y, z);
		TileEntity tile = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileAirCompressor) {
			renderAirCompressor((TileAirCompressor)tile, world, x, y, z, render, block.stone);
		} else if (tile instanceof TilePressureVessel) {
			renderVessel(world, x, y, z, render, block.stone);
		}
		
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return RenderIds.BLOCK_DOUBLE;
	}
}
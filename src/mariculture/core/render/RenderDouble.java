package mariculture.core.render;

import mariculture.core.Core;
import mariculture.core.blocks.BlockDouble;
import mariculture.core.blocks.TileVat;
import mariculture.core.helpers.RenderHelper;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.RenderIds;
import mariculture.diving.TileAirCompressor;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderDouble implements ISimpleBlockRenderingHandler {
	public RenderHelper helper;
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks render) {
		
	}
	
	private void renderCompressorTop(IBlockAccess world, TileAirCompressor tile, ForgeDirection facing) {		
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
	}

	private void renderCompressorBase(IBlockAccess world, TileAirCompressor tile, ForgeDirection facing) {
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
	}
	
	
	private void renderAirCompressor(TileAirCompressor tile, IBlockAccess world, int x, int y, int z) {
		ForgeDirection facing = tile.facing;
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == DoubleMeta.COMPRESSOR_BASE) {
			renderCompressorBase(world, tile, facing);
		} else {
			renderCompressorTop(world, tile, facing);
		}
	}
	
	public boolean doExtendVessel(TileEntity tile) {
		return tile instanceof TilePressureVessel;
	}
	
	private void renderSide(IBlockAccess world, int x, int y, int z, double yStart, double yEnd) {
		//Set Texture
		helper.setTexture(Block.blockLapis);
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel))
			helper.renderBlock(0, yStart, 0.045, 1, yEnd, 0.01);
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel))
			helper.renderBlock(0, yStart, 0.955, 1, yEnd, 0.99);	
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel))
			helper.renderBlock(0.01, yStart, 0, 0.045, yEnd, 1);
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel))
			helper.renderBlock(0.955, yStart, 0, 0.99, yEnd, 1);
	}
	
	private void renderVessel(IBlockAccess world, int x, int y, int z) {
		double xStart = (doExtendVessel(world.getBlockTileEntity(x - 1, y, z)))? 0: 0.05;
		double xEnd = (doExtendVessel(world.getBlockTileEntity(x + 1, y, z)))? 1: 0.95;
		double zStart = (doExtendVessel(world.getBlockTileEntity(x, y, z - 1)))? 0: 0.05;
		double zEnd = (doExtendVessel(world.getBlockTileEntity(x, y, z + 1)))? 1: 0.95;
		double yStart = (doExtendVessel(world.getBlockTileEntity(x, y - 1, z)))? 0: 0.2;
		double yEnd = (doExtendVessel(world.getBlockTileEntity(x, y + 1, z)))? 1: 0.95;
		
		helper.setTexture(Core.doubleBlock.getBlockTexture(world, x, y, z, 0));
		helper.renderBlock(xStart, yStart, zStart, xEnd, yEnd, zEnd);
		
		//Top Sides
		//Right hand side
		if(!(world.getBlockTileEntity(x, y + 1, z) instanceof TilePressureVessel))
			renderSide(world, x, y, z, 0.93, 0.96);
		if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel))
			renderSide(world, x, y, z, 0.2, 0.24);
		if(!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel)) {
			//Side
			helper.setTexture(Block.blockLapis);
			helper.renderBlock(0.95, yStart, 0, 1, yEnd, 0.05);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) && !world.isAirBlock(x, y - 1, z)) {
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.6, 0, 0.1, 0.9, 0.2, 0.4);
			}
		}
				
		//Left hand side
		if(!(world.getBlockTileEntity(x, y, z + 1) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel)) {
			//Side
			helper.setTexture(Block.blockLapis);
			helper.renderBlock(0, yStart, 0.95, 0.05, yEnd, 1);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.1, 0, 0.6, 0.4, 0.2, 0.9);
			}
		}
				
		//Top hand side
		if(!(world.getBlockTileEntity(x - 1, y, z) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x, y, z - 1) instanceof TilePressureVessel)) {
			//Side
			helper.setTexture(Block.blockLapis);
			helper.renderBlock(0, yStart, 0, 0.05, yEnd, 0.05);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.1, 0, 0.1, 0.4, 0.2, 0.4);
			}
		}
				
		//Bottom hand side
		if(!(world.getBlockTileEntity(x + 1, y, z) instanceof TilePressureVessel) &&
				!(world.getBlockTileEntity(x , y, z + 1) instanceof TilePressureVessel)) {
			//Side
			helper.setTexture(Block.blockLapis);
			helper.renderBlock(0.95, yStart, 0.95, 1, yEnd, 1);
			if(!(world.getBlockTileEntity(x, y - 1, z) instanceof TilePressureVessel) &&
					!world.isAirBlock(x, y - 1, z)) {
				helper.setTexture(Core.oreBlocks, OresMeta.TITANIUM_BLOCK);
				helper.renderBlock(0.6, 0, 0.6, 0.9, 0.2, 0.9);
			}
		}
	}
	
	public void renderVat(IBlockAccess world, TileVat tile, int x, int y, int z) {
		//Let's draw a single VAT!!!
		if(tile.facing == ForgeDirection.UNKNOWN) {
			//Edge Inner - Edge Outer
			helper.setTexture(Block.anvil);
			helper.renderBlock(0, 0.3, 0, 0.1, 0.9, 1);
			helper.renderBlock(-0.1, 0.35, 0.1, 0, 0.85, 0.9);
			helper.renderBlock(0.9, 0.3, 0, 1, 0.9, 1);
			helper.renderBlock(1, 0.35, 0.1, 1.1, 0.85, 0.9);
			helper.renderBlock(0.1, 0.3, 0, 0.9, 0.9, 0.1);
			helper.renderBlock(0.1, 0.35, -0.1, 0.9, 0.85, 0);
			helper.renderBlock(0.1, 0.3, 0.9, 0.9, 0.9, 1);
			helper.renderBlock(0.1, 0.35, 1, 0.9, 0.85, 1.1);
			//Legs
			helper.renderBlock(0.1, 0, 0.1, 0.2, 0.3, 0.2);
			helper.renderBlock(0.8, 0, 0.1, 0.9, 0.3, 0.2);
			helper.renderBlock(0.1, 0, 0.8, 0.2, 0.3, 0.9);
			helper.renderBlock(0.8, 0, 0.8, 0.9, 0.3, 0.9);
			//Bottom
			helper.renderBlock(0.2, 0.2, 0.2, 0.8, 0.3, 0.8);
			helper.renderBlock(0.1, 0.3, 0.1, 0.9, 0.4, 0.9);
			//Liquid
			if(tile.getFluid() != null) {
				helper.renderWorldBlock(tile.getFluid(), TileVat.max_sml, 0.46D, 0, 0, 0);
			}
		} else if (tile.facing == ForgeDirection.NORTH) {
			//Edge Inner - Edge Outer
			helper.setTexture(Block.anvil);
			helper.renderBlock(0.9, 0.3, 0, 1, 1, 1);
			helper.renderBlock(1, 0.35, 0, 1.1, 0.95, 0.9);
			helper.renderBlock(0, 0.3, 0.9, 0.9, 1, 1);
			helper.renderBlock(0, 0.35, 1, 0.9, 0.95, 1.1);
			//Legs
			helper.renderBlock(0.8, 0, 0.8, 0.9, 0.3, 0.9);
			//Bottom
			helper.renderBlock(0, 0.2, 0, 0.8, 0.3, 0.8);
			helper.renderBlock(0, 0.3, 0, 0.9, 0.4, 0.9);
		} else if(tile.facing == ForgeDirection.EAST) {
			helper.setTexture(Block.anvil);
			helper.renderBlock(0, 0.3, 0, 0.1, 1, 1);
			helper.renderBlock(-0.1, 0.35, 0, 0, 0.95, 0.9);
			helper.renderBlock(0.1, 0.3, 0.9, 1, 1, 1);
			helper.renderBlock(0.1, 0.35, 1, 1, 0.95, 1.1);
			//Legs
			helper.renderBlock(0.1, 0, 0.8, 0.2, 0.3, 0.9);
			//Bottom
			helper.renderBlock(0.2, 0.2, 0, 1, 0.3, 0.8);
			helper.renderBlock(0.1, 0.3, 0, 1, 0.4, 0.9);
		} else if(tile.facing == ForgeDirection.SOUTH) {
			helper.setTexture(Block.anvil);
			helper.renderBlock(0, 0.3, 0, 0.1, 1, 1);
			helper.renderBlock(-0.1, 0.35, 0.1, 0, 0.95, 1);
			helper.renderBlock(0.1, 0.3, 0, 1, 1, 0.1);
			helper.renderBlock(0.1, 0.35, -0.1, 1, 0.95, 0);
			//Legs
			helper.renderBlock(0.1, 0, 0.1, 0.2, 0.3, 0.2);
			//Bottom
			helper.renderBlock(0.2, 0.2, 0.2, 1, 0.3, 1);
			helper.renderBlock(0.1, 0.3, 0.1, 1, 0.4, 1);
			//Liquid
			if(tile.getFluid() != null) {
				helper.renderWorldBlock(tile.getFluid(), TileVat.max_lrg, 0.56D, 0, 0, 0);
				helper.renderWorldBlock(tile.getFluid(), TileVat.max_lrg, 0.56D, +1, 0, 0);
				helper.renderWorldBlock(tile.getFluid(), TileVat.max_lrg, 0.56D, +1, 0, +1);
				helper.renderWorldBlock(tile.getFluid(), TileVat.max_lrg, 0.56D, 0, 0, +1);
			}
		} else if(tile.facing == ForgeDirection.WEST) {
			helper.setTexture(Block.anvil);
			helper.renderBlock(0.9, 0.3, 0, 1, 1, 1);
			helper.renderBlock(1, 0.35, 0.1, 1.1, 0.95, 1);
			helper.renderBlock(0, 0.3, 0, 0.9, 1, 0.1);
			helper.renderBlock(0, 0.35, -0.1, 0.9, 0.95, 0);
			//Legs
			helper.renderBlock(0.8, 0, 0.1, 0.9, 0.3, 0.2);
			//Bottom
			helper.renderBlock(0, 0.2, 0.2, 0.8, 0.3, 1);
			helper.renderBlock(0, 0.3, 0.1, 0.9, 0.4, 1);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
		helper = new RenderHelper(render, world, x, y, z);
		
		render.renderAllFaces = true;
		TileEntity tile = (TileEntity) world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileAirCompressor) {
			renderAirCompressor((TileAirCompressor)tile, world, x, y, z);
		} else if (tile instanceof TilePressureVessel) {
			renderVessel(world, x, y, z);
		} else if (tile instanceof TileVat) {
			renderVat(world, (TileVat)tile, x, y, z);
		}
		
		render.clearOverrideBlockTexture();
		render.renderAllFaces = false;
		
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
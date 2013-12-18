package mariculture.core.blocks;

import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileDoubleBlock extends TileEntity {
	public boolean isFormed() {
		return this.getMasterBlock() != null && this.getOtherBlock() != null;
	}
	
	public TileDoubleBlock master() {
		return (TileDoubleBlock) this.getMasterBlock();
	}
	
	public TileEntity getMasterBlock() {
		World world = this.worldObj;
		int id = this.getBlockType().blockID;
		int meta = this.getBlockMetadata();
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
	
		if (world.getBlockTileEntity(x + 1, y, z) != null 
				&& world.getBlockId(x + 1, y, z) == id && world.getBlockMetadata(x + 1, y, z) == meta) {
			return world.getBlockTileEntity(x, y, z);
		}
	
		if (world.getBlockTileEntity(x - 1, y, z) != null 
				&& world.getBlockId(x - 1, y, z) == id && world.getBlockMetadata(x - 1, y, z) == meta) {
			return world.getBlockTileEntity(x - 1, y, z);
		}
	
		if (world.getBlockTileEntity(x, y, z + 1) != null 
				&& world.getBlockId(x, y, z + 1) == id && world.getBlockMetadata(x, y, z + 1) == meta) {
			return world.getBlockTileEntity(x, y, z);
		}
	
		if (world.getBlockTileEntity(x, y, z - 1) != null 
				&& world.getBlockId(x, y, z - 1) == id && world.getBlockMetadata(x, y, z - 1) == meta) {
			return world.getBlockTileEntity(x, y, z - 1);
		}

		return null;
	}

	public TileEntity getOtherBlock() {
		World world = this.worldObj;
		int id = this.getBlockType().blockID;
		int meta = this.getBlockMetadata();
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
	
		if (world.getBlockTileEntity(x + 1, y, z) != null 
				&& world.getBlockId(x + 1, y, z) == id && world.getBlockMetadata(x + 1, y, z) == meta) {
			return world.getBlockTileEntity(x + 1, y, z);
		}
	
		if (world.getBlockTileEntity(x - 1, y, z) != null 
				&& world.getBlockId(x - 1, y, z) == id && world.getBlockMetadata(x - 1, y, z) == meta) {
			return world.getBlockTileEntity(x, y, z);
		}
	
		if (world.getBlockTileEntity(x, y, z + 1) != null 
				&& world.getBlockId(x, y, z + 1) == id && world.getBlockMetadata(x, y, z + 1) == meta) {
			return world.getBlockTileEntity(x, y, z + 1);
		}
	
		if (world.getBlockTileEntity(x, y, z - 1) != null 
				&& world.getBlockId(x, y, z - 1) == id && world.getBlockMetadata(x, y, z - 1) == meta) {
			return world.getBlockTileEntity(x, y, z);
		}

		return null;
	}
}

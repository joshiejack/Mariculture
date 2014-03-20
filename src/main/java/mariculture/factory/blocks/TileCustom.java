package mariculture.factory.blocks;

import mariculture.core.network.Packets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileCustom extends TileEntity {
	private float hardness, resist;
	private Block[] theBlocks = new Block[6];
	private int[] theBlockMetas = new int[6];
	private int[] theSides = new int[6];
	private String name = "CustomTile";
	
	public int size() {
		return theBlocks.length;
	}
	
	public String name() {
		return name;
	}
	
	public Block[] theBlocks() {
		return theBlocks;
	}
	
	public int[] theBlockMetas() {
		return theBlockMetas;
	}
	public int[] theBlockSides() {
		return theSides;
	}

	public Block theBlocks(int i) {
		return theBlocks[i] != null? theBlocks[i]: Blocks.stone;
	}
	
	public int theBlockMetas(int i) {
		return theBlockMetas.length > i? theBlockMetas[i]: 0;
	}
	
	public int theBlockSides(int i) {
		return theSides.length > i? theSides[i]: 0;
	}
	
	public float getHardness() {
		return hardness;
	}
	
	public float getResistance() {
		return resist;
	}
	
	@Override
	public boolean canUpdate() {
		return false;
    }

	public void readData(NBTTagCompound nbt) {
		for(int i = 0; i < 6; i++) {
			this.theBlocks[i] = (Block) Block.blockRegistry.getObject(nbt.getString("BlockIdentifier" + i));
		}
		
		this.resist = nbt.getFloat("BlockResistance");
		this.hardness = nbt.getFloat("BlockHardness");
		this.theBlockMetas = nbt.getIntArray("BlockMetas");
		this.theSides = nbt.getIntArray("BlockSides");
		this.name = nbt.getString("Name");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readData(nbt);
	}
	
	public void writeData(NBTTagCompound nbt) {
		for(int i = 0; i < 6; i++) {
			nbt.setString("BlockIdentifier" + i, Block.blockRegistry.getNameForObject(this.theBlocks[i]));
		}
			
		nbt.setFloat("BlockResistance", this.resist);
		nbt.setFloat("BlockHardness", this.hardness);
		nbt.setIntArray("BlockMetas", this.theBlockMetas);
		nbt.setIntArray("BlockSides", this.theSides);
		nbt.setString("Name", this.name);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeData(nbt);
	}

	@Override
	public Packet getDescriptionPacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
	
	public void set(String[] blockNames, int[] metas, int[] sides, String name) {
		Block[] blocks = new Block[6];
		for(int i = 0; i < 6; i++) {
			blocks[i] = (Block) Block.blockRegistry.getObject(blockNames[i]);
		}
		
		set(blocks, metas, sides, name);
	}
	
	public void updateHardness() {
		hardness = 0F;
		for(int i = 0; i < 6; i++) {
			hardness += theBlocks[i].getBlockHardness(worldObj, xCoord, yCoord, zCoord);
		}
		
		hardness /= 6;
	}
	
	public void updateResistance() {
		resist = 0F;
		for(int i = 0; i < 6; i++) {
			resist += theBlocks[i].getExplosionResistance(null, worldObj, xCoord, yCoord, zCoord, 0, 0, 0);
		}
		
		resist /= 6;
	}
	
	public void set(Block[] blocks, int[] metas, int[] sides, String name) {
		this.theBlocks = blocks;
		this.theBlockMetas = metas;
		this.theSides = sides;
		this.name = name;
		updateHardness();
		updateResistance();
		updateRender();
	}
	
	public boolean setSide(int side, Block block, int meta, int sideTexture) {
		boolean ret = false;
		if(size() == 6) {
			if(theBlocks[side] != block || theBlockMetas[side] != meta || theSides[side] != sideTexture) {
				ret = true;
			}
			
			theBlocks[side] = block;
			theBlockMetas[side] = meta;
			theSides[side] = sideTexture;
			updateHardness();
			updateResistance();
			updateRender();
		}
		
		return ret;
	}

	public void updateRender() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		if (!worldObj.isRemote) {
			Packets.updateRender(this);
		}
	}
}

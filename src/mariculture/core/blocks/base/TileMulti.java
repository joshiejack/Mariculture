package mariculture.core.blocks.base;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileMulti extends TileEntity {
	protected boolean shouldTick = true;
	public Master mstr;
	protected int tick;
	
	public TileMulti() {
		mstr = new Master();
	}
	
	public class Master {
		public boolean built;
		public int x, y, z;
		
		public void readFromNBT(NBTTagCompound nbt) {
			this.built = nbt.getBoolean("BlockBuilt");
			this.x = nbt.getInteger("MasterX");
			this.y = nbt.getInteger("MasterY");
			this.z = nbt.getInteger("MasterZ");
			
			cache = new ArrayList<Cached>();
			NBTTagList tagList = nbt.getTagList("Cache");

			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
				cache.add(new Cached(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z")));
			}
		}
		
		public void writeToNBT(NBTTagCompound nbt) {
			nbt.setBoolean("BlockBuilt", built);
			nbt.setInteger("MasterX", x);
			nbt.setInteger("MasterY", y);
			nbt.setInteger("MasterZ", z);
			
			NBTTagList itemList = new NBTTagList();

			for (Cached cach: cache) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("X", cach.x);
				tag.setInteger("Y", cach.y);
				tag.setInteger("Z", cach.z);
				itemList.appendTag(tag);
			}
			
			nbt.setTag("Cache", itemList);
		}

		public boolean set(boolean built, int x, int y, int z) {
			this.built = built;
			this.x = x;
			this.y = y;
			this.z = z;
			return built;
		}
	}
	
	protected ArrayList<Cached> cache = new ArrayList<Cached>();
	
	public static class Cached {
		public int x;
		public int y;
		public int z;
		
		public Cached(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	public boolean isComponent(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == this.getBlockType().blockID 
				&& worldObj.getBlockMetadata(x, y, z) == this.getBlockMetadata();
	}
	
	public boolean isBuilt() {
		return this.mstr.built;
	}
	
	public boolean isMaster() {
		return this.xCoord == mstr.x && this.yCoord == mstr.y && this.zCoord == mstr.z;
	}
	
	public boolean setMaster() {
		cache = new ArrayList<Cached>();
		
		World world = worldObj;
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		
		if(mstr.built && !(x == mstr.x && y == mstr.y && z == mstr.z)) {
			if(world.getBlockTileEntity(mstr.x, mstr.y, mstr.z) != null) {
				((TileMulti) world.getBlockTileEntity(mstr.x, mstr.y, mstr.z)).setMaster();
			}
		}
		
		if(isComponent(x, y + 1, z) && !isComponent(x, y - 1, z) && !isComponent(x, y + 2, z)) {
			cache.add(new Cached(x, y + 1, z));
			cache.add(new Cached(x, y, z));
			return mstr.set(true, x, y, z);
		}
		
		if(isComponent(x, y - 1, z) && !isComponent(x, y + 1, z) && !isComponent(x, y - 2, z)) {
			cache.add(new Cached(x, y, z));
			cache.add(new Cached(x, y - 1, z));
			return mstr.set(true, x, y - 1, z);
		}
		
		return mstr.set(false, xCoord, yCoord, zCoord);
	}
	
	public void updateMaster() {
		
	}
	
	public void updateAll() {
		
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		mstr.readFromNBT(tagCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		mstr.writeToNBT(tagCompound);
	}
	
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(isBuilt()) {
				if(isMaster()) {
					updateMaster();
				}
				
				updateAll();
			}
			
			tick++;
		}
	}
	
	@Override
	public boolean canUpdate() {
		return shouldTick;
    }

	public ArrayList<Cached> getCache() {
		return this.cache;
	}
}

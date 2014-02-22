package mariculture.core.network.old;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mariculture.fishery.blocks.TileFishTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Packet121FishTankSync extends PacketMariculture {
	public NBTTagCompound nbt;
	
	public Packet121FishTankSync() { }
	public Packet121FishTankSync(int xCoord, int yCoord, int zCoord, HashMap fish) {		
		nbt = new NBTTagCompound();
		nbt.setInteger("x", xCoord);
		nbt.setInteger("y", yCoord);
		nbt.setInteger("z", zCoord);
		
		NBTTagList itemList = new NBTTagList();
		Iterator it = fish.entrySet().iterator();
		while (it.hasNext()) {
			NBTTagCompound tag = new NBTTagCompound();
			Map.Entry pairs = (Map.Entry) it.next();
			tag.setInteger("Key", (Integer) pairs.getKey());
			ItemStack stack = (ItemStack) pairs.getValue();
			if(stack != null) {
				stack.writeToNBT(tag);
			}
			
			itemList.appendTag(tag);
		}

		nbt.setTag("FishList", itemList);
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		int x = nbt.getInteger("x");
		int y = nbt.getInteger("y");
		int z = nbt.getInteger("z");
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileFishTank) {
			TileFishTank tank = (TileFishTank) tile;
			NBTTagList tagList = nbt.getTagList("FishList", 10);
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
				ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
				tank.fish.put(tag.getInteger("Key"), stack);
			}
		}
		
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		nbt = CompressedStreamTools.read(is);
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		CompressedStreamTools.write(nbt, os);
	}
}

package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.blocks.TileAnvil;
import mariculture.core.blocks.TileIngotCaster;
import mariculture.core.blocks.TileVat;
import mariculture.fishery.blocks.TileFishTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Packet120ItemSync extends PacketMariculture {

	public NBTTagCompound nbt;
	
	public Packet120ItemSync() { }
	public Packet120ItemSync(int xCoord, int yCoord, int zCoord, ItemStack[] inventory) {		
		nbt = new NBTTagCompound();
		nbt.setInteger("x", xCoord);
		nbt.setInteger("y", yCoord);
		nbt.setInteger("z", zCoord);
		nbt.setInteger("length", inventory.length);
		
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				tag.setBoolean("NULLItemStack", false);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			} else {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				tag.setBoolean("NULLItemStack", true);
				itemList.appendTag(tag);
			}
		}
		
		nbt.setTag("Inventory", itemList);
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		int x = nbt.getInteger("x");
		int y = nbt.getInteger("y");
		int z = nbt.getInteger("z");
		int length = nbt.getInteger("length");
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if(tile instanceof TileVat || tile instanceof TileAnvil || tile instanceof TileIngotCaster) {
			ItemStack[] inventory = new ItemStack[length];
			IInventory block = (IInventory) world.getBlockTileEntity(x, y, z);
			NBTTagList tagList = nbt.getTagList("Inventory");
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
				byte slot = tag.getByte("Slot");
				if(tag.getBoolean("NULLItemStack") == true) {
					block.setInventorySlotContents(slot, null);
				} else if (slot >= 0 && slot < inventory.length) {
					block.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tag));
				}
			}
		}
		
		world.markBlockForRenderUpdate(x, y, z);
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

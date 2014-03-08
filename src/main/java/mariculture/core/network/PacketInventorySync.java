package mariculture.core.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;

public class PacketInventorySync extends PacketNBT {
	public PacketInventorySync(){}
	public PacketInventorySync(int x, int y, int z, ItemStack[] inventory) {
		nbt = new NBTTagCompound();
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
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
	public void handle(Side side, EntityPlayer player) {
		World world = player.worldObj;
		int x = nbt.getInteger("x");
		int y = nbt.getInteger("y");
		int z = nbt.getInteger("z");
		int length = nbt.getInteger("length");
		
		TileEntity tile = world.getTileEntity(x, y, z);
		ItemStack[] inventory = new ItemStack[length];
		IInventory block = (IInventory) world.getTileEntity(x, y, z);
		NBTTagList tagList = nbt.getTagList("Inventory", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if(tag.getBoolean("NULLItemStack") == true) {
				block.setInventorySlotContents(slot, null);
			} else if (slot >= 0 && slot < inventory.length) {
				block.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tag));
			}
		}
		
		world.markBlockForUpdate(x, y, z);
	}
}

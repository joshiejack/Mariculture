package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;

public abstract class PacketNBT implements IMessage, IMessageHandler<PacketNBT, IMessage> {
	public NBTTagCompound nbt;
	
	public PacketNBT() {}
	public PacketNBT(ItemStack[] inventory) {
		nbt = new NBTTagCompound();
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
	public void toBytes(ByteBuf buffer) {	
		try {
			new PacketBuffer(buffer).writeNBTTagCompoundToBuffer(nbt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		try {
			nbt = new PacketBuffer(buffer).readNBTTagCompoundFromBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

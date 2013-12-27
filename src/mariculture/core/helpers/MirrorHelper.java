package mariculture.core.helpers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.magic.ItemMirror;
import mariculture.magic.gui.ContainerMirror;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;

public class MirrorHelper {

	private static final MirrorHelper INSTANCE = new MirrorHelper();

	public static MirrorHelper instance() {
		return INSTANCE;
	}

	public ItemStack[] get(EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			NBTTagCompound loader = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			NBTTagList nbttaglist = loader.getTagList("mirrorContents");

			if (nbttaglist != null) {
				ItemStack[] mirrorContents = new ItemStack[ItemMirror.mirrorSize];
				for (int i = 0; i < nbttaglist.tagCount(); i++) {
					NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if (byte0 >= 0 && byte0 < mirrorContents.length) {
						mirrorContents[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
					}
				}

				return mirrorContents;
			}
		}

		return new ItemStack[ItemMirror.mirrorSize];
	}

	public void save(EntityPlayer player, ItemStack[] mirrorContents) {
		if (!player.worldObj.isRemote) {
			try {
				NBTTagList nbttaglist = new NBTTagList();
				for (int i = 0; i < 3; i++) {
					if (mirrorContents[i] != null) {
						NBTTagCompound nbttagcompound1 = new NBTTagCompound();
						nbttagcompound1.setByte("Slot", (byte) i);
						mirrorContents[i].writeToNBT(nbttagcompound1);
						nbttaglist.appendTag(nbttagcompound1);
					}
				}

				if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
					player.getEntityData().setCompoundTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
				}

				player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setTag("mirrorContents", nbttaglist);

			} catch (Exception e) {
				LogHandler.log(Level.WARNING, "Mariculture had trouble saving Mirror Contents for " + player.username);
			}
		}
	}
	
	public static void enchant(Packet250CustomPayload packet, EntityPlayerMP playerEntity) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int windowId;
		int level;

		try {
			id = inputStream.readInt();
			windowId = inputStream.readInt();
			level = inputStream.readInt();
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		if (playerEntity.openContainer.windowId == windowId
				&& playerEntity.openContainer.isPlayerNotUsingContainer(playerEntity)) {
			ContainerMirror mirror = (ContainerMirror) playerEntity.openContainer;
			mirror.windowId = windowId;
			mirror.enchantItem(playerEntity, level);
			mirror.detectAndSendChanges();
		}
	}
}

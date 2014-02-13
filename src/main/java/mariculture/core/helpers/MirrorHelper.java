package mariculture.core.helpers;

import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MirrorHelper {

	private static final MirrorHelper INSTANCE = new MirrorHelper();

	public static MirrorHelper instance() {
		return INSTANCE;
	}
	
	public ItemStack[] get(EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			NBTTagCompound loader = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			NBTTagList nbttaglist = loader.getTagList("mirrorContents", 10);

			if (nbttaglist != null) {
				ItemStack[] mirrorContents = new ItemStack[4];
				for (int i = 0; i < nbttaglist.tagCount(); i++) {
					NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if (byte0 >= 0 && byte0 < mirrorContents.length) {
						mirrorContents[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
					}
				}

				return mirrorContents;
			}
		}

		return new ItemStack[4];
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
					player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
				}

				player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setTag("mirrorContents", nbttaglist);

			} catch (Exception e) {
				LogHandler.log(Level.WARNING, "Mariculture had trouble saving Mirror Contents for " + player.getDisplayName());
			}
		}
	}
}

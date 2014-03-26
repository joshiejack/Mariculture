package mariculture.magic;

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
	
	public MirrorData getData(EntityPlayer player) {
		MirrorData data = (MirrorData) player.worldObj.mapStorage.loadData(MirrorData.class, MirrorData.name);
		if(data == null) {
			data = new MirrorData();
			player.worldObj.setItemData(MirrorData.name, data);
		}
		
		if(!data.hasInit()) {
			data.init();
		}
		
		return data;
	}
	
	public ItemStack[] get(EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			MirrorData data = getData(player);
			if(data.getJewelry(player) == null) {
				NBTTagCompound loader = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
				NBTTagList nbttaglist = loader.getTagList("mirrorContents");
				if (nbttaglist != null) {
					ItemStack[] mirrorContents = new ItemStack[4];
					for (int i = 0; i < nbttaglist.tagCount(); i++) {
						NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
						byte byte0 = nbttagcompound1.getByte("Slot");
						if (byte0 >= 0 && byte0 < mirrorContents.length) {
							mirrorContents[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						}
					}
					
					player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).removeTag("mirrorContents");

					return data.setJewelry(player, mirrorContents);
				} else return data.addPlayer(player);
			} else return data.getJewelry(player);
		}

		return new ItemStack[4];
	}

	public void save(EntityPlayer player, ItemStack[] mirrorContents) {
		if (!player.worldObj.isRemote) {
			try {
				MirrorData data = getData(player);
				data.setJewelry(player, mirrorContents);
			} catch (Exception e) {
				LogHandler.log(Level.WARNING, "Mariculture had trouble saving Mirror Contents for " + player.username);
			}
		}
	}
}

package mariculture.magic;

import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Extra;
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
		String check = MirrorData.name + "-" + (Extra.JEWELRY_OFFLINE? "PlayerOffline": player.username);
        MirrorData data = (MirrorData) player.worldObj.loadItemData(MirrorData.class, check);
        if (data == null) {
            data = new MirrorData(check);
            player.worldObj.setItemData(check, data);
        }
        
		return data;
	}
	
	public ItemStack[] get(EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			MirrorData data = getData(player);
			if(data.getJewelry() == null) {
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
					return data.setJewelry(mirrorContents);
				} else return data.setJewelry(new ItemStack[4]);
			} else return data.getJewelry();
		}

		return new ItemStack[4];
	}

	public void save(EntityPlayer player, ItemStack[] mirrorContents) {
		if (!player.worldObj.isRemote) {
			try {
				getData(player).setJewelry(mirrorContents);
			} catch (Exception e) {
				LogHandler.log(Level.WARNING, "Mariculture had trouble saving Mirror Contents for " + player.username);
			}
		}
	}
}

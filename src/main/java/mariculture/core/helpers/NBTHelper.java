package mariculture.core.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
	public static NBTTagCompound getPlayerData(EntityPlayer player) {
		NBTTagCompound data = player.getEntityData();
		if(!data.hasKey(player.PERSISTED_NBT_TAG)) {
			data.setTag(player.PERSISTED_NBT_TAG, new NBTTagCompound());
		}
		
		return data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
	}
}

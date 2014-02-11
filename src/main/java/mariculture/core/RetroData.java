package mariculture.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mariculture.core.lib.RetroGeneration;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;

public class RetroData extends WorldSavedData {
	private HashMap<String, Boolean> retro = new HashMap();
	private int lastData = 1;

	public RetroData() {
		super("retrogen-mariculture");
	}
	
	public RetroData(String str) {
		super(str);
	}

	public int getLastKey() {
		return this.lastData;
	}

	public boolean hasRetroGenned(String string, Chunk chunk) {
		String check = string + "~" + chunk.xPosition + "~" + chunk.zPosition;
		if (!retro.containsKey(check)) {
			retro.put(check, false);
			this.markDirty();
			return false;
		}
		
		return retro.get(check);
	}

	public boolean setHasRetroGenned(String string, Chunk chunk) {
		String check = string + "~" + chunk.xPosition + "~" + chunk.zPosition;
		if (!retro.containsKey(check)) {
			retro.put(check, true);
			lastData = RetroGeneration.KEY;
		} else {
			retro.remove(check);
			retro.put(check, true);
			lastData = RetroGeneration.KEY;
		}

		this.markDirty();

		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList tagList = nbt.getTagList("RetroData");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			retro.put(tag.getString("RetroGenKey"), tag.getBoolean("Generated"));
		}

		lastData = nbt.getInteger("LastRetroKey");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList itemList = new NBTTagList();

		Iterator it = retro.entrySet().iterator();
		while (it.hasNext()) {
			NBTTagCompound tag = new NBTTagCompound();
			Map.Entry pairs = (Map.Entry) it.next();
			tag.setString("RetroGenKey", (String) pairs.getKey());
			tag.setBoolean("Generated", (Boolean) pairs.getValue());
			itemList.appendTag(tag);
			it.remove();
		}

		nbt.setTag("RetroData", itemList);
		nbt.setInteger("LastRetroKey", lastData);
	}
}
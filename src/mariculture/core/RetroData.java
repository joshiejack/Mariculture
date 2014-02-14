package mariculture.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.WorldGeneration;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;

public class RetroData extends WorldSavedData {
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
        if(!RetroGen.retro.contains(check)) {
			return false;
		}

        return true;
	}

	public boolean setHasRetroGenned(String string, Chunk chunk) {
		String check = string + "~" + chunk.xPosition + "~" + chunk.zPosition;
        if(!RetroGen.retro.contains(check)) {
            System.out.println("Retro-Generating " + check);
            RetroGen.retro.add(check);
            lastData = RetroGeneration.KEY;
        } else {
            return false;
        }

		this.markDirty();
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
        RetroGen.retro = new ArrayList<String>();
		NBTTagList tagList = nbt.getTagList("RetroData");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            RetroGen.retro.add(tag.getString("RetroGen"));
		}

		lastData = nbt.getInteger("LastRetroKey");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList itemList = new NBTTagList();
        for(String str: RetroGen.retro) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("RetroGen", str);
            itemList.appendTag(tag);
        }

		nbt.setTag("RetroData", itemList);
		nbt.setInteger("LastRetroKey", lastData);
	}
}
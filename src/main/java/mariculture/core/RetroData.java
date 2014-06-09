package mariculture.core;

import java.util.ArrayList;

import mariculture.core.config.GeneralStuff;
import mariculture.core.handlers.LogHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;

import org.apache.logging.log4j.Level;

public class RetroData extends WorldSavedData {
    private int lastData = 1;

    public RetroData() {
        super("retrogen-mariculture");
    }

    public RetroData(String str) {
        super(str);
    }

    public int getLastKey() {
        return lastData;
    }

    public boolean hasRetroGenned(String string, Chunk chunk) {
        String check = string + "~" + chunk.xPosition + "~" + chunk.zPosition;
        if (!RetroGen.retro.contains(check)) return false;

        return true;
    }

    public boolean setHasRetroGenned(String string, Chunk chunk) {
        String check = string + "~" + chunk.xPosition + "~" + chunk.zPosition;
        if (!RetroGen.retro.contains(check)) {
            if (GeneralStuff.DEBUG_ON) {
                LogHandler.log(Level.TRACE, "Retro-Generating " + check);
            }
            RetroGen.retro.add(check);
            lastData = mariculture.core.config.WorldGeneration.RetroGen.KEY;
        } else return false;

        markDirty();
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        RetroGen.retro = new ArrayList<String>();
        NBTTagList tagList = nbt.getTagList("RetroData", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            RetroGen.retro.add(tag.getString("RetroGen"));
        }

        lastData = nbt.getInteger("LastRetroKey");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList itemList = new NBTTagList();
        for (String str : RetroGen.retro) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("RetroGen", str);
            itemList.appendTag(tag);
        }

        nbt.setTag("RetroData", itemList);
        nbt.setInteger("LastRetroKey", lastData);
    }
}
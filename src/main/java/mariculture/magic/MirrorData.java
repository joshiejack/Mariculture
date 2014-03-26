package mariculture.magic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import enchiridion.api.GuideHandler;
import mariculture.core.RetroGen;
import mariculture.core.lib.Extra;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;

public class MirrorData extends WorldSavedData {
	public static final String name = "mirror-contents-mariculture";
	
	private HashMap<String, ItemStack[]> mirrorContents;
	private int lastData = 1;

	public MirrorData() {
		super(name);
	}
	
	public MirrorData(String str) {
		super(str);
	}
	
	//Whether the mirrorcontents hashmap has been created or not
	public boolean hasInit() {
		return mirrorContents != null;
	}
	
	public void init() {
		mirrorContents = new HashMap();
	}
	
	public ItemStack[] addPlayer(EntityPlayer player) {
		setJewelry(player, new ItemStack[4]);
		this.markDirty();
		return new ItemStack[4];
	}
	
	public ItemStack[] getJewelry(EntityPlayer player) {
		return mirrorContents.get(Extra.JEWELRY_OFFLINE? "OfflinePlayer": player.username);
	}
	
	public ItemStack[] setJewelry(EntityPlayer player, ItemStack[] contents) {
		mirrorContents.put(Extra.JEWELRY_OFFLINE? "OfflinePlayer": player.username, contents);
		this.markDirty();
		return contents;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(mirrorContents == null) mirrorContents = new HashMap();
		NBTTagList tagList = nbt.getTagList("PlayerMirrorContents");
		if(tagList != null) {
			for(int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
				String username = tag.getString("Username");
				ItemStack[] contents = new ItemStack[4];
				NBTTagList items = tag.getTagList("MirrorContents");
				if(items != null) {
					for(int j = 0; j < items.tagCount(); j++) {
						NBTTagCompound itemTag = (NBTTagCompound) items.tagAt(j);
						byte byte0 = itemTag.getByte("Slot");
						if (byte0 >= 0 && byte0 < contents.length) {
							contents[byte0] = ItemStack.loadItemStackFromNBT(itemTag);
						}
					}
				}
				
				mirrorContents.put(username, contents);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList tagList = new NBTTagList();
		for (Entry<String, ItemStack[]> mContents : mirrorContents.entrySet()) {
			String player = mContents.getKey();
			ItemStack[] contents = mContents.getValue();
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("Username", player);
			NBTTagList items = new NBTTagList();
			//Don't save the last slot
			for(int i = 0; i < items.tagCount(); i++) {
				ItemStack stack = contents[i];
				if(stack != null) {
					NBTTagCompound itemTag = new NBTTagCompound();
					itemTag.setByte("Slot", (byte) i);
					stack.writeToNBT(itemTag);
					items.appendTag(itemTag);
				}
			}
			
			tag.setTag("MirrorContents", items);
			tagList.appendTag(tag);
		}
		
		nbt.setTag("PlayerMirrorContents", tagList);
	}
}
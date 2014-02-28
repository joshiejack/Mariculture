package mariculture.magic;

import java.util.HashMap;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.NBTHelper;
import mariculture.core.lib.Extra;
import mariculture.magic.enchantments.EnchantmentSpeed;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MirrorData {
	public static String world = "ls43llsds#232423d";
	public static HashMap<String, ItemStack[]> inventories = new HashMap();
	public static ItemStack[] inventory;
	private static int updateTick;
	
	public static boolean isSameWorld(EntityPlayer player) {
		String check = player.worldObj.getWorldInfo().getWorldName();
		if(check.equals(world)) {
			return true;
		} else {
			world = check;
			return false;
		}
	}
	
	public static ItemStack[] getInventory(EntityPlayer player) {
		return player.worldObj.isRemote? getInventoryForClient(): getInventoryForPlayer(player);
	}
	
	public static ItemStack[] getInventoryForPlayer(EntityPlayer player) {
		if(inventories.get(player.getDisplayName()) != null && isSameWorld(player)) {
			return inventories.get(player.getDisplayName());
		}
		
		NBTTagCompound nbt = NBTHelper.getPlayerData(player);
		ItemStack[] contents = new ItemStack[4];
		NBTTagList nbttaglist = nbt.getTagList("mirrorContents", 10);
		if (nbttaglist != null) {
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if (byte0 >= 0 && byte0 < contents.length) {
					contents[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
		}
		
		inventories.put(player.getDisplayName(), contents);
		return contents;
	}
	
	@SideOnly(value = Side.CLIENT)
	public static ItemStack[] getInventoryForClient() {
		if(inventory != null && isSameWorld(ClientHelper.getPlayer())) {
			return inventory;
		}
		
		NBTTagCompound nbt = NBTHelper.getPlayerData(ClientHelper.getPlayer());
		inventory = new ItemStack[4];
		NBTTagList nbttaglist = nbt.getTagList("mirrorContents", 10);
		if (nbttaglist != null) {
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if (byte0 >= 0 && byte0 < inventory.length) {
					inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
		}
		
		return inventory;
	}
	
	public static boolean save(EntityPlayer player, ItemStack[] invent) {
		return player.worldObj.isRemote? saveClient(player, invent): saveServer(player, invent);
	}
	
	public static boolean saveClient(EntityPlayer player, ItemStack[] invent) {
		inventory = invent;
		NBTTagCompound nbt = NBTHelper.getPlayerData(player);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < 3; i++) {
			if (invent[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				invent[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("mirrorContents", nbttaglist);
		
		//Setup the speeds after initially saving client data
		EnchantmentSpeed.set(EnchantHelper.getEnchantStrength(Magic.speed, ClientHelper.getPlayer()));
		return true;
	}
	
	public static boolean saveServer(EntityPlayer player, ItemStack[] invent) {
		inventories.put(player.getDisplayName(), invent);
		
		NBTTagCompound nbt = NBTHelper.getPlayerData(player);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < 3; i++) {
			if (invent[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				invent[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("mirrorContents", nbttaglist);
		return true;
	}
	
	/*
	public static NBTTagCompound getDataTag(EntityPlayer player) {
		return player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG);
	}
	
	public static ItemStack[] get(EntityPlayer player) {
		return get(getDataTag(player));
	}
	
	public static ItemStack[] get(NBTTagCompound nbt) {
		ItemStack[] contents = new ItemStack[4];
		NBTTagList nbttaglist = nbt.getTagList("mirrorContents", 10);
		if (nbttaglist != null) {
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if (byte0 >= 0 && byte0 < contents.length) {
					contents[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
		}
		
		System.out.println(contents[0]);
		
		return contents;
	}
	
	public static void save(EntityPlayer player, ItemStack[] contents) {
		System.out.println(contents[0]);
		save(getDataTag(player), contents);
	}
	
	public static void save(NBTTagCompound nbt, ItemStack[] contents) {
		try {
			NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < 3; i++) {
				if (contents[i] != null) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					contents[i].writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}

			nbt.setTag("mirrorContents", nbttaglist);
		} catch (Exception e) {
			LogHandler.log(Level.WARN, "Mariculture had trouble saving Mirror Contents for Someone");
		}
	}
	
	//Localised Saving and loading
	public static ItemStack[] inventory;
	public static ItemStack[] readDataFromServer(NBTTagCompound nbt) {
		inventory = new ItemStack[4];
		NBTTagList nbttaglist = nbt.getTagList("mirrorContents", 10);
		if (nbttaglist != null) {
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if (byte0 >= 0 && byte0 < inventory.length) {
					inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}
		}
		
		return inventory;
	}
	
	public static ItemStack[] readDataFromClient(EntityPlayer player) {
		if(inventory == null) {
			return readDataFromServer(getDataTag(player));
		} else {
			return inventory;
		}
	}
	
	public static void saveDataToClient(NBTTagCompound nbt) {
		try {
			inventory = new ItemStack[4];
			NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < 3; i++) {
				if (inventory[i] != null) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					inventory[i].writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}

			nbt.setTag("mirrorContents", nbttaglist);

		} catch (Exception e) {
			LogHandler.log(Level.WARN, "Mariculture had trouble saving Mirror Contents for Someone");
		}
	} */
}

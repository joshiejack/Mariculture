package mariculture.magic;

import java.util.ArrayList;
import java.util.HashMap;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.NBTHelper;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentSpeed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MirrorData {
	public static String world = "ls43llsds#232423d";
	public static HashMap<String, Integer[]> colors = new HashMap();
	public static HashMap<String, ItemStack[]> inventories = new HashMap();
	public static ItemStack[] inventory;
	public static Integer[] color;
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
	/** Color Updates **/
	
/* Cache of the colors */
	public static Integer[] getColors(EntityPlayer player) {
		return player.worldObj.isRemote? getColorsForClient(): getColorsForPlayer(player);
	}
	
	public static Integer[] getColorsForPlayer(EntityPlayer player) {
		if(colors.get(player.getDisplayName()) != null && isSameWorld(player)) {
			return colors.get(player.getDisplayName());
		}
		
		Integer[] arr = fetchColors(player);
		colors.put(player.getDisplayName(), arr);
		return arr;
	}
	
	@SideOnly(value = Side.CLIENT)
	private static Integer[] getColorsForClient() {
		if(color != null && isSameWorld(ClientHelper.getPlayer())) {
			return color;
		}
		
		color = fetchColors(ClientHelper.getPlayer());
		return color;
	}
	
	private static Integer[] fetchColors(EntityPlayer player) {
		ArrayList<Integer> colorsArray = new ArrayList();
		ItemStack[] mirror = MirrorData.getInventory(player);
		for(int i = 0; i < mirror.length; i++) {
			if(mirror[i] != null && mirror[i].hasTagCompound()) {
				colorsArray.add(mirror[i].stackTagCompound.getInteger("Part1"));
			}
		}
		
		return colorsArray.toArray(new Integer[colorsArray.size()]);
	}
	
/* Inventory Functions */
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
		//Reset the color
		color = null;
		
		//Setup the speeds after initially saving client data
		EnchantmentSpeed.set(EnchantHelper.getEnchantStrength(Magic.speed, ClientHelper.getPlayer()));
		EnchantmentJump.set(EnchantHelper.getEnchantStrength(Magic.jump, ClientHelper.getPlayer()));
		
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
		//Reset the color
		if(colors.containsKey(player.getDisplayName())) {
			colors.remove(player.getDisplayName());
		}
		
		return true;
	}
}

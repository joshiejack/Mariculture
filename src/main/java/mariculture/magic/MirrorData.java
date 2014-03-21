package mariculture.magic;

import java.util.ArrayList;
import java.util.HashMap;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.NBTHelper;
import mariculture.magic.enchantments.EnchantmentFlight;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
import mariculture.magic.enchantments.EnchantmentStepUp;
import mariculture.magic.jewelry.parts.JewelryMaterial;
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
	
	//This is the 'cached' list of the materials the jewelry piece is made up of
	public static HashMap<String, JewelryMaterial[]> materialList = new HashMap();
	public static JewelryMaterial[] materials;
	
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
	
/* Cache of the Jewelry Material */
	public static JewelryMaterial[] getMaterials(EntityPlayer player) {
		return player.worldObj.isRemote? getColorsForClient(): getColorsForPlayer(player);
	}
	
	public static JewelryMaterial[] getColorsForPlayer(EntityPlayer player) {
		String key = player.getDisplayName();
		if(materialList.get(key) != null && isSameWorld(player)) {
			return materialList.get(key);
		} else {
			JewelryMaterial[] arr = fetchMaterials(player);
			materialList.put(key, arr);
			return arr;
		}
	}
	
	@SideOnly(value = Side.CLIENT)
	private static JewelryMaterial[] getColorsForClient() {
		EntityPlayer player = ClientHelper.getPlayer();
		if(materials != null && isSameWorld(player)) return materials;
		else {
			materials = fetchMaterials(player);
			return materials;
		}
	}
	
	private static JewelryMaterial[] fetchMaterials(EntityPlayer player) {
		ArrayList<JewelryMaterial> array = new ArrayList();
		ItemStack[] mirror = MirrorData.getInventory(player);
		for(int i = 0; i < mirror.length; i++) {
			if(mirror[i] != null && mirror[i].hasTagCompound()) {
				array.add(JewelryMaterial.list.get(mirror[i].stackTagCompound.getString(JewelryMaterial.nbt)));
			}
		}
		
		return array.toArray(new JewelryMaterial[array.size()]);
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
		return player.worldObj.isRemote? saveClient(ClientHelper.getPlayer(), invent): saveServer(player, invent);
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
		materials = null;
		
		//Setup the speeds after initially saving client data
		if(EnchantHelper.exists(Magic.speed)) 	EnchantmentSpeed.set(EnchantHelper.getEnchantStrength(Magic.speed, player));
		if(EnchantHelper.exists(Magic.jump)) 	EnchantmentJump.set(EnchantHelper.getEnchantStrength(Magic.jump, player));
		if(EnchantHelper.exists(Magic.glide)) 	EnchantmentGlide.set(EnchantHelper.getEnchantStrength(Magic.glide, player));
		if(EnchantHelper.exists(Magic.spider))	EnchantmentSpider.set(EnchantHelper.hasEnchantment(Magic.spider, player));
		if(EnchantHelper.exists(Magic.flight))	EnchantmentFlight.set(EnchantHelper.getEnchantStrength(Magic.flight, player), player);
		if(EnchantHelper.exists(Magic.stepUp))	EnchantmentStepUp.set(EnchantHelper.getEnchantStrength(Magic.stepUp, player), player);
		
		return true;
	}
	
	public static boolean saveServer(EntityPlayer player, ItemStack[] invent) {
		String key = player.getDisplayName();
		inventories.put(key, invent);
		
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
		if(materialList.containsKey(key)) {
			materialList.remove(key);
		}
		
		return true;
	}
}

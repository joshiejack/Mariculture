package mariculture.plugins.enchiridion;

import java.util.ArrayList;

import mariculture.core.Core;
import mariculture.core.helpers.NBTHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.diving.ItemArmorScuba;
import mariculture.fishery.Fishery;
import mariculture.plugins.PluginEnchiridion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IPlayerTracker;
import enchiridion.api.DisplayRegistry;

public class EventHandler implements ICraftingHandler, IPlayerTracker {
	public static boolean isLoaded = false;
	
	//Reads the nbt of a player and sets if this book has been collected or not
	public static void spawnBook(EntityPlayer player, int meta) {
		if(!NBTHelper.getPlayerData(player).hasKey("BookCollected" + meta)) {
			NBTHelper.getPlayerData(player).setBoolean("BookCollected" + meta, true);
			ItemStack book = new ItemStack(PluginEnchiridion.guides, 1, meta);
			if(!player.worldObj.isRemote) {
				SpawnItemHelper.spawnItem(player.worldObj, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
			}
		}
	}
	
	@Override
	public void onPlayerLogin(EntityPlayer player) {
		if(Extra.SPAWN_BOOKS) spawnBook(player, GuideMeta.PROCESSING);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		return;
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		return;
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		return;
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack stack, IInventory craftMatrix) {
		if(Extra.SPAWN_BOOKS) {
			if(Modules.isActive(Modules.diving) && stack.getItem() instanceof ItemArmorScuba) 	spawnBook(player, GuideMeta.DIVING);
			if(Modules.isActive(Modules.fishery) && stack.getItem() == Fishery.rodReed) 		spawnBook(player, GuideMeta.FISHING);
			if(Modules.isActive(Modules.factory) && stack.getItem() == Core.crafting && stack.getItemDamage() == CraftingMeta.WHEEL) {
				spawnBook(player, GuideMeta.FISHING);
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		return;
	}
	
	@ForgeSubscribe
	public void onOreDictionaryRegistration(OreRegisterEvent event) {
		//Initialize all existing Entries
		if(!isLoaded) {
			String[] ores = OreDictionary.getOreNames();
			for(String ore: ores) {
				ArrayList<ItemStack> stacks = OreDictionary.getOres(ore);
				for(ItemStack stack: stacks) {
					if(stack != null && ore != null && !ore.equals("")) {
						DisplayRegistry.registerOreDictionaryCycling(ore);
					}
				}
			}
			
			isLoaded = true;
		}
		
		DisplayRegistry.registerOreDictionaryCycling(event.Name);
	}
}

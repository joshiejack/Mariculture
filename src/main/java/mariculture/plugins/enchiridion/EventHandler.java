package mariculture.plugins.enchiridion;

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
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IPlayerTracker;

public class EventHandler implements ICraftingHandler, IPlayerTracker {
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
			if(Modules.diving.isActive() && stack.getItem() instanceof ItemArmorScuba) 	spawnBook(player, GuideMeta.DIVING);
			if(Modules.fishery.isActive() && stack.getItem() == Fishery.rodReed) 		spawnBook(player, GuideMeta.FISHING);
			if(Modules.factory.isActive() && stack.getItem() == Core.craftingItem && stack.getItemDamage() == CraftingMeta.WHEEL) {
				spawnBook(player, GuideMeta.FISHING);
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		return;
	}
}

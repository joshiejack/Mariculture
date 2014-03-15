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
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EventHandler {
	//Reads the nbt of a player and sets if this book has been collected or not
	public void spawnBook(EntityPlayer player, int meta) {
		if(!NBTHelper.getPlayerData(player).hasKey("BookCollected" + meta)) {
			NBTHelper.getPlayerData(player).setBoolean("BookCollected" + meta, true);
			ItemStack book = new ItemStack(PluginEnchiridion.guides, 1, meta);
			if(!player.worldObj.isRemote) {
				SpawnItemHelper.spawnItem(player.worldObj, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if(Extra.SPAWN_BOOKS) spawnBook(event.player, GuideMeta.PROCESSING);
	}
	
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		if(Extra.SPAWN_BOOKS) {
			ItemStack stack = event.crafting;
			if(Modules.diving.isActive() && stack.getItem() instanceof ItemArmorScuba) 	spawnBook(event.player, GuideMeta.DIVING);
			if(Modules.fishery.isActive() && stack.getItem() == Fishery.rodReed) 		spawnBook(event.player, GuideMeta.FISHING);
			if(Modules.factory.isActive() && stack.getItem() == Core.craftingItem && stack.getItemDamage() == CraftingMeta.WHEEL) {
				spawnBook(event.player, GuideMeta.FISHING);
			}
		}
	}
}

package mariculture.core.handlers;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.NBTHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketSyncMirror;
import mariculture.diving.ItemArmorScuba;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import mariculture.magic.MirrorData;
import mariculture.magic.ResurrectionTracker;
import mariculture.plugins.compatibility.CompatBooks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class ServerFMLEvents {
	public void spawnBook(EntityPlayer player, int meta) {
		if(!NBTHelper.getPlayerData(player).hasKey("BookCollected" + meta)) {
			NBTHelper.getPlayerData(player).setBoolean("BookCollected" + meta, true);
			ItemStack book = new ItemStack(Core.guides, 1, meta);
			if(!player.worldObj.isRemote) {
				SpawnItemHelper.spawnItem(player.worldObj, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		
		if(Extra.SPAWN_BOOKS) {
			spawnBook(player, GuideMeta.PROCESSING);
		}
		
		//Custom Book on Login
		NBTTagCompound nbt = NBTHelper.getPlayerData(player);
		for(String str: CompatBooks.onWorldStart) {
			if(!nbt.hasKey(str + "Book")) {
				nbt.setBoolean(str + "Book", true);
				ItemStack book = CompatBooks.generateBook(str);
				if (!player.inventory.addItemStackToInventory(book)) {
					World world = player.worldObj;
					if(!world.isRemote) {
						SpawnItemHelper.spawnItem(world, (int)player.posX, (int)player.posY + 1, (int)player.posZ, book);
					}
				}
			}
		}
		
		if(Modules.magic.isActive() && event.player instanceof EntityPlayerMP) {
			Mariculture.packets.sendTo(new PacketSyncMirror(MirrorData.getInventoryForPlayer(player)), (EntityPlayerMP) player);
		}
	}
	
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		if(Extra.SPAWN_BOOKS) {
			ItemStack stack = event.crafting;
			if(Modules.diving.isActive() && stack.getItem() instanceof ItemArmorScuba)
				spawnBook(event.player, GuideMeta.DIVING);
			if(Modules.fishery.isActive() && stack.getItem() == Fishery.rodReed)
				spawnBook(event.player, GuideMeta.FISHING);
			if(Modules.factory.isActive() && stack.getItem() == Core.craftingItem && stack.getItemDamage() == CraftingMeta.WHEEL)
				spawnBook(event.player, GuideMeta.FISHING);
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(Modules.magic.isActive() && EnchantHelper.exists(Magic.resurrection)) {
			ResurrectionTracker.onPlayerRespawn(event.player);
		}
	}
}

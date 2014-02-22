package mariculture.core.handlers;

import mariculture.core.Core;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.Modules;
import mariculture.diving.ItemArmorScuba;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import mariculture.magic.ResurrectionTracker;
import mariculture.plugins.compatibility.CompatBooks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FMLEvents {
	public void spawnBook(EntityPlayer player, int meta) {
		if(!player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG).hasKey("BookCollected" + meta)) {
			player.getEntityData().setBoolean("BookCollected" + meta, true);
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
		NBTTagCompound nbt = player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG);
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
	public void onPlayerTick(PlayerTickEvent event) {
		//TODO: Update Player Tick
	}
	
	@SubscribeEvent
	public void onKeyPress(KeyInputEvent event) {
		//TODO: Update KeyInput
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(Modules.magic.isActive() && EnchantHelper.exists(Magic.resurrection)) {
			ResurrectionTracker.onPlayerRespawn(event.player);
		}
	}
}

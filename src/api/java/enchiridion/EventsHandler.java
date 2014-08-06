package enchiridion;

import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import enchiridion.CustomBooks.BookInfo;

public class EventsHandler {
	//Places any books in the binder
	@SubscribeEvent
	public void onItemPickUp(EntityItemPickupEvent event) {
		if(Config.binder_enabled && Config.enable_autopick) {
			ItemStack stack = event.item.getEntityItem();
			if(ContainerBinder.isBook(stack)) {
				EntityPlayer player = event.entityPlayer;
				for(ItemStack invent: player.inventory.mainInventory) {
					if(invent != null) {
						if(ItemEnchiridion.isBookBinder(invent) && invent.stackSize == 1) {
							ItemEnchiridion binder = (ItemEnchiridion)invent.getItem();
							int placed = binder.addToStorage(player.worldObj, invent, stack);
							if(placed > 0) {
								ItemStack clone = stack.copy();
								clone.stackSize-= placed;
								if(clone.stackSize > 0) {
									event.item.setEntityItemStack(clone);
								} else event.item.setDead();
	
								Random rand = event.entityPlayer.worldObj.rand;
								event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
								event.setCanceled(true);
								return;
							}
						}
					}
				}
			}
		}
	}

	public NBTTagCompound getPlayerData(EntityPlayer player) {
		NBTTagCompound data = player.getEntityData();
		if(!data.hasKey(player.PERSISTED_NBT_TAG)) {
			data.setTag(player.PERSISTED_NBT_TAG, new NBTTagCompound());
		}

		return data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
	}

	public void spawnBook(EntityPlayer player, String identifier) {
		if(!getPlayerData(player).hasKey("EnchiridionBook" + identifier)) {
			getPlayerData(player).setBoolean("EnchiridionBook" + identifier, true);
			ItemStack book = CustomBooks.create(identifier);
			if (!player.inventory.addItemStackToInventory(book)) {
				if (!player.worldObj.isRemote) {
					player.dropPlayerItemWithRandomChoice(book, false);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		if(CustomBooks.bookInfo.size() > 0) {
			for (Entry<String, BookInfo> books : CustomBooks.bookInfo.entrySet()) {
				BookInfo info = books.getValue();
				if(info.onWorldStart) {
					spawnBook(event.player, books.getKey());
				}
			}
		}
		
		if(Config.spawn_binder && Config.preload_books != null && !getPlayerData(event.player).hasKey("SpawnedBookBinder")) {
			ItemStack binder = new ItemStack(Enchiridion.items, 1, ItemEnchiridion.BINDER);
			NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < Config.preload_books.length; i++) {
				if (Config.preload_books[i] != null) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					Config.preload_books[i].writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}
			if (!binder.hasTagCompound()) {
				binder.setTagCompound(new NBTTagCompound());
			}
			
			binder.stackTagCompound.setTag("Inventory", nbttaglist);
			if (!event.player.inventory.addItemStackToInventory(binder)) {
				if (!event.player.worldObj.isRemote) {
					event.player.dropPlayerItemWithRandomChoice(binder, false);
				}
			}
			
			getPlayerData(event.player).setBoolean("SpawnedBookBinder", true);
		}
	}

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		ItemStack item = event.crafting;
		EntityPlayer player = event.player;
		if(CustomBooks.bookInfo.size() > 0) {
			for (Entry<String, BookInfo> books : CustomBooks.bookInfo.entrySet()) {
				BookInfo info = books.getValue();
				if(info.onCrafting != null) {
					ItemStack check = info.onCrafting;
					if(item.getItem() == check.getItem() && item.getItemDamage() == check.getItemDamage()) {
						spawnBook(player, books.getKey());
					}
				}
			}
		}
	}
}
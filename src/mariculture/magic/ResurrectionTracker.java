package mariculture.magic;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.enchantments.EnchantmentResurrection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.common.IPlayerTracker;

public class ResurrectionTracker {
	public static void onPlayerRespawn(EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			NBTTagCompound nbt = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
			if (nbt.hasKey(EnchantmentResurrection.inventory)) {
				NBTTagList invList = nbt.getTagList(EnchantmentResurrection.inventory);
				if (invList != null) {
					ItemStack[] inventory = new ItemStack[player.inventory.mainInventory.length];
					for (int i = 0; i < invList.tagCount(); i++) {
						NBTTagCompound nbttagcompound1 = (NBTTagCompound) invList.tagAt(i);
						byte byte0 = nbttagcompound1.getByte("Slot");
						if (byte0 >= 0 && byte0 < inventory.length) {
							inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						}
					}

					player.inventory.mainInventory = inventory;
				}

				NBTTagList armorList = nbt.getTagList(EnchantmentResurrection.armor);
				if (armorList != null) {
					ItemStack[] inventory = new ItemStack[player.inventory.armorInventory.length];
					for (int i = 0; i < armorList.tagCount(); i++) {
						NBTTagCompound nbttagcompound1 = (NBTTagCompound) armorList.tagAt(i);
						byte byte0 = nbttagcompound1.getByte("Slot");
						if (byte0 >= 0 && byte0 < inventory.length) {
							inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						}
					}

					player.inventory.armorInventory = inventory;
				}

				nbt.removeTag(EnchantmentResurrection.armor);
				nbt.removeTag(EnchantmentResurrection.inventory);

				EnchantHelper.damageItems(Magic.resurrection, player, 1);

				if (nbt.hasKey(EnchantmentResurrection.spawnX)) {
					int x = nbt.getInteger(EnchantmentResurrection.spawnX);
					int y = nbt.getInteger(EnchantmentResurrection.spawnY);
					int z = nbt.getInteger(EnchantmentResurrection.spawnZ);

					player.setPositionAndUpdate(x, y + 1, z);

					nbt.removeTag(EnchantmentResurrection.spawnX);
					nbt.removeTag(EnchantmentResurrection.spawnY);
					nbt.removeTag(EnchantmentResurrection.spawnZ);

					EnchantHelper.damageItems(Magic.resurrection, player, 2);
				}

				if (nbt.hasKey(EnchantmentResurrection.resistTime)) {
					int resist = nbt.getInteger(EnchantmentResurrection.resistTime);
					player.hurtResistantTime = resist;
					
					EnchantHelper.damageItems(Magic.resurrection, player, resist / 50);
				}
			} else {
				player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).removeTag("mirrorContents");
			}
		}
	}
}

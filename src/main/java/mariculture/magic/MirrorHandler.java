package mariculture.magic;

import mariculture.Mariculture;
import mariculture.api.core.IMirrorHandler;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.network.PacketDamageJewelry;
import mariculture.core.network.PacketSyncMirror;
import mariculture.core.util.Rand;
import mariculture.magic.JewelryHandler.SettingType;
import mariculture.magic.enchantments.EnchantmentElemental;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MirrorHandler implements IMirrorHandler {
	@Override
	public boolean containsEnchantedItems(EntityPlayer player) {
		// Mirror
		ItemStack[] mirror = MirrorData.getInventory(player);
		if (mirror != null) {
			for (int i = 0; i < 3; i++) {
				if (mirror[i] != null) {
					if (mirror[i].isItemEnchanted() && !EnchantHelper.isBroken(mirror[i])) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public void dropItems(EntityPlayer player, World world, double posX, double posY, double posZ) {
		ItemStack[] mirror = MirrorData.getInventory(player);
		for (int i = 0; i < mirror.length; ++i) {
			if (mirror[i] != null) {
				player.dropPlayerItemWithRandomChoice(mirror[i], true);
				mirror[i] = null;
			}
		}
	}

	@Override
	public int getEnchantmentStrength(EntityPlayer player, int enchant) {
		ItemStack[] mirror = MirrorData.getInventory(player);
		int total = 0;
		for (int i = 0; i < 3; i++) {
			if (mirror[i] != null && !EnchantHelper.isBroken(mirror[i])) {
				total = total + EnchantHelper.getLevel(enchant, mirror[i]);
			}
		}
		
		return total;
	}

	@Override
	public boolean hasEnchantment(EntityPlayer player, int enchant) {
		ItemStack[] mirror = MirrorData.getInventory(player);
		for (int i = 0; i < 3; i++) {
			if (mirror[i] != null) {
				if(EnchantHelper.hasEnchantment(enchant, mirror[i]))
					return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack[] getMirrorContents(EntityPlayer player) {
		return MirrorData.getInventory(player);
	}

	@Override
	public void damageItemsWithEnchantment(EntityPlayer player, int enchant, int amount) {
		if (player.worldObj.isRemote && player instanceof EntityClientPlayerMP) {
			Mariculture.packets.sendToServer(new PacketDamageJewelry(enchant, amount));
		} else {
			handleDamage(player, enchant, amount);
		}
	}

	public static void handleDamage(EntityPlayer player, int enchant, int amount) {
		// Mirror
		//Set the amount of damage to 1 if the enchantment is the elemental enchant
		amount = (EnchantHelper.exists(Magic.elemental) && enchant == Magic.elemental.effectId)? 1: amount;
		ItemStack[] mirror = MirrorData.getInventory(player);
		for(int damaged = 0; damaged < amount; damaged++) {
			for (int i = 0; i < 3; i++) {
				if (mirror[i] != null) {
					if (EnchantHelper.hasEnchantment(enchant, mirror[i])) {
						if(mirror[i].attemptDamageItem(1, Rand.rand)) {
							Mariculture.packets.sendTo(new PacketSyncMirror(MirrorData.getInventoryForPlayer(player)), (EntityPlayerMP) player);
						} else if(JewelryHandler.getSetting(mirror[i], SettingType.LEVEL) < JewelryHandler.getMaxLevel(mirror[i])) {
							JewelryHandler.adjustSetting(mirror[i], +1, SettingType.XP);
							// ^ increase the xp level of the jewelry piece, after being damaged
						}
					}
				}
			}
		}

		MirrorData.save(player, mirror);
	}

	@Override
	public boolean isJewelry(ItemStack stack) {
		return (stack != null && stack.getItem() instanceof ItemJewelry);
	}
}

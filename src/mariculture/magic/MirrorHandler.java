package mariculture.magic;

import mariculture.api.core.IMirrorHandler;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.MirrorHelper;
import mariculture.core.network.Packet109DamageJewelry;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MirrorHandler implements IMirrorHandler {
	@Override
	public boolean containsEnchantedItems(EntityPlayer player) {
		// Mirror
		ItemStack[] mirror = MirrorHelper.instance().get(player);
		if (mirror != null) {
			for (int i = 0; i < 3; i++) {
				if (mirror[i] != null) {
					if (mirror[i].isItemEnchanted()) {
						return true;
					}
				}
			}
		}

		// Armor
		for (int i = 0; i < 4; i++) {
			final ItemStack armor = player.inventory.armorInventory[i];
			if (armor != null) {
				if (armor.isItemEnchanted()) {
					return true;
				}
			}
		}

		// Sword
		if (player.getCurrentEquippedItem() != null) {
			if (player.getCurrentEquippedItem().isItemEnchanted()
					&& EnumEnchantmentType.weapon.canEnchantItem(player.getCurrentEquippedItem().getItem())) {
				return true;
			}
		}

		if(EnchantHelper.exists(Magic.flight)) {
			// Feather
			for (int i = 0; i < 9; i++) {
				if (player.inventory.mainInventory[i] != null) {
					if (player.inventory.mainInventory[i].itemID == Item.feather.itemID) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public void dropItems(EntityPlayer player, World world, double posX, double posY, double posZ) {
		ItemStack[] mirror = MirrorHelper.instance().get(player);
		for (int i = 0; i < mirror.length; ++i) {
			if (mirror[i] != null) {
				player.dropPlayerItemWithRandomChoice(mirror[i], true);
				mirror[i] = null;
			}
		}
	}

	@Override
	public int getEnchantmentStrength(EntityPlayer player, int enchant) {
		ItemStack[] mirror = MirrorHelper.instance().get(player);

		int total = 0;

		// Mirror
		for (int i = 0; i < 3; i++) {
			if (mirror[i] != null) {
				total = total + EnchantmentHelper.getEnchantmentLevel(enchant, mirror[i]);
			}
		}

		// Armor
		for (int i = 0; i < 4; i++) {
			final ItemStack armor = player.inventory.armorInventory[i];
			if (armor != null) {
				total = total + EnchantmentHelper.getEnchantmentLevel(enchant, armor);
			}
		}

		// Sword
		if (player.getCurrentEquippedItem() != null
				&& EnumEnchantmentType.weapon.canEnchantItem(player.getCurrentEquippedItem().getItem())) {
			total = total + EnchantmentHelper.getEnchantmentLevel(enchant, player.getCurrentEquippedItem());
		}

		if(EnchantHelper.exists(Magic.flight)) {
			// Feather
			for (int i = 0; i < 9; i++) {
				if (player.inventory.mainInventory[i] != null) {
					if (enchant == Magic.flight.effectId
							&& player.inventory.mainInventory[i].itemID == Item.feather.itemID) {
						total = total + EnchantmentHelper.getEnchantmentLevel(enchant, player.inventory.mainInventory[i]);
					}
				}
			}
		}

		return total;
	}

	@Override
	public boolean hasEnchantment(EntityPlayer player, int enchant) {
		// Mirror
		ItemStack[] mirror = MirrorHelper.instance().get(player);
		for (int i = 0; i < 3; i++) {
			if (mirror[i] != null) {
				if (EnchantmentHelper.getEnchantmentLevel(enchant, mirror[i]) > 0) {
					return true;
				}
			}
		}

		// Armor
		for (int i = 0; i < 4; i++) {
			final ItemStack armor = player.inventory.armorInventory[i];
			if (armor != null) {
				if (EnchantmentHelper.getEnchantmentLevel(enchant, armor) > 0) {
					return true;
				}
			}
		}

		// Sword
		if (player.getCurrentEquippedItem() != null) {
			if (EnumEnchantmentType.weapon.canEnchantItem(player.getCurrentEquippedItem().getItem())) {
				if (EnchantmentHelper.getEnchantmentLevel(enchant, player.getCurrentEquippedItem()) > 0) {
					return true;
				}
			}
		}

		if(EnchantHelper.exists(Magic.flight)) {
			// Feather
			for (int i = 0; i < 9; i++) {
				if (player.inventory.mainInventory[i] != null) {
					if (enchant == Magic.flight.effectId
							&& player.inventory.mainInventory[i].itemID == Item.feather.itemID) {
						if (EnchantmentHelper.getEnchantmentLevel(enchant, player.inventory.mainInventory[i]) > 0) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public ItemStack[] getMirrorContents(EntityPlayer player) {
		return MirrorHelper.instance().get(player);
	}

	@Override
	public void damageItemsWithEnchantment(EntityPlayer player, int enchant, int amount) {
		if (player.worldObj.isRemote && player instanceof EntityClientPlayerMP) {
			((EntityClientPlayerMP)player).sendQueue.addToSendQueue(new Packet109DamageJewelry(enchant, amount).build());
			return;
		}

		handleDamage(player, enchant, amount);
	}

	public static void handleDamage(EntityPlayer player, int enchant, int amount) {
		// Mirror
		ItemStack[] mirror = MirrorHelper.instance().get(player);
		for (int i = 0; i < 3; i++) {
			if (mirror[i] != null) {
				if (EnchantmentHelper.getEnchantmentLevel(enchant, mirror[i]) > 0) {
					mirror[i].damageItem(amount, player);
				}
			}
		}

		// Armor
		for (int i = 0; i < 4; i++) {
			final ItemStack armor = player.inventory.armorInventory[i];

			if (armor != null) {
				if (EnchantmentHelper.getEnchantmentLevel(enchant, armor) > 0) {
					armor.damageItem(amount, player);
				}
			}
		}

		// Sword
		if (player.getCurrentEquippedItem() != null
				&& EnumEnchantmentType.weapon.canEnchantItem(player.getCurrentEquippedItem().getItem())) {
			if (EnchantmentHelper.getEnchantmentLevel(enchant, player.getCurrentEquippedItem()) > 0) {
				player.getCurrentEquippedItem().damageItem(amount, player);
			}
		}

		// Feather no damage

		MirrorHelper.instance().save(player, mirror);
	}

	static boolean once = false;

	@Override
	public boolean isJewelry(ItemStack stack) {
		return (stack != null && stack.getItem() instanceof ItemJewelry);
	}
}

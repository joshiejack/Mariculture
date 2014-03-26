package mariculture.magic;

import mariculture.api.core.IMirrorHandler;
import mariculture.core.network.Packet109DamageJewelry;
import mariculture.core.util.Rand;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
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
					if (mirror[i].isItemEnchanted() && mirror[i].getItemDamage() < mirror[i].getMaxDamage()) {
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
			if (mirror[i] != null && mirror[i].getItemDamage() < mirror[i].getMaxDamage()) {
				total = total + EnchantmentHelper.getEnchantmentLevel(enchant, mirror[i]);
			}
		}
		
		return total;
	}

	@Override
	public boolean hasEnchantment(EntityPlayer player, int enchant) {
		// Mirror
		ItemStack[] mirror = MirrorHelper.instance().get(player);
		for (int i = 0; i < 3; i++) {
			if (mirror[i] != null && mirror[i].getItemDamage() < mirror[i].getMaxDamage()) {
				if (EnchantmentHelper.getEnchantmentLevel(enchant, mirror[i]) > 0) {
					return true;
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
					mirror[i].attemptDamageItem(1, Rand.rand);
				}
			}
		}

		MirrorHelper.instance().save(player, mirror);
	}

	@Override
	public boolean isJewelry(ItemStack stack) {
		return (stack != null && stack.getItem() instanceof ItemJewelry);
	}
}

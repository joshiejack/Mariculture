package mariculture.magic.enchantments;

import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnchantmentFire extends EnchantmentJewelry {
	public EnchantmentFire(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("fireResistance");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 15 + (level - 1) * 10;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return this.getMinEnchantability(level) + 25;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	private static int damageTicker;

	public static void activate(EntityPlayer player, LivingAttackEvent event) {
		if (EnchantHelper.hasEnchantment(Magic.fire, player)) {
			if (EnchantHelper.getEnchantStrength(Magic.fire, player) > 2) {
				if (player.handleLavaMovement()) {
					damageTicker++;
					if (damageTicker >= 100) {
						damageTicker = 0;
						EnchantHelper.damageItems(Magic.fire, player, 1);
					}

					event.setCanceled(true);
				}
			}
		}
	}

	public static void testForFireDamage(LivingHurtEvent event) {
		Random rand = new Random();
		EntityPlayer player = (EntityPlayer) event.entity;

		if (EnchantHelper.hasEnchantment(Magic.fire, player)
				&& (event.source == DamageSource.onFire || event.source == DamageSource.inFire || event.source == DamageSource.lava)) {
			if (EnchantHelper.getEnchantStrength(Magic.fire, player) > 2) {
				EnchantmentFire.damageTicker++;
				if (EnchantmentFire.damageTicker >= 100) {
					EnchantmentFire.damageTicker = 0;
					EnchantHelper.damageItems(Magic.fire, player, 1);
				}

				player.extinguish();

				event.setCanceled(true);
			}
		}
	}

	public static void onAttack(EntityPlayer player, Entity target) {
		if (EnchantHelper.hasEnchantment(Magic.fire, player)) {
			if (target instanceof EntityLivingBase) {
				if (player.getCurrentEquippedItem() == null
						|| (player.getCurrentEquippedItem() != null &&
								EnchantHelper.getLevel(Magic.fire, player.getCurrentEquippedItem()) > 0)) {

					EntityLivingBase enemy = (EntityLivingBase) target;
					if (!target.isBurning()) {
						EnchantHelper.damageItems(Magic.fire, player, 1);
					}

					player.worldObj.playSoundAtEntity(player, Mariculture.modid + ":firepunch", 1.0F, 1.0F);
					target.setFire(2 * EnchantHelper.getEnchantStrength(Magic.fire, player));
				}
			}
		}
	}

	public static void onRightClick(EntityPlayer player, Entity target) {
		if (KeyHelper.ACTIVATE_PRESSED) {
			if (EnchantHelper.hasEnchantment(Magic.fire, player)) {
				if (target instanceof EntityLivingBase
						&& EnchantHelper.getEnchantStrength(Magic.fire, player) > 1) {

					EntityLivingBase life = (EntityLivingBase) target;

					if (!life.isBurning()) {
						EnchantHelper.damageItems(Magic.fire, player, 1);
					}

					player.worldObj.playSoundAtEntity(player, Mariculture.modid + ":firepunch", 1.0F, 1.0F);
					life.setFire(EnchantHelper.getEnchantStrength(Magic.fire, player));

				}
			}
		}
	}
}

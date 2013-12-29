package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Mariculture;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class EnchantmentPunch extends EnchantmentJewelry {
	public EnchantmentPunch(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("punch");
	}
	
	@Override
	public int getMinEnchantability(int level) {
		return 1 + (level - 1) * 10;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return this.getMinEnchantability(level) + 15;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	public static void onAttack(EntityPlayer player, Entity target) {
		if (EnchantHelper.hasEnchantment(Magic.punch, player)) {
			if (target instanceof EntityLivingBase && player.getCurrentEquippedItem() == null) {
				player.worldObj.playSoundAtEntity(player, Mariculture.modid + ":powerpunch", 1.0F, 1.0F);
				
				target.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) target),
						(EnchantHelper.getEnchantStrength(Magic.punch, player)));
				EnchantHelper.damageItems(Magic.punch, player, 1);
			}
		}
	}
}

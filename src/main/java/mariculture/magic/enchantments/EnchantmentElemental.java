package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.util.Rand;
import mariculture.magic.Magic;
import mariculture.magic.MirrorData;
import mariculture.magic.jewelry.parts.JewelryPart;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnchantmentElemental extends EnchantmentJewelry {
	public EnchantmentElemental(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("elemental");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 10 + (level + 1) * 7;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 80;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	public static void onHurt(LivingHurtEvent event) {
		if(!event.entity.worldObj.isRemote) {
			if(event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				if(EnchantHelper.hasEnchantment(Magic.elemental, player)) {
					for(Integer color: MirrorData.getColors(player)) {
						int chance = JewelryPart.materialList.get(color).cancelDamage(player, event.source);
						if(chance > 0) {
							if(Rand.rand.nextInt(100) < chance) EnchantHelper.damageItems(Magic.elemental, player, color);
							event.setCanceled(true);
						}
					}
				}
			}
		}
	}

	public static boolean hasElement(int enchant, int type, ItemStack stack) {
		if(!EnchantHelper.exists(Magic.elemental))
			return false;
		if(enchant == Magic.elemental.effectId) {
			if(stack.hasTagCompound()) {
				if(stack.stackTagCompound.getInteger("Part1") == type) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
}

package mariculture.magic.enchantments;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.util.Rand;
import mariculture.magic.Magic;
import mariculture.magic.MirrorData;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class EnchantmentElemental extends EnchantmentJewelry {
	public EnchantmentElemental(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		setName("elemental");
		minLevel = 35;
		maxLevel = 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	public static void onHurt(LivingHurtEvent event, EntityPlayer player) {
		if(EnchantHelper.hasEnchantment(Magic.elemental, player)) {
			for(JewelryMaterial material: MirrorData.getMaterials(player)) {
				int onHurt = material.onHurt(event, player);
				if(onHurt > 0 && Rand.nextInt(onHurt)) {
					MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
				}
			}
		}
	}
	
	public static void onAttacked(LivingAttackEvent event, EntityPlayer player) {
		if(EnchantHelper.hasEnchantment(Magic.elemental, player)) {
			for(JewelryMaterial material: MirrorData.getMaterials(player)) {
				int onAttacked = material.onAttacked(event, player);
				if(onAttacked > 0 && Rand.nextInt(onAttacked)) {
					MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
				}
			}
		}
	}
	
	public static void onAttack(LivingAttackEvent event, EntityPlayer player) {
		if(EnchantHelper.hasEnchantment(Magic.elemental, player)) {
			for(JewelryMaterial material: MirrorData.getMaterials(player)) {
				int onAttack = material.onAttack(event, player);
				if(onAttack > 0 && Rand.nextInt(onAttack)) {
					MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
				}
			}
		}
	}
	
	public static void onKillEntity(LivingDeathEvent event, EntityPlayer player) {
		if(EnchantHelper.hasEnchantment(Magic.elemental, player)) {
			for(JewelryMaterial material: MirrorData.getMaterials(player)) {
				int onKill = material.onKill(event, player);
				if(onKill > 0 && Rand.nextInt(onKill)) {
					MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
				}
			}
		}
	}
	
	public static void onBlockBreak(HarvestDropsEvent event, EntityPlayer player) {
		if(EnchantHelper.hasEnchantment(Magic.elemental, player)) {
			for(JewelryMaterial material: MirrorData.getMaterials(player)) {
				int onBlockBreak = material.onBlockBreak(event, player);
				if(onBlockBreak > 0 && Rand.nextInt(onBlockBreak)) {
					MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
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

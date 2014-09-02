package joshie.mariculture.magic.enchantments;

import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.core.helpers.EnchantHelper;
import joshie.mariculture.magic.Magic;
import joshie.mariculture.magic.MirrorHelper;
import joshie.mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
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
        if (EnchantHelper.hasEnchantment(Magic.elemental, player)) {
            for (JewelryMaterial material : MirrorHelper.getMaterials(player)) {
                int onHurt = material.onHurt(event, player);
                if (onHurt > 0 && player.worldObj.rand.nextInt(onHurt) == 0) {
                    MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
                }
            }
        }
    }

    public static void onAttacked(LivingAttackEvent event, EntityPlayer player) {
        if (EnchantHelper.hasEnchantment(Magic.elemental, player)) {
            for (JewelryMaterial material : MirrorHelper.getMaterials(player)) {
                int onAttacked = material.onAttacked(event, player);
                if (onAttacked > 0 && player.worldObj.rand.nextInt(onAttacked) == 0) {
                    MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
                }
            }
        }
    }

    public static void onAttack(LivingAttackEvent event, EntityPlayer player) {
        if (EnchantHelper.hasEnchantment(Magic.elemental, player)) {
            for (JewelryMaterial material : MirrorHelper.getMaterials(player)) {
                int onAttack = material.onAttack(event, player);
                if (onAttack > 0 && player.worldObj.rand.nextInt(onAttack) == 0) {
                    MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
                }
            }
        }
    }

    public static void onKillEntity(LivingDeathEvent event, EntityPlayer player) {
        if (EnchantHelper.hasEnchantment(Magic.elemental, player)) {
            for (JewelryMaterial material : MirrorHelper.getMaterials(player)) {
                int onKill = material.onKill(event, player);
                if (onKill > 0 && player.worldObj.rand.nextInt(onKill) == 0) {
                    MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
                }
            }
        }
    }

    public static void onBlockBreak(HarvestDropsEvent event, EntityPlayer player) {
        if (EnchantHelper.hasEnchantment(Magic.elemental, player)) {
            for (JewelryMaterial material : MirrorHelper.getMaterials(player)) {
                int onBlockBreak = material.onBlockBreak(event, player);
                if (onBlockBreak > 0 && player.worldObj.rand.nextInt(onBlockBreak) == 0) {
                    MaricultureHandlers.mirror.damageItemsWithEnchantment(player, Magic.elemental.effectId, material.id);
                }
            }
        }
    }
}

package joshie.mariculture.magic;

import java.util.Random;

import joshie.mariculture.core.helpers.EnchantHelper;
import joshie.mariculture.core.helpers.PlayerHelper;
import joshie.mariculture.core.helpers.cofh.CoFhItemHelper;
import joshie.mariculture.magic.enchantments.EnchantmentBlink;
import joshie.mariculture.magic.enchantments.EnchantmentElemental;
import joshie.mariculture.magic.enchantments.EnchantmentFallDamage;
import joshie.mariculture.magic.enchantments.EnchantmentFlight;
import joshie.mariculture.magic.enchantments.EnchantmentGlide;
import joshie.mariculture.magic.enchantments.EnchantmentHealth;
import joshie.mariculture.magic.enchantments.EnchantmentJump;
import joshie.mariculture.magic.enchantments.EnchantmentNeverHungry;
import joshie.mariculture.magic.enchantments.EnchantmentOneRing;
import joshie.mariculture.magic.enchantments.EnchantmentRestore;
import joshie.mariculture.magic.enchantments.EnchantmentResurrection;
import joshie.mariculture.magic.enchantments.EnchantmentSpeed;
import joshie.mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MagicEventHandler {
    private Random rand = new Random();

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.left;
        ItemStack right = event.right;
        if (!JewelryHandler.canApply(left) || !JewelryHandler.canApply(right)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer && !PlayerHelper.isFake((EntityPlayer) event.entity)) {
            World world = event.entity.worldObj;
            EntityPlayer player = (EntityPlayer) event.entity;
            if (world.isRemote) {
                if (EnchantHelper.exists(Magic.glide)) {
                    EnchantmentGlide.activate(player);
                }
                if (EnchantHelper.exists(Magic.speed)) {
                    EnchantmentSpeed.activate(player);
                }
                if (EnchantHelper.exists(Magic.spider)) {
                    EnchantmentSpider.activate(player);
                }
                if (EnchantHelper.exists(Magic.flight)) {
                    EnchantmentFlight.damage(player);
                }
            } else {
                if (EnchantHelper.exists(Magic.oneRing)) {
                    EnchantmentOneRing.activate(player);
                }
                if (EnchantHelper.exists(Magic.hungry)) {
                    EnchantmentNeverHungry.activate(player);
                }
            }

            if (!player.capabilities.isCreativeMode) if (EnchantHelper.hasEnchantment(Magic.repair, player)) {
                EnchantmentRestore.activate(player);
            }
        }
    }

    @SubscribeEvent
    public void onEntityJump(LivingJumpEvent event) {
        if (EnchantHelper.exists(Magic.jump)) if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (player.worldObj.isRemote) {
                EnchantmentJump.activate(player);
            }
        }
    }

    @SubscribeEvent
    public void onEntityFall(LivingFallEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) if (EnchantHelper.exists(Magic.glide) || EnchantHelper.exists(Magic.fall) || EnchantHelper.exists(Magic.flight)) {
            EntityPlayer player = (EntityPlayer) event.entity;
            EnchantmentGlide.damage(player, rand);
            EnchantmentFallDamage.activate(event, player);

            if (EnchantHelper.hasEnchantment(Magic.flight, player)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(HarvestDropsEvent event) {
        if (event.harvester == null) return;
        if (event.harvester.worldObj.isRemote) return;
        if (EnchantHelper.exists(Magic.elemental)) {
            EnchantmentElemental.onBlockBreak(event, event.harvester);
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (EnchantHelper.exists(Magic.health)) if (event.entity instanceof EntityPlayer) {
            EnchantmentHealth.activate(event, (EntityPlayer) event.entity);
        }

        if (event.entity.worldObj.isRemote) return;
        if (EnchantHelper.exists(Magic.elemental) && event.entity instanceof EntityPlayer) {
            EnchantmentElemental.onHurt(event, (EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onAttackLiving(LivingAttackEvent event) {
        if (event.entity.worldObj.isRemote) return;
        if (EnchantHelper.exists(Magic.elemental)) {
            if (event.entity instanceof EntityPlayer) {
                EnchantmentElemental.onAttacked(event, (EntityPlayer) event.entity);
            }

            if (event.source.getSourceOfDamage() instanceof EntityPlayer) {
                EnchantmentElemental.onAttack(event, (EntityPlayer) event.source.getSourceOfDamage());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (EnchantHelper.hasEnchantment(Magic.blink, event.entityPlayer)) if (event.entityPlayer.worldObj.isRemote && event.action == Action.RIGHT_CLICK_AIR) {
            EnchantmentBlink.sendPacket((EntityClientPlayerMP) event.entityPlayer);
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if (EnchantHelper.exists(Magic.resurrection)) {
            EnchantmentResurrection.activate(event);
        }

        if (event.source.getSourceOfDamage() != null && event.source.getSourceOfDamage() instanceof EntityPlayer) {
            EntityLivingBase entity = event.entityLiving;
            EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
            if (!(entity instanceof EntityPlayer)) {
                if (CoFhItemHelper.isPlayerHoldingItem(Magic.magnet, player)) {
                    ItemStack magnet = player.getCurrentEquippedItem();
                    if (!magnet.hasTagCompound()) {
                        magnet.setTagCompound(new NBTTagCompound());
                        magnet.stackTagCompound.setString("MobName", EntityList.getEntityString(entity));
                    }
                }
            }
        }

        if (event.entity.worldObj.isRemote) return;
        if (EnchantHelper.exists(Magic.elemental) && event.source.getSourceOfDamage() instanceof EntityPlayer) {
            EnchantmentElemental.onKillEntity(event, (EntityPlayer) event.source.getSourceOfDamage());
        }
    }
}

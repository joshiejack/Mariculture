package mariculture.magic;

import java.util.Random;

import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.helpers.cofh.CoFhItemHelper;
import mariculture.magic.enchantments.EnchantmentBlink;
import mariculture.magic.enchantments.EnchantmentElemental;
import mariculture.magic.enchantments.EnchantmentFallDamage;
import mariculture.magic.enchantments.EnchantmentFlight;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentHealth;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentNeverHungry;
import mariculture.magic.enchantments.EnchantmentOneRing;
import mariculture.magic.enchantments.EnchantmentRestore;
import mariculture.magic.enchantments.EnchantmentResurrection;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
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

            if (!player.capabilities.isCreativeMode) {
                if (EnchantHelper.hasEnchantment(Magic.repair, player)) {
                    EnchantmentRestore.activate(player);
                }
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

    public MovingObjectPosition getMovingObjectPositionFromPlayer(World world, EntityPlayer player, boolean p_77621_3_) {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f + (double) (world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (player instanceof EntityPlayerMP) {
            d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        }
        Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return world.func_147447_a(vec3, vec31, p_77621_3_, !p_77621_3_, false);
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_AIR) {
            if (EnchantHelper.hasEnchantment(Magic.blink, event.entityPlayer)) {
                if (event.entityPlayer.worldObj.isRemote) {
                    EnchantmentBlink.sendPacket((EntityClientPlayerMP) event.entityPlayer);
                }
            }

            if (event.entityPlayer instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
                ItemStack held = player.getCurrentEquippedItem();
                if (held == null || held.getItem() == null || !(held.getItem() instanceof ItemBlock)) return;
                if (PlayerHelper.hasItem(player, new ItemStack(Magic.placer), true) >= 0) {
                    World world = event.world;
                    MovingObjectPosition object = getMovingObjectPositionFromPlayer(world, player, true);
                    if (object == null) return;
                    else {
                        if (object.typeOfHit == MovingObjectType.BLOCK) {
                            int x = object.blockX;
                            int y = object.blockY;
                            int z = object.blockZ;
                            ItemBlock item = (ItemBlock) held.getItem();
                            item.onItemUse(held, player, world, x, y + 1, z, object.sideHit, 0.5F, 0.5F, 0.5F);
                        }
                    }
                }
            }
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

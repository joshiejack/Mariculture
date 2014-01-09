package mariculture.magic;

import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.Jewelry;
import mariculture.magic.enchantments.EnchantmentBlink;
import mariculture.magic.enchantments.EnchantmentClock;
import mariculture.magic.enchantments.EnchantmentFallDamage;
import mariculture.magic.enchantments.EnchantmentFire;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentOneRing;
import mariculture.magic.enchantments.EnchantmentPoison;
import mariculture.magic.enchantments.EnchantmentPunch;
import mariculture.magic.enchantments.EnchantmentResurrection;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.event.world.WorldEvent;

public class MagicEventHandler {
	private Random rand = new Random();
	
	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event) {
		if(EnchantHelper.exists(Magic.glide) || EnchantHelper.exists(Magic.speed) || EnchantHelper.exists(Magic.spider)) {
			if (event.entity instanceof EntityPlayer && event.entity.worldObj.isRemote) {
				EntityPlayer player = (EntityPlayer) event.entity;
				EnchantmentGlide.activate(player);
				EnchantmentSpeed.activate(player);
				EnchantmentSpider.activate(player);
			}
		}

		if(EnchantHelper.exists(Magic.oneRing)) {
			if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote) {
				EnchantmentOneRing.activate((EntityPlayer) event.entity);
			}
		}
	}

	@ForgeSubscribe
	public void onEntityJump(LivingJumpEvent event) {
		if(EnchantHelper.exists(Magic.jump)) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				if (player.worldObj.isRemote) {
					EnchantmentJump.activate(player);
				}
			}
		}
	}

	@ForgeSubscribe
	public void onEntityFall(LivingFallEvent event) {
		if(EnchantHelper.exists(Magic.glide) || EnchantHelper.exists(Magic.fall) || EnchantHelper.exists(Magic.flight)) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				if (!player.worldObj.isRemote) {
					EnchantmentGlide.damage(player, rand);
					EnchantmentFallDamage.activate(event);
	
					if (EnchantHelper.hasEnchantment(Magic.flight, player)) {
						event.setCanceled(true);
					}
				}
			}
		}
	}

	@ForgeSubscribe
	public void onLivingHurt(LivingHurtEvent event) {
		if(EnchantHelper.exists(Magic.fire)) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
	
				EnchantmentFire.testForFireDamage(event);
			}
		}
	}

	@ForgeSubscribe
	public void onLivingAttack(LivingAttackEvent event) {
		if(EnchantHelper.exists(Magic.fire)) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				EnchantmentFire.activate(player, event);
			}
		}
	}

	@ForgeSubscribe
	public void onAttackEntity(AttackEntityEvent event) {
		if(EnchantHelper.exists(Magic.fire) || EnchantHelper.exists(Magic.poison) || EnchantHelper.exists(Magic.punch)) {
			EntityPlayer player = event.entityPlayer;
			Entity target = event.target;
			if (!player.worldObj.isRemote) {
				if (MaricultureHandlers.mirror.containsEnchantedItems(player)) {
					EnchantmentFire.onAttack(player, target);
					EnchantmentPoison.onAttack(player, target);
					EnchantmentPunch.onAttack(player, target);
				}
			}
		}
	}

	@ForgeSubscribe
	public void onEntityInteract(EntityInteractEvent event) {
		if(EnchantHelper.exists(Magic.fire) || EnchantHelper.exists(Magic.poison)) {
			EntityPlayer player = (EntityPlayer) event.entity;
			Entity target = event.target;
			if (!player.worldObj.isRemote) {
				if (MaricultureHandlers.mirror.containsEnchantedItems(player)) {
					EnchantmentFire.onRightClick(player, target);
					EnchantmentPoison.onRightClick(player, target);
				}
			}
		}
	}

	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(EnchantHelper.exists(Magic.blink)) {
			if (event.entityPlayer.worldObj.isRemote && event.action == Action.RIGHT_CLICK_AIR) {
				EnchantmentBlink.sendPacket(event.entityPlayer);
			}
		}
	}

	@ForgeSubscribe
	public void onWorldUpdate(WorldEvent event) {
		if(EnchantHelper.exists(Magic.clock)) {
			World world = event.world;
			if (!world.isRemote && world.provider.isSurfaceWorld()) {
				if (world.isDaytime()) {
					EnchantmentClock.activate(world, Jewelry.NIGHT, 18000);
				} else {
					EnchantmentClock.activate(world, Jewelry.DAY, 6000);
				}
			}
		}
	}

	@ForgeSubscribe
	public void onPlayerDeath(LivingDeathEvent event) {
		if(EnchantHelper.exists(Magic.resurrection)) {
			EnchantmentResurrection.activate(event);
		}
	}
}

package mariculture.magic;

import java.util.Random;

import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.helpers.cofh.ItemHelper;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MagicEventHandler {
	private Random rand = new Random();
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if(event.entity instanceof EntityPlayer && !PlayerHelper.isFake((EntityPlayer)event.entity)) {
			World world = event.entity.worldObj;
			EntityPlayer player = (EntityPlayer) event.entity;
			if(world.isRemote) {
				if(EnchantHelper.exists(Magic.glide)) 	EnchantmentGlide.activate(player);
				if(EnchantHelper.exists(Magic.speed))	EnchantmentSpeed.activate(player);
				if(EnchantHelper.exists(Magic.spider)) 	EnchantmentSpider.activate(player);
				if(EnchantHelper.exists(Magic.flight))	EnchantmentFlight.damage(player);
			} else {
				if(EnchantHelper.exists(Magic.oneRing))	EnchantmentOneRing.activate(player);
				if(EnchantHelper.exists(Magic.hungry))	EnchantmentNeverHungry.activate(player);
				if(EnchantHelper.exists(Magic.health)) 	EnchantmentHealth.activate(player);
			}
			
			if(!player.capabilities.isCreativeMode) {
				if(EnchantHelper.hasEnchantment(Magic.repair, player)) EnchantmentRestore.activate(player);
			}
		}
	}

	@SubscribeEvent
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

	@SubscribeEvent
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

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if(EnchantHelper.exists(Magic.elemental))
			EnchantmentElemental.onHurt(event);
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(EnchantHelper.hasEnchantment(Magic.blink, event.entityPlayer)) {
			if (event.entityPlayer.worldObj.isRemote && event.action == Action.RIGHT_CLICK_AIR) {
				EnchantmentBlink.sendPacket((EntityClientPlayerMP)event.entityPlayer);
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		if(event.source.getSourceOfDamage() != null && event.source.getSourceOfDamage() instanceof EntityPlayer) {
			EntityLivingBase entity = event.entityLiving;
			EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
			if(!(entity instanceof EntityPlayer)) {
				if(ItemHelper.isPlayerHoldingItem(Magic.magnet, player)) {
					ItemStack magnet = player.getCurrentEquippedItem();
					if(!magnet.hasTagCompound()) {
						magnet.setTagCompound(new NBTTagCompound());
						magnet.stackTagCompound.setString("MobClass", entity.getClass().toString().substring(6));
						magnet.stackTagCompound.setString("MobName", EntityList.getEntityString(entity));
					}
				}
			}
 		}
	}

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if(EnchantHelper.exists(Magic.resurrection)) {
			EnchantmentResurrection.activate(event);
		}
	}
}

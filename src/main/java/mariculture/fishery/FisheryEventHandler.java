package mariculture.fishery;

import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FisheryEventHandler {
	Random rand = new Random();

	@SubscribeEvent
	public void onLivingSpawned(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityCreeper) {
			EntityCreeper creeper = (EntityCreeper) event.entity;
			creeper.tasks.addTask(3, new EntityAIAvoidCatfish(creeper, EntityPlayer.class, 8.0F, 0.25F, 0.3F));
		}
	}

	public static void updateStack(World world, EntityItem entity, int lifespan, ItemStack stack, Random rand) {
		float var2 = MathHelper.floor_double(entity.boundingBox.minY);
		float var4 = (rand.nextFloat() * 2.0F - 1.0F) * entity.width;
		float var5 = (rand.nextFloat() * 2.0F - 1.0F) * entity.width;
		entity.worldObj.spawnParticle("splash", entity.posX + var4, var2 + 1.0F, entity.posZ + var5, entity.motionX,
				entity.motionY, entity.motionZ);
		entity.lifespan = lifespan;
		entity.setEntityItemStack(stack);
	}

	@SubscribeEvent
	public void onFishDeath(ItemExpireEvent event) {
		Random rand = new Random();
		ItemStack item = event.entityItem.getEntityItem();

		if (item.getItem() instanceof ItemFishy) {

			if (item.hasTagCompound() && !Fishing.fishHelper.isEgg(item)) {
				int fish = item.stackTagCompound.getInteger("SpeciesID");
				FishSpecies species = Fishing.fishHelper.getSpecies(fish);
				if(species.getGroup() != EnumFishGroup.NETHER) {
					if(event.entityItem.isInsideOfMaterial(Material.water)) {
						event.setCanceled(true);
						return;
					}
				}
				
				ItemStack stack = new ItemStack(Fishery.fishyFood, 1, fish);
				
				if (stack != null) {
					updateStack(event.entityItem.worldObj, event.entityItem, 6000, stack, rand);
					event.setCanceled(true);
				}
			}

		}
	}

	@SubscribeEvent
	public void onKillSquid(LivingDropsEvent event) {
		if (event.entity instanceof EntitySquid) {
			EntitySquid entity = (EntitySquid) event.entity;
			ItemStack squid = new ItemStack(Fishery.fishyFood, 1, Fishery.squid.fishID);
			event.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, squid));
			if (event.lootingLevel > 0) {
				for (int i = 0; i < event.lootingLevel; i++) {
					if (rand.nextInt(3) == 0) {
						event.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, squid));
					}
				}
			}

		}
	}
}

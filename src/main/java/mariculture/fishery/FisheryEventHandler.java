package mariculture.fishery;

import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.util.CachedCoords;
import mariculture.core.config.FishMechanics;
import mariculture.core.config.GeneralStuff;
import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PearlColor;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketParticle;
import mariculture.core.network.PacketParticle.Particle;
import mariculture.fishery.items.ItemArmorFishingHat;
import mariculture.world.EntityRockhopper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FisheryEventHandler {
    Random rand = new Random();

    public static void updateStack(World world, EntityItem entity, int lifespan, ItemStack stack, Random rand) {
        float var2 = MathHelper.floor_double(entity.boundingBox.minY);
        float var4 = (rand.nextFloat() * 2.0F - 1.0F) * entity.width;
        float var5 = (rand.nextFloat() * 2.0F - 1.0F) * entity.width;
        PacketHandler.sendAround(new PacketParticle(Particle.SPLASH, 8, entity.posX - 0.5, entity.posY - 0.5, entity.posZ - 0.5), world.provider.dimensionId, entity.posX, entity.posY, entity.posZ);
        entity.lifespan = lifespan;
        entity.setEntityItemStack(stack);
    }

    @SubscribeEvent
    public void onFishDeath(ItemExpireEvent event) {
        Random rand = new Random();
        ItemStack item = event.entityItem.getEntityItem();
        FishSpecies species = Fishing.fishHelper.getSpecies(item);
        if (species != null) {
            if (!species.isLavaFish()) {
                if (event.entityItem.isInsideOfMaterial(Material.water)) {
                    event.setCanceled(true);
                    return;
                }
            }

            ItemStack stack = species.getRawForm(item.stackSize);
            if (stack != null) {
                updateStack(event.entityItem.worldObj, event.entityItem, 6000, stack, rand);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onKillSquid(LivingDropsEvent event) {
        if (FishMechanics.SQUID_DROP_CALAMARI) {
            if (event.entity instanceof EntitySquid) {
                EntitySquid entity = (EntitySquid) event.entity;
                ItemStack squid = new ItemStack(Items.fish, 1, Fish.squid.getID());
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
    
    public static Multimap<Integer, CachedCoords> invalid_spawns = HashMultimap.create();
    
    @SubscribeEvent
    public void onLivingAttemptSpawn(CheckSpawn event) {
    	if (event.entityLiving instanceof EntityMob) {
    		if (invalid_spawns.get(event.world.provider.dimensionId).contains(new CachedCoords((int)event.x, (int)event.y, (int)event.z))) {
    			event.setResult(Result.DENY);
    		}
    	}
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (GeneralStuff.FISHING_GEAR_CHANCE > 0 && event.entity instanceof EntityZombie) {
            if (event.world.rand.nextFloat() - 0.1F < GeneralStuff.FISHING_GEAR_CHANCE) {
                EntityZombie zombie = (EntityZombie) event.entity;
                if (zombie.getEquipmentInSlot(0) == null && zombie.getEquipmentInSlot(ArmorSlot.HAT) == null) {
                    if (event.world.rand.nextInt(100) == 0) {
                        zombie.setCurrentItemOrArmor(0, new ItemStack(Fishery.rodTitanium));
                    } else if (event.world.rand.nextInt(33) == 0) {
                        zombie.setCurrentItemOrArmor(0, new ItemStack(Fishery.rodWood));
                    } else zombie.setCurrentItemOrArmor(0, new ItemStack(Fishery.rodReed));

                    zombie.setCurrentItemOrArmor(ArmorSlot.HAT, ItemArmorFishingHat.getDyed(event.world.rand.nextInt(PearlColor.COUNT)));
                    zombie.setEquipmentDropChance(0, GeneralStuff.FISHING_ROD_DROP_CHANCE);
                    zombie.setEquipmentDropChance(ArmorSlot.HAT, GeneralStuff.FISHING_HAT_DROP_CHANCE);
                    zombie.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 6000, 1));
                    zombie.addPotionEffect(new PotionEffect(Potion.resistance.id, 18000, 2));
                    zombie.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 18000, 2));
                }
            }
        }
    }
}

package joshie.mariculture.fishery;

import java.util.Random;

import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.network.PacketHandler;
import joshie.mariculture.core.network.PacketParticle;
import joshie.mariculture.core.network.PacketParticle.Particle;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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

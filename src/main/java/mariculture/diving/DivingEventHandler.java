package mariculture.diving;

import mariculture.core.Core;
import mariculture.core.config.GeneralStuff;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.WaterMeta;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DivingEventHandler {
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            if (!event.entity.worldObj.isRemote) {
                Snorkel.init((EntityPlayer) event.entity);
                if (GeneralStuff.HARDCORE_DIVING > 0) {
                    if (event.entity.worldObj.getTotalWorldTime() % 5 == 0) {
                        HardcoreDiving.init((EntityPlayer) event.entity);
                    }
                }
            } else {
                DivingBoots.init((EntityPlayer) event.entity);
                LifeJacket.init((EntityPlayer) event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onBreaking(BreakSpeed event) {
        EntityPlayer player = event.entityPlayer;
        boolean isValid = false;

        if (ForgeHooks.canHarvestBlock(event.block, player, event.metadata)) {
            if (player.isInsideOfMaterial(Material.water)) {
                // Diving Pants
                if (PlayerHelper.hasArmor(player, ArmorSlot.LEG, Diving.divingPants)) {
                    event.newSpeed = event.originalSpeed * 2;
                    if (event.block == Core.water && event.metadata == WaterMeta.OYSTER) {
                        event.newSpeed = event.originalSpeed * 64;
                    }
                }
            }
        }
    }

    private boolean isAllowed(ItemStack stack, ItemStack stack2) {
        if (stack != null && stack.getItem() == Diving.snorkel) {
            return stack2 == null || stack2.getItem() != Items.enchanted_book;
        }

        return true;
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (!isAllowed(event.left, event.right) || !isAllowed(event.right, event.left)) {
            event.setCanceled(true);
        }
    }
}

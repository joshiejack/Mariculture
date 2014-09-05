package joshie.maritech.handlers;

import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.PlayerHelper;
import joshie.mariculture.core.lib.ArmorSlot;
import joshie.mariculture.core.lib.WaterMeta;
import joshie.maritech.extensions.modules.ExtensionDiving;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ScubaEvent {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onFogRender(FogDensity event) {
        if (event.block.getMaterial() == Material.water) {
            if (event.entity instanceof EntityPlayer && event.entity == ClientHelper.getPlayer()) {
                EntityPlayer player = ClientHelper.getPlayer();
                if (player.inventory.armorInventory[3] != null) {
                    if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, ExtensionDiving.scubaMask)) {
                        event.density = 0.0F;
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            if (!event.entity.worldObj.isRemote) {
                if (event.entity.worldObj.getWorldTime() % 5 == 0) {
                    ScubaTank.init((EntityPlayer) event.entity);
                    ScubaMask.damage((EntityPlayer) event.entity);
                }
            } else {
                ScubaFin.init((EntityPlayer) event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onBreaking(BreakSpeed event) {
        EntityPlayer player = event.entityPlayer;
        if (ForgeHooks.canHarvestBlock(event.block, player, event.metadata)) {
            if (player.isInsideOfMaterial(Material.water)) {
                // Scuba Suit
                if (PlayerHelper.hasArmor(player, ArmorSlot.LEG, ExtensionDiving.scubaSuit)) {
                    event.newSpeed = event.originalSpeed * 4;

                    if (event.block == Core.water && event.metadata == WaterMeta.OYSTER) {
                        event.newSpeed = event.originalSpeed * 128;
                    }
                }

                if (PlayerHelper.hasArmor(player, ArmorSlot.FEET, ExtensionDiving.swimfin) && !player.onGround) {
                    event.newSpeed *= 5;
                }
            }
        }
    }
}

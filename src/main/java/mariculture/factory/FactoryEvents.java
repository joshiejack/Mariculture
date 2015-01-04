package mariculture.factory;

import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FactoryEvents {
    @SubscribeEvent
    public void HarvestSpeed(BreakSpeed event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (Item.getItemFromBlock(event.block) == Item.getItemFromBlock(Factory.customBlock)) {
                if (player.getCurrentEquippedItem() != null) {
                    if (player.getCurrentEquippedItem().getItem() instanceof ItemPickaxe) {
                        event.newSpeed = event.originalSpeed * 3F;
                    }

                    if (player.getCurrentEquippedItem().getItem() instanceof ItemAxe) {
                        event.newSpeed = event.originalSpeed * 2F;
                    }

                    if (player.getCurrentEquippedItem().getItem() instanceof ItemFishy) {
                        event.newSpeed = event.originalSpeed * 5F;
                    }
                }
            }
        }
    }
}
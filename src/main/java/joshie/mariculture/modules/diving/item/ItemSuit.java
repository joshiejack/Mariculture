package joshie.mariculture.modules.diving.item;

import joshie.mariculture.core.helpers.EntityHelper;
import joshie.mariculture.core.util.item.ItemArmorMC;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static joshie.mariculture.modules.diving.Diving.ARMOR_DIVING;
import static net.minecraft.inventory.EntityEquipmentSlot.LEGS;

public class ItemSuit extends ItemArmorMC<ItemSuit> {
    public ItemSuit() {
        super(ARMOR_DIVING, 0, LEGS);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBreaking(BreakSpeed event) {
        EntityPlayer player = event.getEntityPlayer();
        if (ForgeHooks.canHarvestBlock(event.getState().getBlock(), player, player.worldObj, event.getPos())) {
            if (player.isInsideOfMaterial(Material.WATER)) {
                if (EntityHelper.hasArmor(player, EntityEquipmentSlot.LEGS, this)) {
                    event.setNewSpeed(event.getOriginalSpeed() * 2F);
                }
            }
        }
    }
}

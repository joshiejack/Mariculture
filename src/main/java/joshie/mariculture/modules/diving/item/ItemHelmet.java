package joshie.mariculture.modules.diving.item;

import joshie.mariculture.core.helpers.ClientHelper;
import joshie.mariculture.core.helpers.EntityHelper;
import joshie.mariculture.core.util.item.ItemArmorMC;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.modules.diving.Diving.ARMOR_DIVING;
import static net.minecraft.inventory.EntityEquipmentSlot.HEAD;

public class ItemHelmet extends ItemArmorMC<ItemHelmet> {
    public ItemHelmet() {
        super(ARMOR_DIVING, 0, HEAD);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onFogRender(FogDensity event) {
        EntityPlayer player = ClientHelper.getPlayer();
        if (event.getEntity() == player) {
            boolean inWater = EntityHelper.hasArmor(player, EntityEquipmentSlot.HEAD, this) && (player.isInsideOfMaterial(Material.WATER) || EntityHelper.isInWater(player));
            if (inWater) {
                event.setDensity(0F);
                event.setCanceled(true);
            }
        }
    }
}

package joshie.mariculture.core.handlers;

import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.helpers.EnchantHelper;
import joshie.mariculture.core.helpers.KeyHelper;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.network.PacketHandler;
import joshie.mariculture.core.network.PacketJewelrySwap;
import joshie.mariculture.core.util.MCTranslate;
import joshie.mariculture.magic.Magic;
import joshie.mariculture.magic.enchantments.EnchantmentFlight;
import joshie.mariculture.magic.enchantments.EnchantmentGlide;
import joshie.mariculture.magic.enchantments.EnchantmentSpider;
import joshie.mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class ClientFMLEvents {
    @SubscribeEvent
    public void onKeyPress(KeyInputEvent event) {
        EntityPlayer player = ClientHelper.getPlayer();
        if (KeyHelper.isActivateKeyPressed()) {
            ItemStack selected = ClientHelper.getHeldItem();
            if (selected != null && selected.getItem() instanceof ItemJewelry) {
                PacketHandler.sendToServer(new PacketJewelrySwap(ClientHelper.getPlayer().inventory.currentItem));
            }

            if (Modules.isActive(Modules.magic)) {
                if (KeyHelper.isToggleKeyPressed() && player.capabilities.isFlying && EnchantHelper.hasEnchantment(Magic.flight, player)) {
                    if (EnchantmentFlight.mode < EnchantmentFlight.maxMode) {
                        EnchantmentFlight.mode++;
                    } else {
                        EnchantmentFlight.mode = 0;
                    }

                    ClientHelper.addToChat(MCTranslate.translate("flight") + (EnchantmentFlight.mode + 1));
                    EnchantmentFlight.set(EnchantHelper.getEnchantStrength(Magic.flight, player), player);
                }
            }
        }

        if (KeyHelper.isToggleKeyPressed()) {
            if (Modules.isActive(Modules.magic)) {
                if (KeyHelper.isToggleKeyPressed() && player.isSneaking() && EnchantHelper.hasEnchantment(Magic.spider, player)) {
                    EnchantmentSpider.toggledOn = !EnchantmentSpider.toggledOn;
                    ClientHelper.addToChat(EnchantmentSpider.getChat());
                } else if (KeyHelper.isToggleKeyPressed() && EnchantHelper.hasEnchantment(Magic.glide, player)) {
                    EnchantmentGlide.toggle();
                }
            }
        }
    }
}

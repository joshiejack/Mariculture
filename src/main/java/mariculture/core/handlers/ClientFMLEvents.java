package mariculture.core.handlers;

import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketJewelrySwap;
import mariculture.core.util.MCTranslate;
import mariculture.lib.helpers.ClientHelper;
import mariculture.magic.Magic;
import mariculture.magic.enchantments.EnchantmentFlight;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentSpider;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class ClientFMLEvents {
    @SubscribeEvent
    public void onKeyPress(KeyInputEvent event) {
        if (KeyHelper.isActivateKeyPressed()) {
            ItemStack selected = ClientHelper.getHeldItem();
            if (selected != null && selected.getItem() instanceof ItemJewelry) {
                PacketHandler.sendToServer(new PacketJewelrySwap(ClientHelper.getPlayer().inventory.currentItem));
            }

            if (Modules.isActive(Modules.magic)) {
                EntityPlayer player = ClientHelper.getPlayer();
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
                EntityPlayer player = ClientHelper.getPlayer();
                if (player.isSneaking() && EnchantHelper.hasEnchantment(Magic.spider, player)) {
                    EnchantmentSpider.toggledOn = !EnchantmentSpider.toggledOn;
                    ClientHelper.addToChat(EnchantmentSpider.getChat());
                } else if (EnchantHelper.hasEnchantment(Magic.glide, player)) {
                    EnchantmentGlide.toggle();
                }
            }
        }
    }
}

package mariculture.core.handlers;

import mariculture.Mariculture;
import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.network.PacketJewelrySwap;
import mariculture.magic.Magic;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ClientFMLEvents {
	@SubscribeEvent
	public void onKeyPress(KeyInputEvent event) {
		ItemStack selected = ClientHelper.getHeldItem();
		if(selected != null && selected.getItem() instanceof ItemJewelry && ClientHelper.isActivateKeyPressed()) {
			Mariculture.packets.sendToServer(new PacketJewelrySwap(ClientHelper.getPlayer().inventory.currentItem));
		}
	}
}

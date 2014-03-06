package mariculture.core.handlers;

import mariculture.Mariculture;
import mariculture.core.helpers.ClientHelper;
import mariculture.core.network.PacketJewelrySwap;
import mariculture.magic.enchantments.EnchantmentRestore;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;

public class ClientFMLEvents {
	@SubscribeEvent
	public void onKeyPress(KeyInputEvent event) {
		ItemStack selected = ClientHelper.getHeldItem();
		if(selected != null && selected.getItem() instanceof ItemJewelry && ClientHelper.isActivateKeyPressed()) {
			Mariculture.packets.sendToServer(new PacketJewelrySwap(ClientHelper.getPlayer().inventory.currentItem));
		}
	}
}

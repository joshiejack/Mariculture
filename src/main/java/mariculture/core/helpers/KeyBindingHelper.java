package mariculture.core.helpers;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;

public class KeyBindingHelper {

	public static boolean inFocus() {
		return FMLClientHandler.instance().getClient().inGameHasFocus;
	}

	public static EntityPlayer getPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}

	public static void addToChat(String str) {
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().addToSentMessages(str);
	}

}

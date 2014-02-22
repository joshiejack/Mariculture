package mariculture.core.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import cpw.mods.fml.client.FMLClientHandler;

public class ClientHelper {
	public static boolean inFocus() {
		return FMLClientHandler.instance().getClient().inGameHasFocus;
	}

	public static EntityPlayer getPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}
	
	public static void addToChatAndTranslate(String str) {
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation(str, new Object[0]));
	}

	public static void addToChat(String str) {
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(str));
	}
}

package mariculture.core.helpers;

import mariculture.core.handlers.KeyBindingHandler;
import mariculture.core.lib.Modules;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;

public class KeyHelper {
	public static boolean ACTIVATE_PRESSED;
	public static boolean TOGGLE_DOWN;
	public static boolean FLUDD_KEY_DOWN;
	
	public static boolean inFocus() {
		return FMLClientHandler.instance().getClient().inGameHasFocus;
	}

	public static EntityPlayer getPlayer() {
		return FMLClientHandler.instance().getClient().thePlayer;
	}
	
	public static void addToChat(String str) {
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(str);
	}
}

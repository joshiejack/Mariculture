package mariculture.core.helpers;

import mariculture.core.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	
	public static ItemStack getHeldItem() {
		return getPlayer().getCurrentEquippedItem();
	}
	
	public static void addToChatAndTranslate(String str) {
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation(str, new Object[0]));
	}

	public static void addToChat(String str) {
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(str));
	}
	
	public static boolean isToggleKeyPressed() {
		return (GameSettings.isKeyDown(ClientProxy.key_toggle));
	}
	
	public static boolean isActivateKeyPressed() {
		return (GameSettings.isKeyDown(ClientProxy.key_activate));
	}
	
	public static boolean isSprintingPressed() {
		return GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSprint);
	}

	public static boolean isForwardPressed() {
		return GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward);
	}
}

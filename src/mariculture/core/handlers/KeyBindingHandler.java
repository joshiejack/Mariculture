package mariculture.core.handlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.EnumSet;

import mariculture.core.helpers.KeyHelper;
import mariculture.core.lib.Modules;
import mariculture.core.network.Packet106SwapJewelry;
import mariculture.factory.Factory;
import mariculture.factory.FactoryEvents;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeyBindingHandler extends KeyHandler {
	private static KeyBinding boost = (Modules.magic.isActive())? new KeyBinding(StatCollector.translateToLocal("key.boost"), Keyboard.KEY_LCONTROL): null;
	private static KeyBinding toggle = (Modules.magic.isActive())? new KeyBinding(StatCollector.translateToLocal("key.toggle"), Keyboard.KEY_Y): null;
	public static KeyBinding fludd = (Modules.factory.isActive())? new KeyBinding(StatCollector.translateToLocal("key.fludd"), Keyboard.KEY_V): null;

	public KeyBindingHandler() {
		super(new KeyBinding[] { boost, toggle, fludd }, new boolean[] { false, false, true });
	}

	@Override
	public String getLabel() {
		return "MaricultureKeyBindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if (FMLClientHandler.instance().getClient().inGameHasFocus) {
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			GuiNewChat chat = FMLClientHandler.instance().getClient().ingameGUI.getChatGUI();
			boolean isSneaking = player.isSneaking();
			
			if(isPressed(boost)) {
				KeyHelper.ACTIVATE_PRESSED = true;
			}
			
			if(isPressed(fludd )&& !GameSettings.isKeyDown(toggle) && !isSneaking) {
				FactoryEvents.activateSquirt(player);
			}
		}
	}
	
	public boolean isPressed(KeyBinding kb) {
		if(kb == null)
			return false;
		
		return (kb == boost && Modules.magic.isActive() || kb == fludd && Modules.factory.isActive() 
				|| kb == toggle && (Modules.factory.isActive() || Modules.magic.isActive()));
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if (FMLClientHandler.instance().getClient().inGameHasFocus && tickEnd) {
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			GuiNewChat chat = FMLClientHandler.instance().getClient().ingameGUI.getChatGUI();
			boolean isSneaking = player.isSneaking();
			
			if(isPressed(boost)) {
				KeyHelper.ACTIVATE_PRESSED = false;
				if (player instanceof EntityClientPlayerMP) {
					switchJewelry((EntityClientPlayerMP) player);
				}
			}


			if (isPressed(toggle) && GameSettings.isKeyDown(fludd)) {
				boolean cont = false;
				for (int i = 0; i < 4; i++) {
					if (player.inventory.armorInventory[i] != null) {
						if (player.inventory.armorInventory[i].itemID == Factory.fludd.itemID) {
							if (player.inventory.armorInventory[i].hasTagCompound()) {
								int mode = player.inventory.armorInventory[i].stackTagCompound.getInteger("mode");
								mode++;
								if (mode > 3) {
									mode = 0;
								}

								chat.printChatMessage(StatCollector.translateToLocal("mariculture.string.fludd.mode." + mode));
								player.inventory.armorInventory[i].stackTagCompound.setInteger("mode", mode);
							}
						}
					}
				}
				
				return;
			}

			if (isPressed(toggle)) {
				if (EnchantmentSpider.activated && isSneaking) {
					if (EnchantmentSpider.toggledOn == false) {
						chat.printChatMessage(StatCollector.translateToLocal("mariculture.string.enabledSpider"));
						EnchantmentSpider.toggledOn = true;
						return;
					}

					if (isSneaking && EnchantmentSpider.toggledOn == true) {
						chat.printChatMessage(StatCollector.translateToLocal("mariculture.string.disabledSpider"));
						EnchantmentSpider.toggledOn = false;
					}
				}

				if (EnchantmentGlide.hasGlide > 0 && !isSneaking) {
					if (EnchantmentGlide.toggleOn == 0) {
						chat.printChatMessage(StatCollector.translateToLocal("mariculture.string.enabledGlide"));
						EnchantmentGlide.toggleOn = 1;
						EnchantmentGlide.keyCoolDown = 20;

						return;
					} else if (EnchantmentGlide.toggleOn == 1) {
						if (EnchantmentGlide.hasGlide > 0) {
							chat.printChatMessage(StatCollector.translateToLocal("mariculture.string.enabledFastFall"));
							EnchantmentGlide.toggleOn = 2;
						} else {
							chat.printChatMessage(StatCollector.translateToLocal("mariculture.string.disabledGlide"));
							EnchantmentGlide.toggleOn = 0;
						}

						EnchantmentGlide.keyCoolDown = 20;
						return;
					} else if (EnchantmentGlide.toggleOn == 2) {
						chat.printChatMessage(StatCollector.translateToLocal("mariculture.string.disabledGlide"));
						EnchantmentGlide.toggleOn = 0;
						EnchantmentGlide.keyCoolDown = 20;
						return;
					}
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	public void switchJewelry(EntityClientPlayerMP player) {
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) {
			player.sendQueue.addToSendQueue(new Packet106SwapJewelry(0).build());
		} else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
			player.sendQueue.addToSendQueue(new Packet106SwapJewelry(1).build());
		} else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) {
			player.sendQueue.addToSendQueue(new Packet106SwapJewelry(2).build());
		} else {
			player.sendQueue.addToSendQueue(new Packet106SwapJewelry(-1).build());
		}
	}
}
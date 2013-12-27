package mariculture.core.handlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.EnumSet;

import mariculture.core.helpers.KeyHelper;
import mariculture.core.lib.PacketIds;
import mariculture.factory.Factory;
import mariculture.factory.FactoryEvents;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.client.entity.EntityClientPlayerMP;
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
	private static KeyBinding boost = new KeyBinding(StatCollector.translateToLocal("key.boost"), Keyboard.KEY_LCONTROL);
	private static KeyBinding toggle = new KeyBinding(StatCollector.translateToLocal("key.toggle"), Keyboard.KEY_Y);
	public static KeyBinding fludd = new KeyBinding(StatCollector.translateToLocal("key.fludd"), Keyboard.KEY_V);

	public KeyBindingHandler() {
		super(new KeyBinding[] { boost, toggle, fludd }, new boolean[] { false, false, true });
	}

	@Override
	public String getLabel() {
		return "MaricultureKeyBindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if (kb == boost && FMLClientHandler.instance().getClient().inGameHasFocus) {
			KeyHelper.ACTIVATE_PRESSED = true;
		}

		if (kb == fludd && !GameSettings.isKeyDown(toggle) && FMLClientHandler.instance().getClient().inGameHasFocus
				&& !FMLClientHandler.instance().getClient().thePlayer.isSneaking()) {
			FactoryEvents.activateSquirt(FMLClientHandler.instance().getClient().thePlayer);
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if (FMLClientHandler.instance().getClient().inGameHasFocus) {
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;

			if (kb == boost && tickEnd) {
				KeyHelper.ACTIVATE_PRESSED = false;
				if (player instanceof EntityClientPlayerMP) {
					switchJewelry((EntityClientPlayerMP) player);
				}
			}

			if (kb == toggle && tickEnd && GameSettings.isKeyDown(fludd)) {
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

								FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(
										StatCollector.translateToLocal("mariculture.string.fludd.mode." + mode));
								player.inventory.armorInventory[i].stackTagCompound.setInteger("mode", mode);
							}
						}
					}
				}
			}

			if (kb == toggle && tickEnd) {
				boolean isSneaking = player.isSneaking();

				if (EnchantmentSpider.activated && isSneaking) {
					if (EnchantmentSpider.toggledOn == false) {
						FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(
								StatCollector.translateToLocal("mariculture.string.enabledSpider"));
						EnchantmentSpider.toggledOn = true;
						return;
					}

					if (FMLClientHandler.instance().getClient().thePlayer.isSneaking()
							&& EnchantmentSpider.toggledOn == true
							&& tickEnd) {
						FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(
								StatCollector.translateToLocal("mariculture.string.disabledSpider"));
						EnchantmentSpider.toggledOn = false;
					}
				}

				if (EnchantmentGlide.hasGlide > 0 && !isSneaking) {
					if (EnchantmentGlide.toggleOn == 0) {
						FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(
								StatCollector.translateToLocal("mariculture.string.enabledGlide"));

						EnchantmentGlide.toggleOn = 1;
						EnchantmentGlide.keyCoolDown = 20;

						return;
					} else if (EnchantmentGlide.toggleOn == 1) {
						if (EnchantmentGlide.hasGlide > 0) {
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(
									StatCollector.translateToLocal("mariculture.string.enabledFastFall"));

							EnchantmentGlide.toggleOn = 2;
						} else {
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(
									StatCollector.translateToLocal("mariculture.string.disabledGlide"));
							EnchantmentGlide.toggleOn = 0;
						}

						EnchantmentGlide.keyCoolDown = 20;
						return;
					} else if (EnchantmentGlide.toggleOn == 2) {
						FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(
								StatCollector.translateToLocal("mariculture.string.disabledGlide"));
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
			sendPacket(player, 0);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
			sendPacket(player, 1);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) {
			sendPacket(player, 2);
		} else {
			sendPacket(player, -1);
		}
	}

	private void sendPacket(EntityClientPlayerMP player, int slot) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(PacketIds.SWAP_JEWELRY);
			outputStream.writeInt(slot);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		player.sendQueue.addToSendQueue(packet);
	}
}
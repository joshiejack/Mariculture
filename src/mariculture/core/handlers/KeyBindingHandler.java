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
	public static KeyBinding boost = new KeyBinding(StatCollector.translateToLocal("key.boost"), Keyboard.KEY_LCONTROL);
	public static KeyBinding toggle = new KeyBinding(StatCollector.translateToLocal("key.toggle"), Keyboard.KEY_Y);

	public KeyBindingHandler() {
		super(new KeyBinding[] { boost, toggle }, new boolean[] { false, false });
	}

	@Override
	public String getLabel() {
		return "Mariculture Default Key Bindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		EntityPlayer player = KeyHelper.getPlayer();
		if(KeyHelper.inFocus()) {
			KeyHelper.ACTIVATE_PRESSED = kb == boost;
			KeyHelper.TOGGLE_DOWN = kb == toggle;
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		KeyHelper.ACTIVATE_PRESSED = false;
		KeyHelper.TOGGLE_DOWN = false;
		
		if(KeyHelper.inFocus() && tickEnd) {
			EntityPlayer player = KeyHelper.getPlayer();
			boolean isSneaking = player.isSneaking();
			
			if(Modules.magic.isActive()) {
				if(kb == boost)
					switchJewelry((EntityClientPlayerMP) player);
				if(kb == toggle) {
					if(isSneaking) {
						if (EnchantmentSpider.activated) {
							EnchantmentSpider.toggledOn = !EnchantmentSpider.toggledOn;
							KeyHelper.addToChat(EnchantmentSpider.getChat());
						}
					} else {
						if(EnchantmentGlide.keyCoolDown > 0)
							EnchantmentGlide.keyCoolDown--;
						if (EnchantmentGlide.hasGlide > 0 && EnchantmentGlide.keyCoolDown <= 0) {
							EnchantmentGlide.toggle();
						}
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
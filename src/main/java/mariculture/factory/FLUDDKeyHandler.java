package mariculture.factory;

import java.util.EnumSet;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.KeyHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
//TODO: Move FLUDD Key Handling to new Events System
/**
public class FLUDDKeyHandler extends KeyHandler {
	public static KeyBinding fludd = new KeyBinding(StatCollector.translateToLocal("key.fludd"), Keyboard.KEY_V);

	public FLUDDKeyHandler() {
		super(new KeyBinding[] {  fludd }, new boolean[] { true });
	}

	@Override
	public String getLabel() {
		return "Mariculture FLUDD Key Binding";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		EntityPlayer player = KeyBindingHelper.getPlayer();
		if(KeyBindingHelper.inFocus()){
			if(kb == fludd) {
				KeyHelper.FLUDD_KEY_DOWN = true;
				
				if(!KeyHelper.TOGGLE_DOWN && !player.isSneaking())
					FactoryEvents.activateSquirt(player);
			}
		}
	}
	
	

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if(KeyBindingHelper.inFocus() && tickEnd) {
			EntityPlayer player = KeyBindingHelper.getPlayer();
			KeyHelper.FLUDD_KEY_DOWN = false;
			if (KeyHelper.TOGGLE_DOWN && kb == fludd) {
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

								KeyBindingHelper.addToChat(StatCollector.translateToLocal("mariculture.string.fludd.mode." + mode));
								player.inventory.armorInventory[i].stackTagCompound.setInteger("mode", mode);
							}
						}
					}
				}
				
				return;
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
} **/
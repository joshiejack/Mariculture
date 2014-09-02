package joshie.mariculture.core.helpers;

import joshie.mariculture.core.ClientProxy;
import net.minecraft.client.settings.GameSettings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyHelper {

    public static boolean isToggleKeyPressed() {
        return GameSettings.isKeyDown(ClientProxy.key_toggle);
    }

    public static boolean isActivateKeyPressed() {
        return GameSettings.isKeyDown(ClientProxy.key_activate);
    }

}

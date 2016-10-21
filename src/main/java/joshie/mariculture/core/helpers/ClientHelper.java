package joshie.mariculture.core.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientHelper {
    private static final KeyBinding forward = Minecraft.getMinecraft().gameSettings.keyBindForward;

    public static boolean isForwardPressed() {
        return GameSettings.isKeyDown(forward);
    }

    public static EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}

package maritech.lib;

import mariculture.Mariculture;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class SpecialIcons {
    public static IIcon sluiceAdvanced;
    public static IIcon sluiceAdvancedBack;
    public static IIcon sluiceAdvancedUp;
    public static IIcon sluiceAdvancedDown;
    public static IIcon sluiceBack;
    public static IIcon sluiceUp;
    public static IIcon sluiceDown;
    public static IIcon generatorBack;
    public static IIcon[] incubatorIcons;

    public static void registerIcons(IIconRegister register) {
        sluiceBack = register.registerIcon(MTModInfo.MODPATH + ":sluiceBack");
        sluiceUp = register.registerIcon(MTModInfo.MODPATH + ":sluiceUp");
        sluiceDown = register.registerIcon(MTModInfo.MODPATH + ":sluiceDown");
        sluiceAdvancedBack = register.registerIcon(MTModInfo.MODPATH + ":sluiceAdvancedBack");
        sluiceAdvancedUp = register.registerIcon(MTModInfo.MODPATH + ":sluiceAdvancedUp");
        sluiceAdvancedDown = register.registerIcon(MTModInfo.MODPATH + ":sluiceAdvancedDown");
        sluiceAdvanced = register.registerIcon(MTModInfo.MODPATH + ":sluiceAdvancedSide");
        generatorBack = register.registerIcon(MTModInfo.MODPATH + ":generatorBack");
        //Extra Icons for the blocks
        incubatorIcons = new IIcon[2];
        incubatorIcons[0] = register.registerIcon(MTModInfo.MODPATH + ":incubatorBottom");
        incubatorIcons[1] = register.registerIcon(MTModInfo.MODPATH + ":incubatorTopTop");
    }
}

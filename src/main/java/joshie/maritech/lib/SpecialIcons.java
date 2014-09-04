package joshie.maritech.lib;

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

    public static void registerIcons(IIconRegister register) {
        sluiceBack = register.registerIcon(MTModInfo.TEXPATH + ":sluiceBack");
        sluiceUp = register.registerIcon(MTModInfo.TEXPATH + ":sluiceUp");
        sluiceDown = register.registerIcon(MTModInfo.TEXPATH + ":sluiceDown");
        sluiceAdvancedBack = register.registerIcon(MTModInfo.TEXPATH + ":sluiceAdvancedBack");
        sluiceAdvancedUp = register.registerIcon(MTModInfo.TEXPATH + ":sluiceAdvancedUp");
        sluiceAdvancedDown = register.registerIcon(MTModInfo.TEXPATH + ":sluiceAdvancedDown");
        sluiceAdvanced = register.registerIcon(MTModInfo.TEXPATH + ":sluiceAdvancedSide");
        generatorBack = register.registerIcon(MTModInfo.TEXPATH + ":generatorBack");
    }
}

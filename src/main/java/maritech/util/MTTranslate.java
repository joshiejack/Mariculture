package maritech.util;

import net.minecraft.util.StatCollector;

public class MTTranslate {
    public static String translate(String str) {
        return StatCollector.translateToLocal("maritech." + str);
    }
}

package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getDouble;
import static mariculture.core.helpers.ConfigHelper.getInt;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import net.minecraftforge.common.config.Configuration;

public class Enchantments {
    public static class EnchantIds {
        public static int hungry;
        public static int oneRing;
        public static int speed;
        public static int health;
        public static int repair;
        public static int jump;
        public static int spider;
        public static int glide;
        public static int fall;
        public static int resurrection;
        public static int blink;
        public static int flight;
        public static int stepUp;
        public static int elemental;
    }

    public static int JUMPS_PER;
    public static double JUMP_FACTOR;
    public static int SPEED_TICKS;
    public static double SPEED_FACTOR;
    public static int TICK_REPAIR;

    public static void init(Configuration config) {
        setConfig(config);
        setCategory("IDs");
        EnchantIds.blink = getInt("Blink", 70);
        EnchantIds.elemental = getInt("Elemental Affinity", 71);
        EnchantIds.fall = getInt("Fall Resistance", 72);
        EnchantIds.flight = getInt("Superman", 74);
        EnchantIds.glide = getInt("Paraglide", 75);
        EnchantIds.health = getInt("1 Up", 76);
        EnchantIds.jump = getInt("Leapfrog", 77);
        EnchantIds.hungry = getInt("Never Hungry", 78);
        EnchantIds.oneRing = getInt("The One Ring", 79);
        EnchantIds.repair = getInt("Restoration", 82);
        EnchantIds.resurrection = getInt("Reaper", 83);
        EnchantIds.speed = getInt("Sonic the Hedgehog", 84);
        EnchantIds.spider = getInt("Spiderman", 85);
        EnchantIds.stepUp = getInt("Step Up", 86);

        setCategory("Jewelry");
        Jewelry.JEWELRY_OFFLINE = getBoolean("Enable Singleplayer Jewelry Offline Mode", false);
        Jewelry.DROP_JEWELRY = getBoolean("Jewelry Drops on Death", false);

        setCategory("Tweaks");
        JUMPS_PER = getInt("Leapfrog > Jumps per Damage", 10);
        JUMP_FACTOR = getDouble("Leapfrog > Jump Factor", 0.15);
        SPEED_TICKS = getInt("Sonic the Hedgehog > Ticks per Damage", 1200);
        SPEED_FACTOR = getDouble("Sonic the Hedgehog > Speed Factor", 0.025);
        TICK_REPAIR = getInt("Restoration - Ticks between Repair", 100);
    }

    public static class Jewelry {
        public static boolean JEWELRY_OFFLINE;
        public static boolean DROP_JEWELRY;
    }
}

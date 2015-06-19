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

    public static boolean DISABLE_BOOKS_ON_PEARLS;
    public static int JUMPS_PER;
    public static double JUMP_FACTOR;
    public static int SPEED_TICKS;
    public static double SPEED_FACTOR;
    public static int TICK_REPAIR;
    public static boolean ALLOW_MC_ANVIL;
    public static int BLINK_MILLISECONDS;
    public static int RAY_TRACE_DISTANCE;

    public static void init(Configuration config) {
        setConfig(config);
        setCategory("IDs");
        EnchantIds.blink = getInt("Blink", 75);
        EnchantIds.elemental = getInt("Elemental Affinity", 76);
        EnchantIds.fall = getInt("Fall Resistance", 77);
        EnchantIds.flight = getInt("Superman", 78);
        EnchantIds.glide = getInt("Paraglide", 79);
        EnchantIds.health = getInt("1 Up", 80);
        EnchantIds.jump = getInt("Leapfrog", 81);
        EnchantIds.hungry = getInt("Never Hungry", 82);
        EnchantIds.oneRing = getInt("The One Ring", 83);
        EnchantIds.repair = getInt("Restoration", 84);
        EnchantIds.resurrection = getInt("Reaper", 85);
        EnchantIds.speed = getInt("Sonic the Hedgehog", 86);
        EnchantIds.spider = getInt("Spiderman", 87);
        EnchantIds.stepUp = getInt("Step Up", 88);

        setCategory("Jewelry");
        Jewelry.JEWELRY_OFFLINE = getBoolean("Enable Singleplayer Jewelry Offline Mode", false);
        Jewelry.DROP_JEWELRY = getBoolean("Jewelry Drops on Death", false);
        Jewelry.ALTERNATIVE_CELESTIAL_RECIPE = getBoolean("Enable Non-Fish Breeding Recipe for Celestial Mirror", true);

        setCategory("Tweaks");
        JUMPS_PER = getInt("Leapfrog > Jumps per Damage", 10);
        JUMP_FACTOR = getDouble("Leapfrog > Jump Factor", 0.15);
        SPEED_TICKS = getInt("Sonic the Hedgehog > Ticks per Damage", 1200);
        SPEED_FACTOR = getDouble("Sonic the Hedgehog > Speed Factor", 0.05);
        TICK_REPAIR = getInt("Restoration - Ticks between Repair", 100);
        ALLOW_MC_ANVIL = getBoolean("Enable Books being applied to Jewelry", false);
        DISABLE_BOOKS_ON_PEARLS = getBoolean("Disable Pearls in Vanilla Anvil", false);
        BLINK_MILLISECONDS = getInt("Blink > Milliseconds between Blinks", 0);
        RAY_TRACE_DISTANCE = getInt("Blink > Ray Trace Distance", 2000);
    }

    public static class Jewelry {
        public static boolean JEWELRY_OFFLINE;
        public static boolean DROP_JEWELRY;
        public static boolean ALTERNATIVE_CELESTIAL_RECIPE;
    }
}

package mariculture.core.config;

import mariculture.core.lib.config.Category;
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
		EnchantIds.blink = config.get(Category.ENCHANT, "Blink", 70).getInt();
		EnchantIds.elemental = config.get(Category.ENCHANT, "Elemental Affinity", 71).getInt();
		EnchantIds.fall = config.get(Category.ENCHANT, "Fall Resistance", 72).getInt();
		EnchantIds.flight = config.get(Category.ENCHANT, "Superman", 74).getInt();
		EnchantIds.glide = config.get(Category.ENCHANT, "Paraglide", 75).getInt();
		EnchantIds.health = config.get(Category.ENCHANT, "1 Up", 76).getInt();
		EnchantIds.jump = config.get(Category.ENCHANT, "Leapfrog", 77).getInt();
		EnchantIds.hungry = config.get(Category.ENCHANT, "Never Hungry", 78).getInt();
		EnchantIds.oneRing = config.get(Category.ENCHANT, "The One Ring", 79).getInt();
		EnchantIds.repair = config.get(Category.ENCHANT, "Restoration", 82).getInt();
		EnchantIds.resurrection = config.get(Category.ENCHANT, "Reaper", 83).getInt();
		EnchantIds.speed = config.get(Category.ENCHANT, "Sonic the Hedgehog", 84).getInt();
		EnchantIds.spider = config.get(Category.ENCHANT, "Spiderman", 85).getInt();
		EnchantIds.stepUp = config.get(Category.ENCHANT, "Step Up", 86).getInt();

		JUMPS_PER = config.get(Category.EXTRA, "Leapfrog > Jumps per Damage", 10).getInt();
		JUMP_FACTOR = config.get(Category.EXTRA, "Leapfrog > Jump Factor", 0.15).getDouble(0.15);
		SPEED_TICKS = config.get(Category.EXTRA, "Sonic the Hedgehog > Ticks per Damage", 1200).getInt();
		SPEED_FACTOR = config.get(Category.EXTRA, "Sonic the Hedgehog > Speed Factor", 0.025).getDouble(0.025);
		TICK_REPAIR = config.get(Category.EXTRA, "Restoration - Ticks between Repair", 100).getInt();
	}
}

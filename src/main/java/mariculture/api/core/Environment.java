package mariculture.api.core;

import net.minecraft.world.World;

public class Environment {
	public static class Temperature {
		public static int getCoolingSpeed(int temp) {
			return ((temp * -1) + 60) / 5;
		}
	}

	public static class Height {
		// Whether this is considered a cave
		public static boolean isCave(int y) {
			return y <= 48;
		}

		// Whether this is considered deep water
		public static boolean isDeep(int y) {
			return y <= 16;
		}

		// Whether this is considering shallow water
		public static boolean isShallows(int y) {
			return y >= 32 && y <= 75;
		}

		// Whether this height is considered
		public static boolean isOverground(int y) {
			return y >= 64;
		}

		// Whether this height is considered underground
		public static boolean isUnderground(int y) {
			return y <= 64;
		}

		// Whether this is considereed high up
		public static boolean isHigh(int y) {
			return y >= 96;
		}
	}

	public static enum Salinity {
		FRESH, BRACKISH, SALINE
	}

	public static class Time {
		// Get the time in 24000 blocks from the world
		public static int getTime(World world) {
			return (int) (world.getWorldTime() % 24000);
		}

		public static boolean isDay(int time) {
			return time < 12000;
		}

		public static boolean isDawn(int time) {
			return time < 6000;
		}

		public static boolean isNoon(int time) {
			return time >= 6000 && time < 12000;
		}

		public static boolean isDusk(int time) {
			return time >= 12000 && time < 18000;
		}

		public static boolean isMidnight(int time) {
			return time >= 18000;
		}
	}
}

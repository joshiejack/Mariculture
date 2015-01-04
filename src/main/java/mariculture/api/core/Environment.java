package mariculture.api.core;

public class Environment {
    public static class Temperature {
        public static int getCoolingSpeed(int temp) {
            return (temp * -1 + 60) / 5;
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
}

package mariculture.core.util;

import java.util.Random;

public class Rand {
    public static Random rand = new Random();

    public static boolean nextInt(int i) {
        return rand.nextInt(i) == 0;
    }
}

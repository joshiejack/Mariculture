package joshie.mariculture.core.util;

import joshie.mariculture.Mariculture;
import org.apache.logging.log4j.Level;

import java.util.HashMap;

public class Timer {
    private static final HashMap<String, Long> TIMERS = new HashMap<>();

    public static void start(String name) {
        TIMERS.put(name, System.nanoTime());
    }

    public static void end (String name) {
        long then = TIMERS.get(name);
        long difference = System.nanoTime() - then;
        Mariculture.logger.log(Level.INFO, name + " took " + difference + " nano time");
    }
}

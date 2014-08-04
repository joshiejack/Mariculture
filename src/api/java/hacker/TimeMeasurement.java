package hacker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//BY CHYLEX
public final class TimeMeasurement {
    private static final Map<String, Long> timers = new HashMap<String, Long>();

    public static void start(String identifier) {
        timers.put(identifier, System.nanoTime());
    }

    public static long finish(String identifier) {
        return finish(identifier, true);
    }

    public static long finish(String identifier, boolean writeOutput) {
        Long time = timers.remove(identifier);
        if (time == null) return 0;

        long dur = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
        if (dur > 0) System.out.println("Finished process '" + identifier + "' in " + dur + " ms.");
        return dur;
    }
}
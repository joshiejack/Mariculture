package joshie.mariculture;

import joshie.mariculture.lib.MaricultureInfo;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MLogger {
    private static final Logger logger = LogManager.getLogger(MaricultureInfo.MODNAME);

    public static void log(Level level, String message) {
        logger.log(level, message);
    }
}
package mariculture.core.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class LogHandler {
	private static Logger logger = Logger.getLogger("Mariculture");

	public static void init() {
		logger.setParent((Logger) FMLLog.getLogger());
	}

	public static void log(Level level, String message) {
		logger.log(level, message);
	}
}

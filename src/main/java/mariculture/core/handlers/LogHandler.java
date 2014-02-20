package mariculture.core.handlers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLLog;

public class LogHandler {
	public static void log(Level level, String message) {
		FMLLog.getLogger().log(level, message);
	}
}

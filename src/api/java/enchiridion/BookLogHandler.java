package enchiridion;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class BookLogHandler {
	public static void log(Level level, String message) {
		FMLLog.getLogger().log(level, "[Enchiridion] " + message);
	}
}

package mariculture.core.handlers;



import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHandler {
	public static final Logger logger = LogManager.getLogger("Mariculture");
	public static void log(Level level, String message) {
		logger.log(level, message);
	}
}

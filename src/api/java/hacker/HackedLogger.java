package hacker;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.message.MessageFactory;

public class HackedLogger extends Logger {
    private static ArrayList<String> list = new ArrayList();

    protected HackedLogger(LoggerContext context, String name, MessageFactory messageFactory) {
        super(context, name, messageFactory);
    }

    public static HackedLogger getLogger() {
        Logger logger = (Logger) LogManager.getLogger();
        return new HackedLogger(logger.getContext(), logger.getName(), logger.getMessageFactory());
    }

    @Override
    public void error(String message, Throwable t) {
        if (contains(message)) return;
        else super.error(message, t);
    }

    @Override
    public void warn(String message) {
        if (contains(message)) return;
        else super.warn(message);
    }

    private boolean contains(String message) {
        for (String s : list) {
            if (message.contains(s)) return true;
        }

        return false;
    }

    static {
        list.add("Using missing texture");
        list.add("Skipping BlockEntity");
    }
}

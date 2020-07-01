package it.polimi.ingsw.utils;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * The Logger manager.
 */
public class LoggerManager {
    private static final Logger LOGGER = Logger.getLogger(LoggerManager.class.getName());
    /**
     * The constant containing the name of the environment variable that specifies the desired log level.
     *
     * @see java.util.logging.Level
     */
    public static final String LOG_LEVEL_ENV_VAR_NAME = "LOG_LEVEL";

    /**
     * Prevents the LoggerManager to be initialized as a class instance (it must be accessed statically).
     */
    private LoggerManager() {
        throw new IllegalStateException("Do not try to instantiate this utility class");
    }

    /**
     * Sets the log level specified in the environment variable.
     */
    public static void setLogLevel() {
        try {
            setLogLevel(Level.parse(System.getenv(LOG_LEVEL_ENV_VAR_NAME).toUpperCase()));
        }
        catch (NullPointerException e) {
            LOGGER.log(Level.FINEST, "No logging level specified");
        }
        catch (IllegalArgumentException e) {
            LOGGER.log(Level.INFO, "Invalid logging level", e);
        }
    }

    /**
     * Sets the log level.
     *
     * @param level the log level
     */
    public static void setLogLevel(Level level) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(level);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(level);
        }
    }
}

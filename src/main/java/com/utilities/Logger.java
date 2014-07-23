package main.java.com.utilities;

import main.java.com.references.AutomationReferences;
import org.apache.log4j.LogManager;

/**
 * It can be used to log events for debugging purposes.
 * It is a wrapper on Apache log4j.
 */
public class Logger {

    /**
     * instantiation of Logger.
     */
    private org.apache.log4j.Logger logger;

    public static Logger getRootLogger() {
        return Holder.ROOT_LOGGER;
    }

    /**
     * Holder class for loggers which will contain the instances
     * in a thread safe manner.
     */
    private final static class Holder {

        private final static Logger ROOT_LOGGER = new Logger(AutomationReferences.LOGGER_DEFAULT);

    }

    /**
     * ALternate constructor.
     * @param name Name of logger.
     */
    private Logger(String name) {
        org.apache.log4j.Logger logger1 = LogManager.exists(name);
        if (logger1 == null) {
            getRootLogger().error("Unable to find logger with name: " + name);
        }
        else
            this.logger = logger1;
    }

    /**
     * @return The current logging level.
     */
    public String getLogLevel() {
        return this.logger.getLevel().toString();
    }

    /**
     * Reports at ERROR level.
     * @param message String message to append in log file.
     */
    public void error(String message) {
        logger.error(message);
    }

    /**
     * Reports at WARN level.
     * @param message String message to append in log file.
     */
    public void warn(String message) {
        logger.warn(message);
    }

    /**
     * Reports at INFO level.
     * @param message String message to append in log file.
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * Reports at DEBUG level.
     * @param message String message to append in log file.
     */
    public void debug(String message) {
        logger.debug(message);
    }

    /**
     * Reports at TRACE level.
     * @param message String message to append in log file.
     */
    public void trace(String message) {
        logger.trace(message);
    }

    /**
     * Reports at ERROR level.
     * @param message String message to append in log file.
     * @param throwable Caught exception to append in log file.
     */
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Reports at WARN level.
     * @param message String message to append in log file.
     * @param throwable Caught exception to append in log file.
     */
    public void warn(String message, Throwable throwable) {
        logger.warn(message, throwable);
    }

    /**
     * Reports at INFO level.
     * @param message String message to append in log file.
     * @param throwable Caught exception to append in log file.
     */
    public void info(String message, Throwable throwable) {
        logger.info(message, throwable);
    }

    /**
     * Reports at DEBUG level.
     * @param message String message to append in log file.
     * @param throwable Caught exception to append in log file.
     */
    public void debug(String message, Throwable throwable) {
        logger.debug(message, throwable);
    }

    /**
     * Reports at TARCE level.
     * @param message String message to append in log file.
     * @param throwable Caught exception to append in log file.
     */
    public void trace(String message, Throwable throwable) {
        logger.trace(message, throwable);
    }

}

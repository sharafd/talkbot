package logging;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.ConsoleAppender;

import static org.apache.log4j.Logger.getRootLogger;

/**
 * Логирование на консоль.
 */
public final class ConsoleLogger {

    /**
     * Вывод на консоль.
     */
    private static final org.apache.log4j.Logger C_LOG =
            Logger.getLogger(ConsoleLogger.class);

    /**
     * ConsoleLogger.
     */
    private static ConsoleLogger loggerInstance = null;

    /**
     * Создание экземпляр ConsoleLogger.
     *
     * @return ConsoleLogger
     */
    public static synchronized ConsoleLogger getInstance() {
        if (loggerInstance == null) {
            loggerInstance = new ConsoleLogger();
        }
        return loggerInstance;
    }

    /**
     * Инициализация.
     */
    private ConsoleLogger() {

        ConsoleAppender ca = new ConsoleAppender();

        ca.setName("ConsoleLogger");
        ca.setLayout(new PatternLayout("%m%n"));

        // DOS codepage support
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            ca.setEncoding("Cp866");
        }
        ca.setThreshold(Level.INFO);
        ca.activateOptions();

        getRootLogger().addAppender(ca);

    }

    /**
     * Получить доступ и использовать ConsoleLogger.
     *
     * @return ConsoleLogger
     */
    public org.apache.log4j.Logger getLogger() {
        return C_LOG;
    }

}

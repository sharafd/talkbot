package logging;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.log4j.Logger.getRootLogger;

/**
 * Логирование в файл.
 */
public final class FileLogger {

    /**
     * Логирование в файл.
     */
    private static final org.apache.log4j.Logger LOG =
            org.apache.log4j.Logger.getLogger(FileLogger.class);

    /**
     * FileLogger.
     */
    private static FileLogger loggerInstance = null;

    /**
     * Дата-время для имени лога.
     */
    private String now = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss")
                            .format(LocalDateTime.now());

    /**
     * Получить экземпляр FileLogger.
     *
     * @return FileLogger
     */
    public static synchronized FileLogger getInstance() {
        if (loggerInstance == null) {
            loggerInstance = new FileLogger();
        }
        return loggerInstance;
    }

    /**
     * Инициализация.
     */
    private FileLogger() {

        DailyRollingFileAppender fa = new DailyRollingFileAppender();

        fa.setName("FileLogger");
        fa.setFile(now + ".LOG");
        fa.setLayout(new PatternLayout("%d{dd-MM-yy HH:mm:ss} %m%n"));
        fa.setThreshold(Level.DEBUG);
        fa.setAppend(true);
        fa.activateOptions();

        getRootLogger().addAppender(fa);
    }

    /**
     * Получить доступ и использовать FileLogger.
     *
     * @return FileLogger
     */
    public org.apache.log4j.Logger getLogger() {
        return LOG;
    }

}

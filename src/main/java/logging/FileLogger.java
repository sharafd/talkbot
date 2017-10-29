package logging;

import org.apache.log4j.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.log4j.Logger.getRootLogger;

/**
 * Логирование в файл
 */
public class FileLogger {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FileLogger.class);
    private static FileLogger loggerInstance = null;
    private String now = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss").format(LocalDateTime.now());

    public static synchronized FileLogger getInstance() {
        if (loggerInstance == null)
            loggerInstance = new FileLogger();
        return loggerInstance;
    }

    private FileLogger() {

     DailyRollingFileAppender fa = new DailyRollingFileAppender();

     fa.setName("FileLogger");
     fa.setFile( now  + ".log");
     fa.setLayout(new PatternLayout("%d{dd-MM-yy HH:mm:ss} %m%n"));
     fa.setThreshold(Level.INFO);
     fa.setAppend(true);
     fa.activateOptions();

     getRootLogger().addAppender(fa);
    }

    public org.apache.log4j.Logger getLogger() { return log; }

}
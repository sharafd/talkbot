package talk;

import org.apache.log4j.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.log4j.Logger.getRootLogger;

/**
 * Логирование
 */
public class Logger {

    public final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Logger.class);

    private static Logger logger_instance = null;
    private String now = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

    // Java 1.8
  //  private String now = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss").format(LocalDateTime.now());

    public static synchronized Logger getInstance() {
        if (logger_instance == null)
            logger_instance = new Logger();
        return logger_instance;
    }

    private Logger() {

     DailyRollingFileAppender fa = new DailyRollingFileAppender();

     if (System.getProperty("os.name").toLowerCase().contains("windows")) {

        fa.setEncoding("CP1251");
     }

     fa.setName("FileLogger");
     fa.setFile( now  + ".log");
     fa.setLayout(new PatternLayout("%d{dd-MM-yy HH:mm:ss} %m%n"));
     fa.setThreshold(Level.INFO);
     fa.setAppend(true);
     fa.activateOptions();

     getRootLogger().addAppender(fa);

    }
}
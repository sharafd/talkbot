package logging;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.ConsoleAppender;

import static org.apache.log4j.Logger.getRootLogger;


/**
 * Логирование на консоль
 */
public class ConsoleLogger {

    private static final org.apache.log4j.Logger cLog = Logger.getLogger(ConsoleLogger.class);
    private static ConsoleLogger loggerInstance = null;

    public static synchronized ConsoleLogger getInstance() {
        if (loggerInstance == null)
            loggerInstance = new ConsoleLogger();
        return loggerInstance;
    }

    private ConsoleLogger() {

     ConsoleAppender ca = new ConsoleAppender();

     ca.setName("ConsoleLogger");
     ca.setLayout(new PatternLayout("%m%n"));

     // DOS codepage support
     if(System.getProperty("os.name").toLowerCase().contains("windows")) {
         ca.setEncoding("Cp866");
     }

     ca.setThreshold(Level.TRACE);
     ca.activateOptions();

     getRootLogger().addAppender(ca);

    }

    public org.apache.log4j.Logger getLogger() { return cLog; }

}
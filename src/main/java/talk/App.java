package talk;

import logging.ConsoleLogger;
import logging.FileLogger;
import org.apache.log4j.Logger;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Чатбот.Главный класс
 *
 */
public class App 
{

    static Logger consoleLogger = ConsoleLogger.getInstance().getLogger();
    static Logger fileLogger = FileLogger.getInstance().getLogger();

    public static void main( String[] args )
    {

        setCodepage();

        try {

        boolean talking = true;
        Scanner in = new Scanner(System.in);
        String currentAnswersFile = "./answers.txt";
        String fname = "";

        Logic logic = new  Logic("./answers.txt");

        fileLogger.info(logic.getHello());
        consoleLogger.trace(logic.getHello());

        while ( 1 > 0) {

            String s = in.nextLine();
            String answer;

            if (talking) {

                switch (s) {
                    case "\"Goodbye\"":
                        fileLogger.info(logic.getGoodbye());
                        System.exit(0);
                        break;
                    case "\"Stop talking\"":
                        talking = false;
                        break;
                    default:
                        fileLogger.info(s);
                        answer = logic.getRandomAnswer();
                        fileLogger.info(answer);
                        break;
                }
            }

            if (s.startsWith("\"Use another file:") && (talking)) {
                try {
                    fname = s.trim().substring(0,s.length()-1).replace("\"Use another file:", "");
                    logic =  new  Logic(fname);
                    currentAnswersFile = fname;
                    logic.answersFileWasChanged();

                } catch (Exception e) {
                    consoleLogger.error("Ошибка загрузки файла ответов. " + fname );
                    consoleLogger.trace("Будет продолжено использование " + currentAnswersFile);

                }
            }

            if (s.equals("\"Start talking\"")) {
                talking = true;
            }
        }

     } catch (Exception e) {
          consoleLogger.trace("", e);
          System.exit(10006);
      }
    }

    private static void setCodepage() {
        // DOS codepage support
        if(System.getProperty("os.name").toLowerCase().contains("windows")) {

              try {

                  PrintStream out = new PrintStream(System.out, true, "Cp866");
                  PrintStream err = new PrintStream(System.err, true, "Cp866");
                  System.setOut(out);
                  System.setErr(err);

              } catch (UnsupportedEncodingException e) {
                  consoleLogger.trace("", e);
                  System.exit(10005);
              }


        }
    }
}

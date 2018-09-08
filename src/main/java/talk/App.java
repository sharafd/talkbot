package talk;

import common.ConstantsProvider;
import logging.ConsoleLogger;
import logging.FileLogger;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Чатбот.Главный класс.
 */
public final class App {

    /**
     * Logger - Вывод на консоль.
     */
    private static Logger consoleLogger =
            ConsoleLogger.getInstance().getLogger();

    /**
     * Logger - Логирование в файл.
     */
    private static Logger fileLogger =
            FileLogger.getInstance().getLogger();

    /**
     * Default non-public constructor.
     */
    private App() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * @param args -
     */
    public static void main(final String[] args) {
        try {

            boolean talking = true;
            Scanner in = new Scanner(System.in);
            String currentAnswersFile = "./answers.txt";
            String fname = "";

            Logic logic = new Logic("./answers.txt");

            fileLogger.info(logic.getHello());

            while (1 > 0) {

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
                            fileLogger.debug(s);
                            answer = logic.getRandomAnswer();
                            fileLogger.info(answer);
                            break;
                    }
                }

                if (s.startsWith("\"Use another file:")) {
                    try { //NOSONAR
                        fname = s.trim().substring(0, s.length() - 1)
                                .replace("\"Use another file:", "");
                        logic = new Logic(fname);

                    } catch (Exception e) {
                        consoleLogger.error("Ошибка загрузки файла ответов. "
                                + fname);
                        consoleLogger.error("Будет продолжено использование "
                                + currentAnswersFile);

                    }
                    currentAnswersFile = fname;
                    logic.answersFileWasChanged();
                }

                if (s.equals("\"Start talking\"")) {
                    talking = true;
                }
            }

        } catch (Exception e) {
            consoleLogger.trace("", e);
            System.exit(ConstantsProvider
                    .ErrorCodes.ERROR_UNHANDLED_EXCEPTION.getCode());
        }

    }

}

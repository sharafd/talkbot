package ru.sbt.qa.talk;

import org.slf4j.Logger;

import java.util.Scanner;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Чатбот.Главный класс.
 */
public final class Talkbot {

    /**
     * Default non-public constructor.
     */
    private Talkbot() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Логирование.
     */
    private static final Logger LOG = getLogger(Talkbot.class);

    /**
     * @param args -
     */
    public static void main(final String[] args) {
        try {

            boolean talking = true;
            Scanner in = new Scanner(System.in);
            String currentAnswersFile = "./answers.txt";
            String fname = "";
            String answer;
            String s;

            Logic logic = new Logic(currentAnswersFile, false);

            LOG.info(logic.getHello());

            while (1 > 0) {

                s = in.nextLine();

                if (talking) {

                    switch (s) {
                        case "\"\"":
                            LOG.info(logic.getGoodbye());
                            System.exit(0);
                            break;
                        case "\"Stop talking\"":
                            talking = false;
                            break;
                        default:
                            LOG.debug(s);
                            answer = logic.getRandomAnswer();
                            LOG.info(answer);
                            break;
                    }

                }

                if (s.startsWith("\"Use another file:")) {
                    try { //NOSONAR
                        fname = s.replace("\"Use another file:", "");
                        fname = fname.substring(0, fname.length() - 1).trim();
                        LOG.info("Загружен файл ответов: {}", fname);
                        logic = new Logic(fname, false);
                        currentAnswersFile = fname;
                        logic.answersFileWasChanged();
                    } catch (Exception e) {
                        LOG.error("Ошибка загрузки файла ответов {}", fname);
                        LOG.error("Будет продолжено использование {}", currentAnswersFile);
                    }
                }

                if (s.equals("\"Start talking\"")) {
                    talking = true;
                }
            }

        } catch (Exception e) { // NOSONAR
            // Исключения обрабатываются в классе Logic
        }
    }

}

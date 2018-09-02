package talk;

import common.ConstantsProvider;
import logging.ConsoleLogger;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Логика работв чатбота
 */
public class Logic {

    private String hello;
    private String goodbye;
    private List<String> answers;
    private boolean answersFileChanged;
    static Logger consoleLogger = ConsoleLogger.getInstance().getLogger();

    public Logic(String filePath) {
        readAnswersFile(filePath);
    }

    /**
     * Выставляем флаг "Файл ответов загружен по запросу пользователя"
     */
    public void answersFileWasChanged() {
        answersFileChanged = true;
    }

    /**
     * Чтение файла ответов в строковый массив
     *
     * @param filePath путь к файлу ответов
     * @return
     * @throws Exception
     */
    public List<String> readAnswersFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            answers = new ArrayList<>();

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {

                answers.add(new String(sCurrentLine.getBytes(), Charset.defaultCharset()));
            }

        } catch (FileNotFoundException e) {

            consoleLogger.trace("Не найден файл ответов " + filePath);

            if (!answersFileChanged) {
                System.exit(ConstantsProvider.
                        ErrorCodes.ERROR_ANSWERS_FILE_NOT_FOUND.getCode());
            }


        } catch (IOException e) {

            consoleLogger.trace("Не удалось обработать файл ответов " + filePath);

            if (!answersFileChanged) {
                System.exit(ConstantsProvider.
                        ErrorCodes.ERROR_ANSWERS_FILE_PARSING.getCode());
            }
        }

        if (answers == null) {
            consoleLogger.trace("Пустой файл ответов " + filePath);
            System.exit(ConstantsProvider.
                    ErrorCodes.ERROR_ANSWERS_FILE_IS_EMPTY.getCode());
        }
        if (answers.size() < ConstantsProvider.MIN_ANSWERS_ALLOWED) {
            consoleLogger.trace("Файл ответов " + filePath + " должен содержать не менее трех строк");
            if (!answersFileChanged) {
                System.exit(ConstantsProvider.
                        ErrorCodes.ERROR_ANSWERS_FILE_HAS_WRONG_LINES_COUNT.getCode());
            }
        }

        hello = answers.get(0);
        answers.remove(0);

        goodbye = answers.get(answers.size() - 1);
        answers.remove(answers.size() - 1);

        return answers;
    }

    /**
     * Получить строку-привествие
     *
     * @return
     */
    public String getHello() {
        return hello;
    }

    /**
     * Получить строку-прощание
     *
     * @return
     */
    public String getGoodbye() {
        return goodbye;
    }

    /**
     * Вывод случайного ответа из массива answers
     *
     * @return
     */
    public String getRandomAnswer() {
        Random rand = new Random();
        int n = rand.nextInt(answers.size());

        return answers.get(n);
    }


}

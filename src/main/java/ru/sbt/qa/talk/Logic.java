package ru.sbt.qa.talk;

import ru.sbt.qa.common.ConstantsProvider;
import ru.sbt.qa.logging.ConsoleLogger;
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

    /**
     * Строка-приветствие.
     */
    private String hello;

    /**
     * Строка-прощание.
     */
    private String goodbye;

    /**
     * Массив строк-ответов.
     */
    private List<String> answers;

    /**
     * Признак смены файла ответов.
     */
    private boolean answersFileChanged;

    /**
     * Вывод на консоль.
     */
    private static Logger consoleLogger = ConsoleLogger.getInstance().getLogger();

    /**
     * @param filePath
     */
    public Logic(String filePath) {
        readAnswersFile(filePath);
    }

    /**
     * Выставляем флаг "Файл ответов загружен по запросу пользователя".
     */
    public void answersFileWasChanged() {
        answersFileChanged = true;
    }

    /**
     * Чтение файла ответов в строковый массив.
     *
     * @param filePath путь к файлу ответов
     * @return Массив строк-ответов
     * @throws Exception -
     */
    private List<String> readAnswersFile(String filePath) {
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

        try {
            if (answers.size() < ConstantsProvider.MIN_ANSWERS_ALLOWED) { // NOSONAR
                consoleLogger.trace("Файл ответов " + filePath + " должен содержать не менее трех строк");
                if (!answersFileChanged) {
                    System.exit(ConstantsProvider.
                            ErrorCodes.ERROR_ANSWERS_FILE_HAS_WRONG_LINES_COUNT.getCode());
                }
            }
        } catch (NullPointerException e) {
            consoleLogger.trace("Файл ответов " + filePath + " не найден или недоступен.", e);
        }

        hello = answers.get(0);
        answers.remove(0);

        goodbye = answers.get(answers.size() - 1);
        answers.remove(answers.size() - 1);

        return answers;
    }

    /**
     * Получить строку-привествие.
     *
     * @return -
     */
    public String getHello() {
        return hello;
    }

    /**
     * Получить строку-прощание.
     *
     * @return -
     */
    public String getGoodbye() {
        return goodbye;
    }

    /**
     * Вывод случайного ответа из массива answers.
     *
     * @return -
     */
    public String getRandomAnswer() {
        int n = new Random().nextInt(answers.size());
        return answers.get(n);
    }


}

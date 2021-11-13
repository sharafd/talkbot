package ru.sbt.qa.talk;

import org.slf4j.Logger;
import ru.sbt.qa.common.ConstantsProvider;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Логика работв чатбота
 */
public class Logic {

    /**
     * Логирование.
     */
    private static final Logger LOG = getLogger(Logic.class);

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
     * @param filePath полный путь к фпйлу с ответами
     * @param isDebug  принак отладки
     */
    public Logic(String filePath, boolean isDebug) {
        readAnswersFile(filePath, isDebug);
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
     */
    private List<String> readAnswersFile(String filePath, boolean isDebug) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            answers = new ArrayList<>();

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                answers.add(new String(sCurrentLine.getBytes(), Charset.defaultCharset()));
            }

        } catch (NullPointerException | FileNotFoundException e) {
            e.printStackTrace();
            LOG.info("Файл ответов " + filePath + " не найден или недоступен.");
        } catch (IOException e) {
            e.printStackTrace();
            LOG.info("Не удалось обработать файл ответов {}", filePath);
        }

        if (answers == null) {
            LOG.error("Пустой файл ответов {}", filePath);
            if (!isDebug) {
                System.exit(ConstantsProvider.ErrorCodes.ERROR_ANSWERS_FILE_IS_EMPTY.getCode());
            }
        } else {
            if (answers.size() < ConstantsProvider.MIN_ANSWERS_ALLOWED) { // NOSONAR
                LOG.error("Файл ответов " + filePath + " должен содержать не менее трех строк");
                if (!answersFileChanged && !isDebug) {
                    System.exit(ConstantsProvider.ErrorCodes.ERROR_ANSWERS_FILE_HAS_WRONG_LINES_COUNT.getCode());
                }
            } else {
                hello = answers.get(0);
                answers.remove(0);

                goodbye = answers.get(answers.size() - 1);
                answers.remove(answers.size() - 1);
            }
        }

        return answers;
    }

    /**
     * Получить строку-привествие.
     *
     * @return строка-привествие.
     */
    public String getHello() {
        return hello;
    }

    /**
     * Получить строку-прощание.
     *
     * @return строка-прощание.
     */
    public String getGoodbye() {
        return goodbye;
    }

    /**
     * Вывод случайного ответа из массива answers.
     *
     * @return ответ
     */
    public String getRandomAnswer() {
        int n = new Random().nextInt(answers.size());
        return answers.get(n);
    }

}

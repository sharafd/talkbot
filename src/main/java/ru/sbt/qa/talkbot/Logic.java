package ru.sbt.qa.talkbot;

import org.slf4j.Logger;
import ru.sbt.qa.common.ConstantsProvider;

import java.io.*;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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
            LOG.info("Файл ответов {} не найден или недоступен.", filePath, e);
        } catch (IOException e) {
            LOG.info("Не удалось обработать файл ответов {} {}", filePath, e);
        }

        if (answers == null) {
            LOG.error("Пустой файл ответов {}", filePath);
            if (!isDebug) {
                System.exit(ConstantsProvider.ErrorCodes.ERROR_ANSWERS_FILE_IS_EMPTY.getCode());
            }
        } else {
            if (answers.size() < ConstantsProvider.MIN_ANSWERS_ALLOWED) { // NOSONAR
                LOG.error("Файл ответов {} должен содержать не менее трех строк", filePath);
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
     * Выставляем флаг "Файл ответов загружен по запросу пользователя".
     * @param value boolean
     */
    public void setAnswersFileWasChanged(boolean value) {
        answersFileChanged = value;
    }

    /**
     * Получить значение флага "Файл ответов загружен по запросу пользователя".
     * @return boolean
     */
    public boolean getAnswersFileWasChanged(){
        return answersFileChanged;
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
        int n = new SecureRandom().nextInt(answers.size());
        return answers.get(n);
    }

}

package talk;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Логика работв чатбота
 */
public class Logic {

    private String hello;
    private String goodbye;
    private List<String> answers = new ArrayList<String>();
    private boolean answersFileChanged = false;

    public static String currentAnswersFile = "answers.txt";

    public Logic(String filePath) {

        try {
            readAnswersFile(filePath);
            Logger logger = Logger.getInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Выставляем флаг "Файл ответов загружен по запросу пользователя"
     */
    public void answersFileWasChanged(){
        answersFileChanged = true;
    }

    /**
     * Чтение файла ответов в строковый массив
     * @param filePath путь к файлу ответов
     * @return
     * @throws Exception
     */
    public List<String> readAnswersFile(String filePath) throws Exception {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            answers.clear();

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {

                answers.add(new String(sCurrentLine.getBytes(), Charset.forName("UTF-8")));
            }

        } catch (FileNotFoundException e) {

            System.out.println("Не найден файл ответов " + filePath);

            if (!answersFileChanged) {
                Thread.sleep(5000);
                System.exit(10001);
            }


        } catch (IOException e) {

            System.out.println("Не удалось обработать файл ответов " + filePath);

            if (!answersFileChanged) {
                Thread.sleep(5000);
                System.exit(10002);
            }
        }

        if (answers == null) {

            System.out.println("Пустой файл ответов " + filePath);

            if (!answersFileChanged) {
                Thread.sleep(5000);
                System.exit(10003);
            }
        }
        if (answers.size() < 3) {

            System.out.println("Файл ответов " + filePath + " должен содержать не менее трех строк");

            if (!answersFileChanged) {
                Thread.sleep(5000);
                System.exit(10004);
            }
        }

        if (answersFileChanged) {

            System.out.println("Будет использован файл ответов " + currentAnswersFile);
            Logger.logger.info("Будет использован файл ответов " + currentAnswersFile);
        }

        hello  = answers.get(0).substring(1);
        answers.remove(0);

        goodbye = answers.get(answers.size() - 1);
        answers.remove(answers.size() - 1);

        return answers;
    }

    /**
     * Получить строку-привествие
     * @return
     */
    public String getHello(){

        return hello;
    }

    /**
     * Получить строку-прощание
     * @return
     */
    public String getGoodbye(){

        return goodbye;
    }

    /**
     * Вывод случайного ответа из массива answers
     * @return
     */
    public String getRandomAnswer() {

        Random rand = new Random();
        int n = rand.nextInt(answers.size());

        return answers.get(n);
    }

}

package talk;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import static talk.Logic.currentAnswersFile;

/**
 * Чатбот.Главный класс
 *
 */
public class App 
{
    public static void main( String[] args )
    {

      // DOS codepage support
      if(System.getProperty("os.name").toLowerCase().contains("windows")) {

            PrintStream out = null;
            PrintStream err = null;

            try {

                out = new PrintStream(System.out, true, "Cp866");
                err = new PrintStream(System.err, true, "Cp866");
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                System.exit(10005);
            }

            System.setOut(out);
            System.setErr(err);
      }

      try {

        boolean talking = true;
        Scanner in = new Scanner(System.in);
        String fname = "";

        Logic logic = new  Logic("./answers.txt");

        Logger.logger.info(logic.getHello());
        System.out.println(logic.getHello());

        while (true) {

            String s = in.nextLine();

            if (talking) {

                switch (s) {
                    case "\"Goodbye\"":
                        Logger.logger.info(logic.getGoodbye());
                        System.out.println(logic.getGoodbye());
                        System.exit(0);

                    case "\"Stop talking\"":
                        talking = false;
                        break;

                    default:

                        Logger.logger.info(s);

                        if (!s.startsWith("\"Use another file:")) {

                            System.out.println(logic.getRandomAnswer());
                            Logger.logger.info(logic.getRandomAnswer());
                        }
                }
            }

            if (s.startsWith("\"Use another file:")) {
                try {

                    logic.answersFileWasChanged();
                    fname = s.trim().substring(0,s.length()-1).replace("\"Use another file:", "").trim();
                    logic.readAnswersFile(fname);
                    currentAnswersFile = fname;

                } catch (Exception e) {

                    System.out.println("Ошибка загрузки файла ответов. " + fname );
                    System.out.println("Будет продолжено использование " + currentAnswersFile);

                }
            }

            if (s.equals("\"Start talking\"")) {
                talking = true;
            }
        }

     } catch (Exception e) {

          e.printStackTrace();
          System.exit(10006);
      }
    }
}

package ru.sbt.qa;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sbt.qa.talkbot.Logic;

import java.io.File;
import java.io.IOException;

public class LogicTest {

    private Logic logic;
    private String basePath = new File(".").getCanonicalPath();

    public LogicTest() throws IOException {
    }

    @Test(description = "Корректный файл ответов")
    public void defaultFileNameTest() {
        Logic logic = new Logic("answers.txt", false);
        Assert.assertNotSame(logic.getRandomAnswer(), "Привет!");
        Assert.assertNotSame(logic.getRandomAnswer(), "До встречи.");
    }

    @Test(description = "Корректный файл ответов")
    public void validFileNameTest() {
        String answersFile = basePath + "/src/test/resources/new.txt";
        Logic logic = new Logic(answersFile, false);

        Assert.assertEquals(logic.getHello(), "1");
        Assert.assertEquals(logic.getRandomAnswer(), "2");
        Assert.assertEquals(logic.getGoodbye(), "3");
    }

    @Test(description = "Корректный файл ответов, признак перехода на новый файл")
    public void answersFileWasChangedTest() {
        String answersFile = basePath + "/src/test/resources/new.txt";
        Logic logic = new Logic(answersFile, false);

        Assert.assertNotNull(logic.getRandomAnswer());

        logic.setAnswersFileWasChanged(true);

        Assert.assertTrue(logic.getAnswersFileWasChanged());
    }


    @Test(description = "Файл ответов не определён")
    public void nullFileNameTest() {
        try {
            logic = new Logic(null, true);
        } catch (NullPointerException e) {
            Assert.assertNull(logic);
        }
    }

    @Test(description = "Файл ответов не существует")
    public void answersFileNotExistsTest() {
        try {
            logic = new Logic("foo", true);
        } catch (Exception e) {
            Assert.assertNull(logic);
        }
    }

    @Test(description = "Пустой файл ответов")
    public void emptyAnswersFileTest() {
        try {
            logic = new Logic(basePath + "/src/test/resources/empty.txt", true);
            logic.setAnswersFileWasChanged(true);
        } catch (IndexOutOfBoundsException e) {
            Assert.assertNull(logic);
        }
    }

    @Test(description = "Файл ответов из одной строки")
    public void onlinerAnswersFileTest() {
        logic = new Logic(basePath + "/src/test/resources/oneliner.txt", true);
        logic.setAnswersFileWasChanged(true);
        Assert.assertNull(logic.getGoodbye());
    }

}

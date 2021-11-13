package ru.sbt.qa;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.sbt.qa.talk.Logic;

import java.io.File;
import java.io.IOException;

public class LogicTest {

    private Logic logic;
    private String basePath = new File(".").getCanonicalPath();

    public LogicTest() throws IOException {
    }

    @Test(description = "Корректный файл ответов")
    public void defaultFileNameTest() {
        Assert.assertNotSame(new Logic("answers.txt", false).getRandomAnswer(), "Привет!");
        Assert.assertNotSame(new Logic("answers.txt", false).getRandomAnswer(), "До встречи.");
    }

    @Test(description = "Корректный файл ответов")
    public void validFileNameTest() {
        Assert.assertNotNull(new Logic(basePath + "/src/test/resources/new.txt", false).getRandomAnswer());
        Assert.assertNotSame(new Logic("answers.txt", false).getRandomAnswer(), "1");
        Assert.assertNotSame(new Logic("answers.txt", false).getRandomAnswer(), "2");
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
        } catch (IndexOutOfBoundsException e) {
            Assert.assertNull(logic);
        }
    }

    @Test(description = "Файл ответов из одной строки")
    public void onlinerAnswersFileTest() {
        logic = new Logic(basePath + "/src/test/resources/oneliner.txt", true);
        Assert.assertNull(logic.getGoodbye());
    }

}

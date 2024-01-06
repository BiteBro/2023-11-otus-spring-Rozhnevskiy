package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsvQuestionDaoTest {
    private QuestionDao questionDao;
    private final Map<String, String> fileNameByLocaleTag = Map.of(
            "ru-RU", "questions_ru.csv", "en-US", "questions.csv");
    private final int countRightAnswerFromFile = 3;

    @Test
    @DisplayName("Checked question from question.csv file")
    void findAllVsEnglishLocale() {
        List<Answer> answers = Arrays.asList(
                new Answer("Tom from the cartoon Tom and Jerry", false),
                new Answer("Motroskin from cartoon Prostokvashino", true),
                new Answer("Felix from cartoon Felix the Cat", false));
        String questionText = "Which cat was tabby?";
        Locale locale = new Locale("en", "US");
        TestFileNameProvider fileNameProvider = new AppProperties(countRightAnswerFromFile, locale, fileNameByLocaleTag);
        questionDao = new CsvQuestionDao(fileNameProvider);
        List<Question> questions = questionDao.findAll();

        assertNotNull(questions);
        assertEquals(questionText, questions.get(4).text());
        assertEquals(answers, questions.get(4).answers());
    }
    @Test
    @DisplayName("Checked question from question_ru.csv file")
    void findAllVsRussianLocale() {
        List<Answer> answers = Arrays.asList(
                new Answer("Том из мультика Том и Джери", false),
                new Answer("Мотроскин из мультика Простоквашино", true),
                new Answer("Феликс из мультика Кот Феликс", false));
        String questionText = "Какой кот был полосатым?";
        Locale locale = new Locale("ru", "RU");
        TestFileNameProvider fileNameProvider = new AppProperties(countRightAnswerFromFile, locale, fileNameByLocaleTag);
        questionDao = new CsvQuestionDao(fileNameProvider);
        List<Question> questions = questionDao.findAll();

        assertNotNull(questions);
        assertEquals(questionText, questions.get(4).text());
        assertEquals(answers, questions.get(4).answers());
    }
}
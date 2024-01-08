package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CsvQuestionDaoTest {

    private QuestionDao questionDao;

    @Test
    @DisplayName("Checked question from question.csv file")
    void findAllVsEnglishLocale() {
        List<Answer> answers = Arrays.asList(
                new Answer("Tom from the cartoon Tom and Jerry", false),
                new Answer("Motroskin from cartoon Prostokvashino", true),
                new Answer("Felix from cartoon Felix the Cat", false));
        String questionText = "Which cat was tabby?";

        TestFileNameProvider fileNameProvider = () -> "questions.csv";
        questionDao = new CsvQuestionDao(fileNameProvider);
        List<Question> questions = questionDao.findAll();

        assertNotNull(questions);
        assertEquals(questionText, questions.get(4).text());
        assertEquals(answers, questions.get(4).answers());
    }
    @Test
    @DisplayName("Checked question from questions_ru.csv file")
    void findAllVsRussianLocale() {
        List<Answer> answers = Arrays.asList(
                new Answer("Том из мультика Том и Джери", false),
                new Answer("Мотроскин из мультика Простоквашино", true),
                new Answer("Феликс из мультика Кот Феликс", false));
        String questionText = "Какой кот был полосатым?";

        TestFileNameProvider fileNameProvider = () -> "questions_ru.csv";
        questionDao = new CsvQuestionDao(fileNameProvider);
        List<Question> questions = questionDao.findAll();

        assertNotNull(questions);
        assertEquals(questionText, questions.get(4).text());
        assertEquals(answers, questions.get(4).answers());
    }
}
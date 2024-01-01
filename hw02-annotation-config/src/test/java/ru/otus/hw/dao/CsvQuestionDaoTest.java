package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.List;

class CsvQuestionDaoTest {
    private QuestionDao questionDao;
    private final String questionFileName = "questions.csv";
    private final String questionText = "Which cat was tabby?";
    private final int countRightAnswerFromFile = 3;
    private final List<Answer> answers = Arrays.asList(
            new Answer("Tom from the cartoon Tom and Jerry", false),
            new Answer("Motroskin from cartoon Prostokvashino", true),
            new Answer("Felix from cartoon Felix the Cat", false)
    );

    @Test
    @DisplayName("Checked question from question.csv file")
    void findAll() {
        TestFileNameProvider fileNameProvider = new AppProperties(countRightAnswerFromFile, questionFileName);
        questionDao = new CsvQuestionDao(fileNameProvider);
        List<Question> questions = questionDao.findAll();

        assertNotNull(questions);
        System.out.println(questions.get(4).text());
        assertEquals(questionText, questions.get(4).text());
        assertEquals(answers, questions.get(4).answers());
    }
}
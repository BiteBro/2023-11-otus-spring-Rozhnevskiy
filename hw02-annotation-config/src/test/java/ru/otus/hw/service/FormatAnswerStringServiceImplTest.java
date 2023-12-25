package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.domain.Answer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormatAnswerStringServiceImplTest {
    private FormatAnswerStringService formatAnswerStringService;
    private static final String STRING_TEMPLATE = "\t Variants of answers: \n" +
            "\t\tN1 : Answer: Science doesn't know this yet\n" +
            "\t\tN2 : Answer: Certainly. The red UFO is from Mars. And green is from Venus\n" +
            "\t\tN3 : Answer: Absolutely not\n\t\t";

    @Test
    @DisplayName("Checked question from question.csv file")
    void formattedAnswersString() {
        List<Answer> answers = Arrays.asList(new Answer("Science doesn't know this yet", true),
                new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                new Answer("Absolutely not", false));
        formatAnswerStringService = new FormatAnswerStringServiceImpl();
        String answerText = formatAnswerStringService.formattedAnswersString(answers);
        System.out.println(answerText);
        assertEquals(STRING_TEMPLATE, answerText);
    }

}
package ru.otus.hw.service;

import ru.otus.hw.domain.Answer;

import java.util.List;

public class FormatAnswerStringServiceImpl implements FormatAnswerStringService {

    @Override
    public String formattedAnswersString(List<Answer> answers) {
        StringBuilder answersString = new StringBuilder();
        answersString.append("\t Variants of answers: \n\t\t");
        for (int count = 0; count < answers.size(); count++) {
            answersString.append("N" + (count + 1) + " : " + stringOfAnswer(answers.get(count)) + "\n\t\t");
        }
        return answersString.toString();
    }

    private String stringOfAnswer(Answer answer) {
        return "Answer: " + answer.text() + " (" + answer.isCorrect() + ")";
    }
}

package ru.otus.hw.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
    public boolean checkAnswer(int numberOfAnswer) {
        return answers.get(numberOfAnswer - 1).isCorrect();
    }
}

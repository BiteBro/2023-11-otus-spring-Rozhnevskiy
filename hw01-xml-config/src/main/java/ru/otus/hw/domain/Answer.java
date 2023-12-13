package ru.otus.hw.domain;

public record Answer(String text, boolean isCorrect) {
    @Override
    public String toString() {
        return " Answer: " + text + "  (" + isCorrect + ")";
    }
}

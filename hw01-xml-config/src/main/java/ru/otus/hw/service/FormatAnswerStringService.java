package ru.otus.hw.service;

import ru.otus.hw.domain.Answer;

import java.util.List;

public interface FormatAnswerStringService {
    String formattedAnswersString(List<Answer> answers);

}

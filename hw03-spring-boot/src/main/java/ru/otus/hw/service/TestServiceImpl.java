package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    private final FormatAnswerStringService formatAnswer;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var testResult = new TestResult(student);

        for (var question : questionDao.findAll()) {
            List<Answer> answers = question.answers();
            ioService.printLine(question.text());
            ioService.printLine(formatAnswer.formattedAnswersString(answers));
            int inputNumber = ioService.readIntForRangeWithPromptLocalized(1, answers.size(),
                    "TestService.number.of.answer",
                    "TestService.number.of.answer.error");
            boolean isAnswerValid = question.checkAnswer(inputNumber);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

}

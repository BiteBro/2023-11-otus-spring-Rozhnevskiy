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
    private final IOService ioService;

    private final QuestionDao questionDao;

    private final FormatAnswerStringService formatAnswer;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var testResult = new TestResult(student);

        for (var question : questionDao.findAll()) {
            List<Answer> answers = question.answers();
            ioService.printLine(question.text());
            ioService.printLine(formatAnswer.formattedAnswersString(answers));
            int inputNumber = ioService.readIntForRangeWithPrompt(1, answers.size(),
                    "Insert number of answer!", "Error. Please enter the answer number again!");

            boolean isAnswerValid = returnTrueOrFalseAnswer(answers, inputNumber);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private boolean returnTrueOrFalseAnswer(List<Answer> answers, int numberOfAnswer) {
        return answers.get(numberOfAnswer - 1).isCorrect();
    }
}

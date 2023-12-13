package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> questions = questionDao.findAll();
        for (Question question : questions) {
            ioService.printLine(question.text());
            ioService.printFormattedLine(formattedAnswerString(question.answers()));
        }
    }

    private String formattedAnswerString(List<Answer> answers){
        StringBuilder answersString = new StringBuilder();
        answersString.append("\t Variants of answers: \n\t\t");
        for (int count = 0; count < answers.size(); count++){
            answersString.append("N" + (count+1) + " : " + answers.get(count).toString() + "\n\t\t");
        }
        return answersString.toString();
    }
}

package ru.otus.hw.config;

import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

@PropertySource("application.properties")
@Configuration
@Setter
public class AppProperties implements TestConfig, TestFileNameProvider {

    private int rightAnswersCountToPass;

    private String testFileName;

    public AppProperties(@Value("${test.rightAnswersCountToPass}") int rightAnswersCountToPass,
                         @Value("${test.fileName}") String testFileName) {
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.testFileName = testFileName;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return testFileName;
    }
}

package ru.otus.hw.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.ResultService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestService;

@ShellComponent
public class ShellCommands {

    private final StudentService studentService;

    private final TestService testService;

    private final ResultService resultService;

    private final LocalizedIOService localizedMessages;

    private Student student;

    private TestResult testResult;

    public ShellCommands(StudentService studentService, TestService testService,
                         ResultService resultService, LocalizedIOService localizedMessages) {
        this.studentService = studentService;
        this.testService = testService;
        this.resultService = resultService;
        this.localizedMessages = localizedMessages;
    }

    @ShellMethod(key = "start test", value = "Fast start testing")
    public void startTest() {
        student = studentService.determineCurrentStudent();
        testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }

    @ShellMethod(key = "login", value = "Enter your first name and last name")
    public void login() {
        student = studentService.determineCurrentStudent();
    }

    @ShellMethod(key = "test", value = "Start testing")
    @ShellMethodAvailability(value = "isCheckStudent")
    public void test() {
        testResult = testService.executeTestFor(student);
    }

    @ShellMethod(key = "result", value = "Get result")
    @ShellMethodAvailability(value = "isCheckTest")
    public void result() {
        resultService.showResult(testResult);
    }

    private Availability isCheckStudent() {
        return student != null ? Availability.available() : Availability.unavailable(
                localizedMessages.getMessage("ShellCommand.is.check.student"));
    }

    private Availability isCheckTest() {
        return testResult != null ? Availability.available() : Availability.unavailable(
                localizedMessages.getMessage("ShellCommand.is.check.test"));
    }
}

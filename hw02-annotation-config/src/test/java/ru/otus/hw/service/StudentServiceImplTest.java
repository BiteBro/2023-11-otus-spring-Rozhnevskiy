package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    private String name = "Alexandr";

    private String surname = "Petrov";

    private String fullName = "Alexandr Petrov";

    private IOService ioService = Mockito.spy(IOService.class);

    @Test
    @DisplayName("StudentServiceImplTest")
    void determineCurrentStudent() {
        Mockito.when(ioService.readStringWithPrompt("Please input your first name"))
                .thenReturn(name);
        Mockito.when(ioService.readStringWithPrompt("Please input your last name"))
                .thenReturn(surname);

        var studentServiceImpl = new StudentServiceImpl(ioService);
        var student = studentServiceImpl.determineCurrentStudent();

        System.out.println(student.getFullName());

        assertEquals(name, student.firstName());
        assertEquals(surname, student.lastName());
        assertEquals(fullName, student.getFullName());
    }
}
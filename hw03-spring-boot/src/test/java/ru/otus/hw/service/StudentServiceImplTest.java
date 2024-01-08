package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentServiceImplTest {

    private final LocalizedIOService localizedIOService = Mockito.mock(LocalizedIOService.class);

    @Test
    @DisplayName("StudentServiceImplTest")
    void determineCurrentStudent() {
        String name = "Alexandr";
        String surname = "Petrov";
        Mockito.when(localizedIOService.readStringWithPromptLocalized("StudentService.input.first.name"))
                .thenReturn(name);
        Mockito.when(localizedIOService.readStringWithPromptLocalized("StudentService.input.last.name"))
                .thenReturn(surname);

        var studentServiceImpl = new StudentServiceImpl(localizedIOService);
        var student = studentServiceImpl.determineCurrentStudent();

        assertEquals(name, student.firstName());
        assertEquals(surname, student.lastName());
        assertEquals("Alexandr Petrov", student.getFullName());
    }
}
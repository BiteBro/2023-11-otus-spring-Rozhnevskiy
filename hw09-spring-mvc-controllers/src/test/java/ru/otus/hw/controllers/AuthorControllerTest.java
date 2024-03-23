package ru.otus.hw.controllers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.AuthorController;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.AuthorMapper;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("Должен возвращать код 400 при создании автора с пустым названием")
    void shouldCreateOrUpdateBookWithEmptyTitle() throws Exception {
        var authorDto = new AuthorDto(0, "");

        mockMvc.perform(post("/author/save")
                        .param("fullName", String.valueOf(authorDto.getFullName())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Должен возвращать код 404 при при поиске автора")
    void shouldReturnNotFound() throws Exception {
        given(authorService.findById(1L)).willThrow(new EntityNotFoundException("empty"));
        mockMvc.perform(get("/author/edit?authorId=1"))
                .andExpect(status().isNotFound());
    }


}

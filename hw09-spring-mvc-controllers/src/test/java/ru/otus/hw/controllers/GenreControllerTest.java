package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.GenreController;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.GenreService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("Должен возвращать код 400 при создании жанра с пустым именем")
    void shouldCreateOrUpdateBookWithEmptyTitle() throws Exception {
        var genreDto = new GenreDto(0, "");

        mockMvc.perform(post("/genre/save")
                        .param("name", String.valueOf(genreDto.getName())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Должен возвращать код 404 при при поиске жанра")
    void shouldReturnNotFound() throws Exception {
        given(genreService.findById(1L)).willThrow(new EntityNotFoundException("empty"));
        mockMvc.perform(get("/genre/edit?genreId=1"))
                .andExpect(status().isNotFound());
    }
}

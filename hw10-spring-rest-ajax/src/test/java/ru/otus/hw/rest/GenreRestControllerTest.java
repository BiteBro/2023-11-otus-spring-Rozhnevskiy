package ru.otus.hw.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreRestController.class)
class GenreRestControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("Должен возвращать список жанров")
    void shouldReturnListGenres() throws Exception {
        var listGenres = List.of(
                new GenreDto(1L, "Genre_1"),
                new GenreDto(2L, "Genre_2"),
                new GenreDto(3L, "Genre_3"));

        given(genreService.findAll()).willReturn(listGenres);

        mock.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Genre_1\"}," +
                        "{\"id\":2,\"name\":\"Genre_2\"},{\"id\":3,\"name\":\"Genre_3\"}]"));
    }

    @Test
    @DisplayName("Должен возвращать жанр по id")
    void shouldReturnGenreById() throws Exception {
        var genre = new GenreDto(1L, "Genre_1");
        given(genreService.findById(genre.getId())).willReturn(genre);

        mock.perform(get("/api/genre/"+genre.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Genre_1\"}"));
    }
}
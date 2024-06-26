package ru.otus.hw.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorRestController.class)
class AuthorRestControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Должен возвращать список авторов")
    void shouldReturnListAuthors() throws Exception{
        var listAuthors = List.of(
                new AuthorDto(1L, "Author_1"),
                new AuthorDto(2L, "Author_2"),
                new AuthorDto(3L, "Author_3"));

        given(authorService.findAll()).willReturn(listAuthors);

        mock.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(listAuthors)));
    }

    @Test
    @DisplayName("Должен возвращать автора по id")
    void shouldReturnAuthorById() throws Exception {
        var author = new AuthorDto(1L, "Author_1");

        given(authorService.findById(author.getId())).willReturn(author);

        mock.perform(get("/api/author/" + author.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(author)));
    }
}
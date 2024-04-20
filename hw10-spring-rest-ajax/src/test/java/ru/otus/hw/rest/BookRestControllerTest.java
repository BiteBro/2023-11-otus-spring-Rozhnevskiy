package ru.otus.hw.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.services.BookService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Должен возвращать список книг")
    void shouldReturnListBooks() throws Exception {
        var listBooks = List.of(
                new BookDto(1L, "BookTitle_1",
                        new AuthorDto(1L, "Author_1"), new GenreDto(1L, "Genre_1")),
                new BookDto(2L, "BookTitle_2",
                        new AuthorDto(2L, "Author_2"), new GenreDto(2L, "Genre_2"))
        );

        given(bookService.findAll()).willReturn(listBooks);

        mock.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(listBooks)));
    }

    @Test
    @DisplayName("Должен возвращать книгу по id")
    void shouldReturnBookById() throws Exception {
        var bookDto = new BookDto(1L, "BookTitle_1",
                new AuthorDto(1L, "Author_1"), new GenreDto(1L, "Genre_1"));

        given(bookService.findById(bookDto.getId())).willReturn(bookDto);

        mock.perform(get("/api/book/" + bookDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto))
                );
    }

    @Test
    @DisplayName("Должен сохранять книгу")
    void shouldSaveBook() throws Exception{
        var bookCreateDto = new BookCreateDto("BookTitle_1", 1L, 1L);
        var expected = mapper.writeValueAsString(bookCreateDto);

        mock.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isCreated());

        verify(bookService).create(bookCreateDto);
    }

    @Test
    @DisplayName("Должен изменять книгу по id")
    void shouldEditBookById() throws Exception{
        var bookUpdateDto = new BookUpdateDto(1L, "BookTitle_1", 1L, 1L);
        var expected = mapper.writeValueAsString(bookUpdateDto);

        mock.perform(put("/api/book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isCreated());

        verify(bookService).update(bookUpdateDto);
    }

    @Test
    @DisplayName("Должен удалять книгу по id")
    void shouldDeleteBookById() throws Exception{
        mock.perform(delete("/api/book/1"))
                .andExpect(status().isOk());
        verify(bookService).deleteById(1L);
    }

    @Test
    @DisplayName("Должен возвращать ошибку при удалении книгу по несуществующему id")
    void shouldReturnNotFound() throws Exception {
        mock.perform(delete("/api/books/1"))
                .andExpect(status().isNotFound());
    }

}
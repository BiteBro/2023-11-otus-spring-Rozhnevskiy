package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookMapper bookMapper;



    @Test
    @DisplayName("Должен изменять книгу")
    void shouldUpdateBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto(1L, "0_book", 1L, 1L);

        mockMvc.perform(post("/book/edit")
                        .param("id", String.valueOf(bookUpdateDto.getId()))
                        .param("title", bookUpdateDto.getTitle())
                        .param("authorId", String.valueOf(bookUpdateDto.getAuthorId()))
                        .param("genreId", String.valueOf(bookUpdateDto.getGenreId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));

        verify(bookService).update(bookUpdateDto);
    }

    @Test
    @DisplayName("Должен создавать книгу")
    void shouldCreateBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto("0_book", 1L, 1L);

        mockMvc.perform(post("/book/save")
                        .param("title", bookCreateDto.getTitle())
                        .param("authorId", String.valueOf(bookCreateDto.getAuthorId()))
                        .param("genreId", String.valueOf(bookCreateDto.getGenreId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));

        verify(bookService).create(bookCreateDto);
    }

    @Test
    @DisplayName("Должен оставаться на той же странице при создании книги с пустым названием")
    void shouldCreateOrUpdateBookWithEmptyTitle() throws Exception {
        var bookCreateDto = new BookCreateDto("", 1L, 1L);

        mockMvc.perform(post("/book/save")
                        .param("title", String.valueOf(bookCreateDto.getTitle()))
                        .param("authorId", String.valueOf(bookCreateDto.getAuthorId()))
                        .param("genreId", String.valueOf(bookCreateDto.getGenreId())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Должен возвращать код 404 при при поиске книги")
    void shouldReturnNotFound() throws Exception {
        given(bookService.findById(1L)).willThrow(new NotFoundException("empty"));
        mockMvc.perform(get("/book/edit?bookId=1"))
                        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Должен удалять книгу")
    void shouldDeleteBook() throws Exception {
        long id = 3L;
        mockMvc.perform(post("/book/delete?bookId={id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/book"));

        verify(bookService).deleteById(id);
    }



}

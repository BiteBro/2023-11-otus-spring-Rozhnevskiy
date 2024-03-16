package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("Должен загружать список книг")
    void shouldLoadListBooks() throws Exception {
        List<BookDto> books = getBooks();
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bookDtoList", books))
                .andExpect(view().name("books_list"));
    }

    @Test
    @DisplayName("Должен изменять книгу")
    void shouldUpdateBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto(0, "0_book", 0, 0);

        mockMvc.perform(post("/edit")
                        .param("id", String.valueOf(bookUpdateDto.getId()))
                        .param("title", bookUpdateDto.getTitle())
                        .param("author.id", String.valueOf(bookUpdateDto.getAuthorId()))
                        .param("genre.id", String.valueOf(bookUpdateDto.getGenreId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService).update(bookUpdateDto);
    }

    @Test
    @DisplayName("Должен создавать книгу")
    void shouldCreateBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto(0, "0_book", 0, 0);

        mockMvc.perform(post("/save")
                        .param("title", bookCreateDto.getTitle())
                        .param("author.id", String.valueOf(bookCreateDto.getAuthorId()))
                        .param("genre.id", String.valueOf(bookCreateDto.getGenreId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService).create(bookCreateDto);
    }

    @Test
    @DisplayName("Должен удалять книгу")
    void shouldDeleteBook() throws Exception {
        long id = 3L;
        mockMvc.perform(post("/delete?bookId={id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService).deleteById(id);
    }

    private List<BookDto> getBooks() {
        List<BookDto> books = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            books.add(new BookDto(i, i + "_book",
                    new AuthorDto(i, i + "_author"),
                    new GenreDto(i, i + "_genre")));
        }
        return books;
    }

}

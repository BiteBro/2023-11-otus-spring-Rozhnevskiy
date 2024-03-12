package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.GenreDTO;
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

    @Test
    @DisplayName("Должен загружать список книг")
    void shouldLoadListBooks() throws Exception {
        List<BookDTO> books = getBooks();
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("books_list"));
    }

    @Test
    @DisplayName("Должен изменять книгу")
    void shouldUpdateBook() throws Exception {
        var author = new AuthorDTO(0, "0_author");
        var genre = new GenreDTO(0, "0_genre");
        BookDTO bookDTO = new BookDTO(0, "0_book", author, genre);

        mockMvc.perform(post("/edit")
                        .param("id", String.valueOf(bookDTO.getId()))
                        .param("title", bookDTO.getTitle())
                        .param("author.id", String.valueOf(bookDTO.getAuthor().getId()))
                        .param("author.fullName", bookDTO.getAuthor().getFullName())
                        .param("genre.id", String.valueOf(bookDTO.getGenre().getId()))
                        .param("genre.name", bookDTO.getGenre().getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService).update(bookDTO);
    }

    @Test
    @DisplayName("Должен создавать книгу")
    void shouldCreateBook() throws Exception {
        var author = new AuthorDTO(0, "0_author");
        var genre = new GenreDTO(0, "0_genre");
        BookDTO bookDTO = new BookDTO(0, "0_book", author, genre);

        mockMvc.perform(post("/save")
                        .param("title", bookDTO.getTitle())
                        .param("author.id", String.valueOf(bookDTO.getAuthor().getId()))
                        .param("author.fullName", bookDTO.getAuthor().getFullName())
                        .param("genre.id", String.valueOf(bookDTO.getGenre().getId()))
                        .param("genre.name", bookDTO.getGenre().getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService).create(bookDTO);
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

    private List<BookDTO> getBooks() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            books.add(new BookDTO(i, i + "_book",
                    new AuthorDTO(i, i + "_author"),
                    new GenreDTO(i, i + "_genre")));
        }
        return books;
    }

}

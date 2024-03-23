package ru.otus.hw.controllers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.StartPageController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StartPageController.class)
public class StartPageControllerTest {

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
    void shouldLoadListBook() throws Exception {
        List<BookDto> books = getBooks();
        when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bookDtoList", books))
                .andExpect(view().name("book_list"));
    }

    @Test
    @DisplayName("Должен загружать список авторов")
    void shouldLoadListAuthor() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1, "1_author"),
                new AuthorDto(2, "2_author"), new AuthorDto(3, "3_author"));

        when(authorService.findAll()).thenReturn(authors);
        mockMvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("authorDtoList", authors))
                .andExpect(view().name("author_list"));
    }

    @Test
    @DisplayName("Должен загружать список жанров")
    void shouldLoadListGenre() throws Exception {
        List<GenreDto> genres = List.of(new GenreDto(1, "1_genre"),
                new GenreDto(2, "2_genre"), new GenreDto(3, "3_genre"));

        when(genreService.findAll()).thenReturn(genres);
        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("genreDtoList", genres))
                .andExpect(view().name("genre_list"));
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

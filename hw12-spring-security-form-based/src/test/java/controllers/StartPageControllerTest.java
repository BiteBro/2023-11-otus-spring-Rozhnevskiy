package controllers;


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
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
        List<BookDto> books = Arrays.asList(
                new BookDto(1L, "1_book",  new AuthorDto(1L, "1_author"),
                        new GenreDto(1L, "1_genre")),
                new BookDto(2L, "2_book", new AuthorDto(2L, "2_author"),
                        new GenreDto(2L, "2_genre")),
                new BookDto(3L, "3_book", new AuthorDto(3L, "3_author"),
                        new GenreDto(3L, "3_genre")));
        when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bookDtoList", books))
                .andExpect(view().name("book_list"));
    }

    @Test
    @DisplayName("Должен загружать список авторов")
    void shouldLoadListAuthor() throws Exception {
        List<AuthorDto> authors = List.of(new AuthorDto(1L, "1_author"),
                new AuthorDto(2L, "2_author"), new AuthorDto(3L, "3_author"));

        when(authorService.findAll()).thenReturn(authors);
        mockMvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("authorDtoList", authors))
                .andExpect(view().name("author_list"));
    }

    @Test
    @DisplayName("Должен загружать список жанров")
    void shouldLoadListGenre() throws Exception {
        List<GenreDto> genres = List.of(new GenreDto(1L, "1_genre"),
                new GenreDto(2L, "2_genre"), new GenreDto(3L, "3_genre"));

        when(genreService.findAll()).thenReturn(genres);
        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("genreDtoList", genres))
                .andExpect(view().name("genre_list"));
    }

}

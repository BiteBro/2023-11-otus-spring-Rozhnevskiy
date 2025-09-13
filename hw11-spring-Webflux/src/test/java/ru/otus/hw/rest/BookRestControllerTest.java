package ru.otus.hw.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.repositories.custom.BookRepositoryCustomImpl;

import static org.mockito.BDDMockito.given;

@WebFluxTest(BookRestController.class)
class BookRestControllerTest {

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookRepositoryCustomImpl repositoryCustom;

    @MockBean
    private BookRepository repository;

    @Test
    @DisplayName("Должен вернуть массив книг и сопоставить первый элемент с заданным")
    void shouldReturnAnArrayWithBooks() {
        Flux<BookDto> books = Flux.just(
                new BookDto(1L,"Book_1", new Author(1L, "Author_1"), new Genre(1L, "Genre_1")),
                new BookDto(2L,"Book_2", new Author(1L, "Author_2"), new Genre(1L, "Genre_2")),
                new BookDto(3L,"Book_3",new Author(2L, "Author_3"), new Genre(2L, "Genre_3"))
        );

        Mockito.when(repositoryCustom.findAll()).thenReturn(books);

        webClient.get().uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo(1L)
                .jsonPath("$[0].title").isEqualTo("Book_1")
                .jsonPath("$[0].author.id").isEqualTo(1L)
                .jsonPath("$[0].author.fullName").isEqualTo("Author_1")
                .jsonPath("$[0].genre.id").isEqualTo(1L)
                .jsonPath("$[0].genre.name").isEqualTo("Genre_1");
    }

    @Test
    @DisplayName("Должен вернуть книгу по id")
    void shouldReturnTheBookById() {
        BookDto expected = new BookDto(1L, "Book_1",
                new Author(1L, "Author_1"), new Genre(1L, "Genre_1"));
        Mono<BookDto> book = Mono.just(expected);

        Mockito.when(repositoryCustom.findById(1L)).thenReturn(book);

        webClient.get().uri("/api/book/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Должен сохранить книгу")
    void shouldSaveNewBook() {
        Book book = new Book("Test_save_book", 1L, 1L);
        Author author = new Author(1L,"Test_save_book");
        Genre genre = new Genre(1L,"Test_save_book");

        Mockito.when(authorRepository.findById(1L)).thenReturn(Mono.just(author));
        Mockito.when(genreRepository.findById(1L)).thenReturn(Mono.just(genre));
        Mockito.when(repository.save(book)).thenReturn(Mono.just(book));

        webClient.post().uri("/api/book")
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("Должен изменить книгу")
    void shouldSaveUpdateBook() {
        Book book = new Book(1L, "Test_save_book", 1L, 1L);
        Author author = new Author(1L,"Test_save_book");
        Genre genre = new Genre(1L,"Test_save_book");

        Mockito.when(authorRepository.findById(1L)).thenReturn(Mono.just(author));
        Mockito.when(genreRepository.findById(1L)).thenReturn(Mono.just(genre));
        Mockito.when(repository.findById(1L)).thenReturn(Mono.just(book));
        Mockito.when(repository.save(book)).thenReturn(Mono.just(book));

        webClient.put().uri("/api/book/{bookId}", 1L)
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("Должен удалить книгу")
    void shouldDeleteBook() {
        Book book = new Book(1L, "Test_save_book", 1L, 1L);

        given(repository.delete(book)).willReturn(Mono.empty());

        webClient.delete().uri("/api/book/{bookId}", 1L)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("Должен вернуть код ошибки 404")
    void shouldReturnStatusCode404() {
        webClient.get().uri("/api/non-existent-resource")
                .exchange()
                .expectStatus().isEqualTo(404);
    }

    @Test
    @DisplayName("Должен вернуть код ошибки 500")
    void shouldReturnStatusCode500() {
        webClient.get().uri("/api/book/{id}", 2L)
                .exchange()
                .expectStatus().isEqualTo(500);
    }

    @Test
    @DisplayName("Должен вернуть код ошибки 400")
    void shouldReturnStatusCode400() {
        webClient.post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("\"tittle\": \"BookTitle_11\", \"authorId\": 2, \"genreId\": 2")
                .exchange()
                .expectStatus().isEqualTo(500);
    }

}
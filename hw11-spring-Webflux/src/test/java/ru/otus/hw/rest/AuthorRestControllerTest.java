package ru.otus.hw.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

@WebFluxTest(AuthorRestController.class)
class AuthorRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AuthorRepository repository;

    @Test
    @DisplayName("Должен вернуть массив авторов и сопоставить первый элемент с заданным")
    void shouldReturnAnArrayWithAuthors() {
        Flux<Author> authors = Flux.just(new Author(1L, "Author_1"),
                new Author(2L, "Author_2"), new Author(3L, "Author_3"));

        Mockito.when(repository.findAll()).thenReturn(authors);

        webClient.get().uri("/api/author")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo(1L)
                .jsonPath("$[0].fullName").isEqualTo("Author_1");
    }

    @Test
    @DisplayName("Должен вернуть автора по id")
    void shouldReturnTheAuthorById() {
        Mono<Author> author = Mono.just(new Author(1L, "Author_1"));

        Mockito.when(repository.findById(1L)).thenReturn(author);

        webClient.get().uri("/api/author/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Author.class)
                .isEqualTo(new Author(1L, "Author_1"));
    }
}


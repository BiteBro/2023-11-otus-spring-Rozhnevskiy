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
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

@WebFluxTest(GenreRestController.class)
public class GenreRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GenreRepository repository;

    @Test
    @DisplayName("Должен вернуть массив жанров и сопоставить первый элемент с заданным")
    void shouldReturnAnArrayWithGenres() {

        Flux<Genre> genres = Flux.just(new Genre(1L, "Genre_1"),
                new Genre(2L, "Genre_2"), new Genre(3L, "Genre_3"));

        Mockito.when(repository.findAll()).thenReturn(genres);

        webClient.get().uri("/api/genre")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo(1L)
                .jsonPath("$[0].name").isEqualTo("Genre_1");
    }

    @Test
    @DisplayName("Должен вернуть жанр по id")
    void shouldReturnTheGenreById() {
        Mono<Genre> genre = Mono.just(new Genre(1L, "Genre_1"));

        Mockito.when(repository.findById(1L)).thenReturn(genre);

        webClient.get().uri("/api/genre/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Genre.class)
                .isEqualTo(new Genre(1L, "Genre_1"));
    }

}

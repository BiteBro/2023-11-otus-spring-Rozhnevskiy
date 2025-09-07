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
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.custom.CommentRepositoryCustomImpl;

import static org.mockito.BDDMockito.given;

@WebFluxTest(CommentRestController.class)
class CommentRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CommentRepositoryCustomImpl repositoryCustom;

    @MockBean
    private CommentRepository repository;

    @Test
    @DisplayName("Должен вернуть массив комментариев по id книги и сопоставить первый элемент с заданным")
    void shouldReturnAnArrayWithCommentsByBookId() {
        Flux<CommentDto> comments = Flux.just(
                new CommentDto(1L,"Comment_1", 1L, "Book_1"),
                new CommentDto(2L,"Comment_2", 1L, "Book_1"));

        Mockito.when(repositoryCustom.findByBookId(1L)).thenReturn(comments);

        webClient.get().uri("/api/book/{bookId}/comment", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[1].id").isEqualTo(2L)
                .jsonPath("$[1].textContent").isEqualTo("Comment_2")
                .jsonPath("$[1].bookId").isEqualTo(1L)
                .jsonPath("$[1].bookTitle").isEqualTo("Book_1");
    }

    @Test
    @DisplayName("Должен вернуть массив комментариев и сопоставить первый элемент с заданным")
    void shouldReturnAnArrayWithComments() {
        Flux<CommentDto> comments = Flux.just(
                new CommentDto(1L,"Comment_1", 1L, "Book_1"),
                new CommentDto(2L,"Comment_2", 2L, "Book_2"),
                new CommentDto(3L,"Comment_3", 3L, "Book_3"));

        Mockito.when(repositoryCustom.findAll()).thenReturn(comments);

        webClient.get().uri("/api/comment")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo(1L)
                .jsonPath("$[0].textContent").isEqualTo("Comment_1")
                .jsonPath("$[0].bookId").isEqualTo(1L);
    }

    @Test
    @DisplayName("Должен вернуть комментарий по id")
    void shouldReturnTheCommentById() {
        Mono<CommentDto> comment = Mono.just(
                new CommentDto(1L,"Comment_1", 1L, "Book_1"));

        Mockito.when(repositoryCustom.findById(1L)).thenReturn(comment);

        webClient.get().uri("/api/comment/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Comment.class)
                .isEqualTo(new Comment(1L,"Comment_1", 1L));
    }

    @Test
    @DisplayName("Должен сохранить комментарий")
    void shouldSaveNewComment() {
        Comment comment = new Comment("Test_save_comments", 1L);

        Mockito.when(repository.save(comment)).thenReturn(Mono.just(comment));

        webClient.post().uri("/api/comment")
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    @DisplayName("Должен изменить комментарий")
    void shouldSaveUpdateComment() {
        Comment comment = new Comment(1L, "Test_save_comments", 1L);

        Mockito.when(repository.save(comment)).thenReturn(Mono.just(comment));

        webClient.put().uri("/api/comment/{commentId}", 1L)
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("Должен удалить комментарий")
    void shouldDeleteComment() {

        given(repository.deleteById(1L)).willReturn(Mono.empty());

        webClient.delete().uri("/api/comment/{commentId}", 1L)
                .exchange()
                .expectStatus().isOk();
    }
}
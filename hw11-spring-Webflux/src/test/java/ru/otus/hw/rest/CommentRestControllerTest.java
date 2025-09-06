package ru.otus.hw.rest;

import org.junit.jupiter.api.BeforeEach;
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
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.models.Comment;

import static org.mockito.BDDMockito.given;

@WebFluxTest(CommentRestController.class)
class CommentRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CommentRestController controller;

    @Test
    @DisplayName("Должен вернуть массив комментариев по id книги и сопоставить первый элемент с заданным")
    void shouldReturnAnArrayWithCommentsByBookId() {
        Flux<CommentDto> comments = Flux.just(
                new CommentDto(1L,"Comment_1", 1L, "Book_1"),
                new CommentDto(2L,"Comment_2", 1L, "Book_1"));

        Mockito.when(controller.listCommentsByBookId(1L)).thenReturn(comments);

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

        Mockito.when(controller.listComments()).thenReturn(comments);

        webClient.get().uri("/api/comment")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$[0].id").isEqualTo(1L)
                .jsonPath("$[0].textContent").isEqualTo("Comment_1")
                .jsonPath("$[0].bookId").isEqualTo(1L)
                .jsonPath("$[0].bookTitle").isEqualTo("Book_1");
    }

    @Test
    @DisplayName("Должен вернуть комментарий по id")
    void shouldReturnTheCommentById() {
        Mono<CommentDto> comment = Mono.just(
                new CommentDto(1L,"Comment_1", 1L, "Book_1"));

        Mockito.when(controller.commentById(1L)).thenReturn(comment);

        webClient.get().uri("/api/comment/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDto.class)
                .isEqualTo(new CommentDto(1L,"Comment_1", 1L, "Book_1"));
    }

    @Test
    @DisplayName("Должен сохранить комментарий")
    void shouldSaveNewComment() {
        CommentCreateDto comment = new CommentCreateDto("Test_save_comments", 1L);

        Mockito.when(controller.saveComment(comment)).thenReturn(Mono.just(
                new Comment(comment.textContent(), comment.bookId())
        ));

        webClient.post().uri("/api/comment")
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    @DisplayName("Должен изменить комментарий")
    void shouldSaveUpdateComment() {
        CommentUpdateDto comment = new CommentUpdateDto(1L, "Test_save_comments", 1L);

        Mockito.when(controller.editComment(comment, 1L)).thenReturn(Mono.just(
                new Comment(comment.id(), comment.textContent(), comment.bookId())
        ));

        webClient.put().uri("/api/comment/{commentId}", 1L)
                .body(Mono.just(comment), Comment.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("Должен удалить комментарий")
    void shouldDeleteComment() {

        given(controller.deleteComment(1L)).willReturn(Mono.empty());

        webClient.delete().uri("/api/comment/{commentId}", 1L)
                .exchange()
                .expectStatus().isOk();
    }
}
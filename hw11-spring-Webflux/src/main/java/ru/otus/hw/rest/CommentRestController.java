package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.custom.CommentRepositoryCustom;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class CommentRestController {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final CommentRepositoryCustom commentRepositoryCustom;

    @GetMapping("book/{bookId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CommentDto> listCommentsByBookId(@PathVariable Long bookId) {
        return commentRepositoryCustom.findByBookId(bookId)
                .switchIfEmpty(Mono.error(new NotFoundException("Comment with book id %d not found".formatted(bookId))));
    }

    @GetMapping("comment")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CommentDto> listComments() {
        return commentRepositoryCustom.findAll().switchIfEmpty(Mono.error(
                new NotFoundException("Comments not found")));
    }

    @GetMapping("comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CommentDto> commentById(@PathVariable("id") Long commentId) {
        return commentRepositoryCustom.findById(commentId).switchIfEmpty(Mono.error(
                new NotFoundException("Comment with id %d not found".formatted(commentId))));
    }

    @PostMapping("comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Comment> saveComment(@RequestBody CommentCreateDto commentCreateDto) {
        return bookRepository.findById(commentCreateDto.bookId()).switchIfEmpty(Mono.error(
                        new NotFoundException("Book with id %d not found".formatted(commentCreateDto.bookId()))))
                .flatMap(book -> {
                            return commentRepository.save(new Comment(commentCreateDto.textContent(), book.id()));
                        });
    }

    @PutMapping("comment/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Comment> editComment(@RequestBody CommentUpdateDto commentUpdateDto, @PathVariable Long commentId) {
        return bookRepository.findById(commentUpdateDto.bookId()).switchIfEmpty(Mono.error(
                        new NotFoundException("Book with id %d not found".formatted(commentUpdateDto.bookId()))))
                .flatMap(book -> {
                    return commentRepository
                            .save(new Comment(commentUpdateDto.id(), commentUpdateDto.textContent(), book.id()));
                });
    }

    @DeleteMapping("comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteComment(@PathVariable Long commentId) {
        return commentRepository.deleteById(commentId);
    }
}

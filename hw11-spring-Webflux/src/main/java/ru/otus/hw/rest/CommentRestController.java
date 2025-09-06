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
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.custom.CommentRepositoryCustomImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class CommentRestController {

    private final CommentRepository commentRepository;

    private final CommentRepositoryCustomImpl commentRepositoryCustomImpl;

    @GetMapping("book/{bookId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CommentDto> listCommentsByBookId(@PathVariable Long bookId) {
        return commentRepositoryCustomImpl.findByBookId(bookId);
    }

    @GetMapping("comment")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CommentDto> listComments() {
        return commentRepositoryCustomImpl.findAll();
    }

    @GetMapping("comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CommentDto> commentById(@PathVariable("id") Long id) {
        return commentRepositoryCustomImpl.findById(id);
    }

    @PostMapping("comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Comment> saveComment(@RequestBody CommentCreateDto commentCreateDto) {
        return commentRepository.save(new Comment(commentCreateDto.textContent(), commentCreateDto.bookId()));
    }

    @PutMapping("comment/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Comment> editComment(@RequestBody CommentUpdateDto commentUpdateDto, @PathVariable Long commentId) {
        return commentRepository.save(
                new Comment(commentId, commentUpdateDto.textContent(), commentUpdateDto.bookId()));
    }

    @DeleteMapping("comment/{commentId}")
    public Mono<Void> deleteComment(@PathVariable Long commentId) {
        return commentRepository.deleteById(commentId);
    }
}

package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.custom.CommentRepositoryCustom;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class CommentRestController {

    private final CommentRepository commentRepository;

    private final CommentRepositoryCustom commentRepositoryCustom;

    private final CommentMapper commentMapper;

    @GetMapping("book/{bookId}/comment")
    public Flux<CommentDto> listCommentsByBookId(@PathVariable Long bookId) {
        return commentRepositoryCustom.findByBookId(bookId).map(commentMapper::toDto);
    }

    @GetMapping("comment")
    public Flux<CommentDto> listComments() {
        return commentRepositoryCustom.findAll().map(commentMapper::toDto);
    }

    @GetMapping("comment/{id}")
    public Mono<CommentDto> commentById(@PathVariable("id") Long id) {
        return commentRepositoryCustom.findById(id).map(commentMapper::toDto);
    }

    @DeleteMapping("comment/{commentId}")
    public Mono<Void> deleteComment(@PathVariable Long commentId) {
        return commentRepository.deleteById(commentId);
    }
}

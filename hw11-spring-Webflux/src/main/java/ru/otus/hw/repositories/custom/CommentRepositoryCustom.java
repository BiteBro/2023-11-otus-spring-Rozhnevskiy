package ru.otus.hw.repositories.custom;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentDto;

public interface CommentRepositoryCustom {

    Flux<CommentDto> findAll();

    Mono<CommentDto> findById(Long id);

    Flux<CommentDto> findByBookId(Long bookId);
}

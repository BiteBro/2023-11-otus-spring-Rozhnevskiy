package ru.otus.hw.repositories.custom;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookDto;

public interface BookRepositoryCustom {

    Flux<BookDto> findAll();

    Mono<BookDto> findById(Long id);
}

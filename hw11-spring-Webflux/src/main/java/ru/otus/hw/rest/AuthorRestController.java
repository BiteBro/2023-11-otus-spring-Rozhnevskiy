package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mapper.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @GetMapping("author")
    public Flux<AuthorDto> listAuthors() {
        return authorRepository.findAll().map(authorMapper::toDto);
    }

    @GetMapping("author/{authorId}")
    public Mono<AuthorDto> findById(@PathVariable("authorId") Long id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException::new)).map(authorMapper::toDto);
    }

}

package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    @GetMapping("author")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Author> listAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("author/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Author> findById(@PathVariable("authorId") Long id) {
        return authorRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Author with id %d not found".formatted(id))));
    }

}

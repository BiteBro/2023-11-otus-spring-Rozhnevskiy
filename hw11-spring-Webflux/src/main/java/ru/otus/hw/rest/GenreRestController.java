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
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class GenreRestController {

    private final GenreRepository genreRepository;


    @GetMapping("genre")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Genre> listGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("genre/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Genre> getGenreById(@PathVariable("genreId") Long id) {
        return genreRepository.findById(id).switchIfEmpty(
                Mono.error(new NotFoundException("Genre with id %d not found".formatted(id)))
                );
    }

}

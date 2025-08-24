package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mapper.GenreMapper;
import ru.otus.hw.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class GenreRestController {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @GetMapping("genre")
    public Flux<GenreDto> listGenres() {
        var genres = genreRepository.findAll();
        return genres.map(genreMapper::toDto);
    }

    @GetMapping("genre/{genreId}")
    public Mono<GenreDto> getGenreById(@PathVariable("genreId") Long id) {
        var genre = genreRepository.findById(id);
        return genre.map(genreMapper::toDto);
    }

}

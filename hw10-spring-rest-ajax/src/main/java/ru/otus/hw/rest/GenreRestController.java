package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("api/genre")
    @ResponseStatus(HttpStatus.OK)
    public List<GenreDto> listGenres() {
        return genreService.findAll();
    }

    @GetMapping("api/genre/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public GenreDto getGenre(@PathVariable("genreId") Long genreId) {
        return genreService.findById(genreId);
    }

}

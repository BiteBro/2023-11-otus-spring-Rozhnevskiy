package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("genre")
    public List<GenreDto> listGenres() {
        return genreService.findAll();
    }

    @GetMapping("genre/{genreId}")
    public GenreDto getGenre(@PathVariable("genreId") Long genreId) {
        return genreService.findById(genreId);
    }

}

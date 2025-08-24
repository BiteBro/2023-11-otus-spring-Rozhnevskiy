package ru.otus.hw.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Genre;

@Component
public class GenreMapper {

    public Genre toModel(GenreDto dto) {
        return new Genre(dto.id(), dto.name());
    }

    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.id(), genre.name());
    }
}

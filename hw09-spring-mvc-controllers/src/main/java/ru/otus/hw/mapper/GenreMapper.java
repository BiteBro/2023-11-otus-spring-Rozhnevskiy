package ru.otus.hw.mapper;

import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.models.Genre;

public class GenreMapper implements Mapper<Genre, GenreDTO> {

    @Override
    public Genre toDomainObject(GenreDTO dto) {
        return new Genre(dto.getId(), dto.getName());
    }

    @Override
    public GenreDTO toDto(Genre genre) {
        return new GenreDTO(genre.getId(), genre.getName());
    }
}

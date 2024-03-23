package ru.otus.hw.services;

import ru.otus.hw.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(long id);

    GenreDto create(GenreDto authorDto);

    GenreDto update(GenreDto authorDto);

    void deleteById(long id);
}

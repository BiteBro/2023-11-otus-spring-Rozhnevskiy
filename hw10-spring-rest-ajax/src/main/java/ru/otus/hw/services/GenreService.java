package ru.otus.hw.services;

import ru.otus.hw.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();

    GenreDto findById(Long id);

    GenreDto create(GenreDto authorDto);

    GenreDto update(GenreDto authorDto);

    void deleteById(Long id);
}

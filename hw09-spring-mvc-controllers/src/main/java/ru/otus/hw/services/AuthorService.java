package ru.otus.hw.services;

import ru.otus.hw.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();

    AuthorDto findById(long id);

    AuthorDto create(AuthorDto authorDto);

    AuthorDto update(AuthorDto authorDto);

    void deleteById(long id);
}

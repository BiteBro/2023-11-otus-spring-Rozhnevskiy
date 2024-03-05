package ru.otus.hw.services;

import ru.otus.hw.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();
}

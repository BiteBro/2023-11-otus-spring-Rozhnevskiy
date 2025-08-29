package ru.otus.hw.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.otus.hw.models.Genre;


public interface GenreRepository extends R2dbcRepository<Genre, Long> {
}

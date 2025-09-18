package ru.otus.hw.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.otus.hw.models.Author;


public interface AuthorRepository extends R2dbcRepository<Author, Long> {
}

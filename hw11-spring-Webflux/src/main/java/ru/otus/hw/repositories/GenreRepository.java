package ru.otus.hw.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.hw.models.Genre;


public interface GenreRepository extends ReactiveCrudRepository<Genre, Long> {
}

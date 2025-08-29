package ru.otus.hw.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

public interface BookRepository extends R2dbcRepository<BookDto, Long> {
}

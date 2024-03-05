package ru.otus.hw.services;

import ru.otus.hw.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO findById(long id);

    List<BookDTO> findAll();

    BookDTO create(BookDTO bookDTO);

    BookDTO update(BookDTO bookDTO);

    void deleteById(long id);
}

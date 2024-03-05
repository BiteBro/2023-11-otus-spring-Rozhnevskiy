package ru.otus.hw.mapper;

import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;


public class BookMapper implements Mapper<Book, BookDTO> {

    @Override
    public Book toDomainObject(BookDTO bookDTO) {
        return new Book(bookDTO.getId(), bookDTO.getTitle(),
                new Author(bookDTO.getAuthor().getId(), bookDTO.getAuthor().getFullName()),
                new Genre(bookDTO.getGenre().getId(), bookDTO.getTitle()));
    }

    @Override
    public BookDTO toDto(Book book) {
        return new BookDTO(book.getId(), book.getTitle(),
                new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getFullName()),
                new GenreDTO(book.getGenre().getId(), book.getGenre().getName()));
    }
}

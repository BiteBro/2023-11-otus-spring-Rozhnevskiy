package ru.otus.hw.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@Component
public class BookMapper {

    public Book toCreateModel(BookCreateDto dto, Author author, Genre genre) {
        return new Book(dto.getId(), dto.getTitle(), author, genre);

    }

    public Book toUpdateModel(BookUpdateDto dto, Author author, Genre genre) {
        return new Book(dto.getId(), dto.getTitle(), author, genre);

    }

    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(),
                new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFullName()),
                new GenreDto(book.getGenre().getId(), book.getGenre().getName()));
    }

    public BookUpdateDto toUpdateDto(BookDto bookDto) {
        return new BookUpdateDto(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthor().getId(), bookDto.getGenre().getId());
    }

}

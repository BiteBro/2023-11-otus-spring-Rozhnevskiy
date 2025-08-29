package ru.otus.hw.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@Component
@RequiredArgsConstructor
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().id(), book.getGenre().id());
    }

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public Book toCreateModel(BookCreateDto dto, Author author, Genre genre) {
        return new Book(0L, dto.getTitle(), author, genre);

    }

    public Book toUpdateModel(BookUpdateDto dto, Author author, Genre genre) {
        return new Book(dto.getId(), dto.getTitle(), author, genre);

    }

    public Book toModel(BookDto dto, Author author, Genre genre) {
        return new Book(dto.id(), dto.title(), author, genre);

    }

    /*public BookUpdateDto toUpdateDto(BookDto bookDto) {
        return new BookUpdateDto(bookDto.id(), bookDto.title(),
                bookDto.author().id(), bookDto.genre().id());
    }*/


}

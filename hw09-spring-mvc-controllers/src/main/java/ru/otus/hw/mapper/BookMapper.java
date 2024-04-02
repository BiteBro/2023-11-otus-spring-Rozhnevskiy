package ru.otus.hw.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public Book toCreateModel(BookCreateDto dto, Author author, Genre genre) {
        return new Book(0, dto.getTitle(), author, genre);

    }

    public Book toUpdateModel(BookUpdateDto dto, Author author, Genre genre) {
        return new Book(dto.getId(), dto.getTitle(), author, genre);

    }

    public Book toModel(BookDto dto, Author author, Genre genre) {
        return new Book(dto.getId(), dto.getTitle(), author, genre);

    }

    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(),
                authorMapper.toDto(book.getAuthor()), genreMapper.toDto(book.getGenre()));
    }

    public BookUpdateDto toUpdateDto(BookDto bookDto) {
        return new BookUpdateDto(bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthor().getId(), bookDto.getGenre().getId());
    }

}

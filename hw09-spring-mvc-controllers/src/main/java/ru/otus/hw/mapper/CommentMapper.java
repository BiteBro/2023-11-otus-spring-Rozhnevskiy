package ru.otus.hw.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

@Component
public class CommentMapper {

    public Comment toModel(CommentDto dto) {
        Author author = new Author(dto.getBook().getAuthor().getId(), dto.getBook().getAuthor().getFullName());
        Genre genre = new Genre(dto.getBook().getGenre().getId(), dto.getBook().getGenre().getName());
        Book book = new Book(dto.getBook().getId(), dto.getBook().getTitle(), author, genre);
        return new Comment(dto.getId(), dto.getTextContent(), book);
    }

    public CommentCreateDto toCreateDto(Comment comment) {
        return new CommentCreateDto(comment.getId(), comment.getTextContent(), comment.getBook().getId());
    }

    public CommentUpdateDto toUpdateDto(CommentDto comment) {
        return new CommentUpdateDto(comment.getId(), comment.getTextContent(), comment.getBook().getId());
    }

    public CommentDto toDto(Comment comment) {
        AuthorDto authorDTO = new AuthorMapper().toDto(comment.getBook().getAuthor());
        GenreDto genreDTO = new GenreMapper().toDto(comment.getBook().getGenre());
        BookDto bookDTO = new BookDto(comment.getBook().getId(), comment.getBook().getTitle(), authorDTO, genreDTO);
        return new CommentDto(comment.getId(), comment.getTextContent(), bookDTO);
    }
}

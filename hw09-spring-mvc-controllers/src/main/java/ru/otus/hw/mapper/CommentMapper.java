package ru.otus.hw.mapper;

import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.dto.GenreDTO;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

public class CommentMapper implements Mapper<Comment, CommentDTO> {

    @Override
    public Comment toDomainObject(CommentDTO dto) {
        Author author = new Author(dto.getBook().getAuthor().getId(), dto.getBook().getAuthor().getFullName());
        Genre genre = new Genre(dto.getBook().getGenre().getId(), dto.getBook().getGenre().getName());
        Book book = new Book(dto.getBook().getId(), dto.getBook().getTitle(), author, genre);
        return new Comment(dto.getId(), dto.getTextContent(), book);
    }

    @Override
    public CommentDTO toDto(Comment comment) {
        AuthorDTO authorDTO = new AuthorMapper().toDto(comment.getBook().getAuthor());
        GenreDTO genreDTO = new GenreMapper().toDto(comment.getBook().getGenre());
        BookDTO bookDTO = new BookDTO(comment.getBook().getId(), comment.getBook().getTitle(), authorDTO, genreDTO);
        return new CommentDTO(comment.getId(), comment.getTextContent(), bookDTO);
    }
}

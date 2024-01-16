package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Objects;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        var params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    """
                            SELECT books.id, books.title, books.author_id, authors.full_name, books.genre_id, genres.name
                            FROM books
                            JOIN authors ON books.author_id = authors.id
                            JOIN genres ON books.genre_id = genres.id
                            WHERE books.id = :id""",
                    params, new BookRowMapper())
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return jdbcOperations.query(
                """
                        SELECT books.id, books.title, books.author_id, authors.full_name, books.genre_id, genres.name
                        FROM books
                        JOIN authors ON books.author_id = authors.id
                        JOIN genres ON books.genre_id = genres.id""",
                new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        var params = Collections.singletonMap("id", id);
        jdbcOperations.update("DELETE FROM books WHERE id = :id", params);
    }

    private Book insert(Book book) {
        Map<String, Object> bookParams = Map.of(
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId());
        MapSqlParameterSource params = new MapSqlParameterSource(bookParams);

        var keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(
                "INSERT INTO books (title, author_id, genre_id) VALUES (:title, :author_id, :genre_id)",
                params, keyHolder);
        book.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return book;
    }

    private Book update(Book book) {
        Map<String, Object> params = Map.of("id", book.getId(), "title", book.getTitle(),
                "author_id", book.getAuthor().getId(), "genre_id", book.getGenre().getId());
        int count = jdbcOperations.update(
                "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id",
                params);
        if (count == 0) {
            throw new EntityNotFoundException("");
        }
        return book;
    }


    @RequiredArgsConstructor
    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("id"), rs.getString("title"),
                    new Author(rs.getLong("author_id"), rs.getString("full_name")),
                    new Genre(rs.getLong("genre_id"), rs.getString("name")));
        }
    }

}

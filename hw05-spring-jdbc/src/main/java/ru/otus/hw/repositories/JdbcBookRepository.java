package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        var params = Collections.singletonMap("id", id);
        return Optional.ofNullable(jdbcOperations.queryForObject(
                "select books.id, books.title, " +
                        "books.author_id, authors.full_name, " +
                        "books.genre_id, genres.name from books " +
                        "join authors ON books.author_id = authors.id " +
                        "join genres ON books.genre_id = genres.id " +
                        "where books.id = :id",
                params, new BookRowMapper())
        );
    }

    @Override
    public List<Book> findAll() {
        return jdbcOperations.query(
                "select books.id, books.title, " +
                        "books.author_id, authors.full_name, " +
                        "books.genre_id, genres.name from books " +
                        "join authors ON books.author_id = authors.id " +
                        "join genres ON books.genre_id = genres.id",
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
        jdbcOperations.update("delete from books where id=:id", params);
    }

       private Book insert(Book book) {
        var params = Map.of(
                "title", book.getTitle(),
                "author_id", book.getAuthor(),
                "genre_id", book.getGenre());
        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(
                "insert into books (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                params, keyHolder);
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        //...
        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
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

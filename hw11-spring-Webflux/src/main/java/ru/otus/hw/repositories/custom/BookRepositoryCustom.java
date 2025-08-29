package ru.otus.hw.repositories.custom;

import io.r2dbc.spi.Readable;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@AllArgsConstructor
@Repository
public class BookRepositoryCustom {

    private static final String SQL_ALL = """
            SELECT books.id, books.title, books.author_id, authors.full_name, genre_id, genres.name
            FROM books
            JOIN authors ON books.author_id = authors.id
            JOIN genres ON books.genre_id = genres.id
        """;

    private static final String SQL_BY_ID = """
            SELECT books.id, books.title, books.author_id, authors.full_name, genre_id, genres.name
            FROM books
            JOIN authors ON books.author_id = authors.id
            JOIN genres ON books.genre_id = genres.id
            WHERE books.id = $1
        """;

    private final R2dbcEntityTemplate template;

    public Flux<Book> findAll() {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(SQL_ALL).execute())
                        .switchIfEmpty(Flux.error(NotFoundException::new))
                        .flatMap(result -> result.map(this::mapper)));
    }

    public Mono<Book> findById(Long id) {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(SQL_BY_ID).bind("$1", id).execute())
                        .switchIfEmpty(Flux.error(NotFoundException::new))
                        .flatMap(result -> result.map(this::mapper))).single();
    }


    /*public Mono<Book> save(BookCreateDto book) {
        return template.getDatabaseClient().inConnection(connection ->
                Mono.from(connection.createStatement(SQL_BY_SAVE)
                                .bind("$1", book.getTitle())
                                .bind("$2", book.getAuthorId())
                                .bind("$3", book.getGenreId()).execute())
                        .switchIfEmpty(Mono.error(NotFoundException::new))
                        .map(this::mapperBookDto));
    }*/

    private Book mapper(Readable selectedRecord) {
        return new Book(selectedRecord.get("id", Long.class),
                selectedRecord.get("title", String.class),
                new Author(selectedRecord.get("author_id", Long.class),
                        selectedRecord.get("full_name", String.class)),
                new Genre(selectedRecord.get("genre_id", Long.class),
                        selectedRecord.get("name", String.class))
        );
    }
}

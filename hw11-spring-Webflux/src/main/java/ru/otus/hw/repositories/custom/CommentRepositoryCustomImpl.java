package ru.otus.hw.repositories.custom;

import io.r2dbc.spi.Readable;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.NotFoundException;

@AllArgsConstructor
@Repository
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private static final String SQL_ALL = """
            SELECT comments.id, comments.text_content, comments.book_id, books.title
            FROM comments JOIN books ON comments.book_id = books.id
        """;

    private static final String SQL_BY_ID = """
            SELECT comments.id, comments.text_content, comments.book_id, books.title
            FROM comments JOIN books ON comments.book_id = books.id
            WHERE comments.id = $1
        """;

    private static final String SQL_BY_BOOK_ID = """
            SELECT comments.id, comments.text_content, comments.book_id, books.title
            FROM comments JOIN books ON comments.book_id = books.id
            WHERE comments.book_id = $1
        """;

    private final R2dbcEntityTemplate template;

    public Flux<CommentDto> findAll() {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(SQL_ALL).execute())
                        .switchIfEmpty(Flux.error(NotFoundException::new))
                        .flatMap(result -> result.map(this::mapper)));
    }

    public Mono<CommentDto> findById(Long id) {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(SQL_BY_ID).bind("$1", id).execute())
                        .switchIfEmpty(Flux.error(NotFoundException::new))
                        .flatMap(result -> result.map(this::mapper))).single();
    }

    public Flux<CommentDto> findByBookId(Long bookId) {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(SQL_BY_BOOK_ID).bind("$1", bookId).execute())
                        .switchIfEmpty(Flux.error(NotFoundException::new))
                        .flatMap(result -> result.map(this::mapper)));
    }

    private CommentDto mapper(Readable selectedRecord) {
        return new CommentDto(selectedRecord.get("id", Long.class),
                selectedRecord.get("text_content", String.class),
                selectedRecord.get("book_id", Long.class),
                selectedRecord.get("title", String.class)
        );
    }
}

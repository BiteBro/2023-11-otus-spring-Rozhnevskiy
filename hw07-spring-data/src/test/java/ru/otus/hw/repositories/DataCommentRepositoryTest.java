package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями ")
@DataJpaTest
public class DataCommentRepositoryTest {

    @Autowired
    private CommentRepository repository;

    private List<Comment> dbComments;

    @BeforeEach
    void setUp() {
        List<Genre> dbGenres = getDbGenres();
        List<Author> dbAuthors = getDbAuthors();
        List<Book> dbBooks = getDbBooks(dbAuthors, dbGenres);
        dbComments = getDbComments(dbBooks);
    }

    @DisplayName("должен загружать комментарии к книге по id книги")
    @ParameterizedTest
    @MethodSource("getDbBooks")
    void shouldReturnCorrectCommentByBookId(Book expectedBook) {
        var actualComment = repository.findByBookId(expectedBook.getId());
        var expectedComment = dbComments.stream()
                .filter(comment -> comment.getBook().getId() == expectedBook.getId()).toList();
        System.out.println(actualComment);
        System.out.println(expectedBook);
        assertThat(actualComment).usingRecursiveComparison()
                .ignoringFields("book").isEqualTo(expectedComment);
    }

    private static List<Comment> getDbComments(List<Book> dbBooks) {
        List<Comment> comments = new ArrayList<>();
        IntStream.range(1, 4).boxed().forEach(
                id -> {
                    comments.add(new Comment(id, "Comment_" + id, dbBooks.get(id - 1)));
                    comments.add(new Comment(id + 3, "Comment_" + (id + 3), dbBooks.get(id - 1)));
                });
        return comments;
    }


    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
        return IntStream.range(1, 4).boxed()
            .map(id -> new Book(
                    id, "BookTitle_" + id, dbAuthors.get(id - 1), dbGenres.get(id - 1)))
            .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }
}

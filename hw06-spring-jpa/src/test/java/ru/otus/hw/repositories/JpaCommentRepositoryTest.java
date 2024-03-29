package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
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
@Import(JpaCommentRepository.class)
public class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository repository;

    private List<Comment> dbComments;

    private List<Author> dbAuthors;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp() {
        dbGenres = getDbGenres();
        dbAuthors = getDbAuthors();
        List<Book> dbBooks = getDbBooks(dbAuthors, dbGenres);
        dbComments = getDbComments(dbBooks);
    }

    @DisplayName("должен загружать комментарий по id")
    @ParameterizedTest
    @MethodSource("getDbComments")
    void shouldReturnCorrectCommentById(Comment expectedComment) {
        var actualComment = repository.findById(expectedComment.getId());
        assertThat(actualComment).isPresent()
                .get().usingRecursiveComparison()
                .ignoringFields("book")
                .isEqualTo(expectedComment);
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

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        var expectedComment = new Comment(0, "what",
                new Book(0, "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0)));
        var returnedComment = repository.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(repository.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {
        var expectedComment = new Comment(1L, "hello",
                new Book(1L, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2)));

        assertThat(repository.findById(expectedComment.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(expectedComment);

        var returnedComment = repository.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(repository.findById(returnedComment.getId()))
                .isPresent()
                .get()
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {
        assertThat(repository.findById(1L)).isPresent();
        repository.deleteById(1L);
        assertThat(repository.findById(1L)).isEmpty();
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

    private static List<Comment> getDbComments() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        var dbBooks = getDbBooks(dbAuthors, dbGenres);
        return getDbComments(dbBooks);
    }

    private static List<Book> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }
}

package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Book repository test")
public class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository repository;

    private final Author author = new Author("one", "Author_1");

    private final Genre genre = new Genre("one", "Genre_1");

    @Test
    @DisplayName("Должен загружать книгу по id")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnBookById() {
        var book = new Book("one", "Book_1", author, genre);
        var expectedBook = repository.save(book);
        var actualBook = repository.findById(expectedBook.getId());
        System.out.println(actualBook);
        assertThat(actualBook).isPresent().get().usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book("BookTitle", author, genre);

        var returnedBook = repository.save(expectedBook);

        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() != null)
                .usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(expectedBook);

        assertThat(repository.findById(returnedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedBook);
    }

    @DisplayName("Должен сохранять измененную книгу")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book("one", "BookTitle", author, genre);
        var returnedBook = repository.save(expectedBook);

        assertThat(repository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .isNotEqualTo(returnedBook);

        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() != null)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(repository.findById(expectedBook.getId()))
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(returnedBook);
    }

}

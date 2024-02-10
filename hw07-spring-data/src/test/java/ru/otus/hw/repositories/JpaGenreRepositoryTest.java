package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с книгами ")
@DataJpaTest
@Import(JpaGenreRepository.class)
public class JpaGenreRepositoryTest {

    @Autowired
    private JpaGenreRepository repository;

    private List<Genre> dbGenres;

    @BeforeEach
    void setUp(){
        dbGenres = getDbGenres();
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    public void shouldReturnCorrectGenresList(){
        var actualGenres = repository.findAll();
        var expectedGenres = dbGenres;
        assertThat(actualGenres)
                .usingRecursiveComparison()
                .isEqualTo(expectedGenres);
        actualGenres.forEach(System.out::println);
    }

    @DisplayName("должен загружать жанр по id")
    @ParameterizedTest
    @MethodSource("getDbGenres")
    public void shouldReturnCorrectGenreById(Genre expectedGenre){
        var actualGenre = repository.findById(expectedGenre.getId());
        System.out.println(expectedGenre);
        System.out.println(actualGenre);
        assertThat(actualGenre).isPresent()
                .get().usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    private static List<Genre> getDbGenres(){
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }
}

package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.repositories.custom.BookRepositoryCustom;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BookRestController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepositoryCustom bookRepositoryCustom;

    private final BookMapper bookMapper;

    @GetMapping("book")
    public Flux<BookDto> listBooks() {
        return bookRepositoryCustom.findAll().map(bookMapper::toDto);
    }

    @GetMapping("book/{bookId}")
    public Mono<BookDto> getBook(@PathVariable Long bookId) {
        return bookRepositoryCustom.findById(bookId).map(bookMapper::toDto);
    }

    /*@PutMapping("book/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> editBook(@RequestBody BookUpdateDto bookUpdateDto, @PathVariable Long bookId) {
        bookUpdateDto.setId(bookId);
        return bookRepository.update(bookUpdateDto);
    }*/

    /*@PostMapping("book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> saveBook(@RequestBody BookCreateDto bookCreateDto) {
        return bookRepository.save(new Book(0L, bookCreateDto.getTitle(), null, null));
    }*/

    @DeleteMapping("book/{bookId}")
    public Mono<Void> delete(@PathVariable Long bookId) {
        return bookRepository.deleteById(bookId);
    }
}

/*
return Mono.zip(
        authorRepository.findById(dto.getAuthorId())
        .switchIfEmpty(Mono.error(NotFoundException::new)),
        genreRepository.findById(dto.getGenreId())
        .switchIfEmpty(Mono.error(NotFoundException::new)),
        (author, genre) -> new Book(dto.getTitle(), author, genre))
        .flatMap(bookRepository::save)
        .map(mapper::toDto);

        return authorRepository.findById(bookCreateDto.getAuthorId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Author with id %s not found".formatted(bookCreateDto.getAuthorId()))
                ))
                .zipWith(genreRepository.findById(bookCreateDto.getGenreId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                "Genre with id %s not found".formatted(bookCreateDto.getGenreId()))
                        )))
                .flatMap(tuple -> bookRepository.save(new Book(
                        bookCreateDto.getTitle(), tuple.getT1(), tuple.getT2()))
                        .map(bookMapper::toDto));

    }
        */

package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.repositories.custom.BookRepositoryCustomImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BookRestController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepositoryCustomImpl bookRepositoryCustomImpl;

    @GetMapping("book")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> listBooks() {
        return bookRepositoryCustomImpl.findAll();
    }

    @GetMapping("book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BookDto> getBook(@PathVariable Long bookId) {
        return bookRepositoryCustomImpl.findById(bookId).switchIfEmpty(
                Mono.error(new NotFoundException("Book with id %d not found".formatted(bookId)))
        );
    }

    @PutMapping("book/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> editBook(@RequestBody BookUpdateDto bookUpdateDto, @PathVariable Long bookId) {
        return Mono.zip(
            authorRepository.findById(bookUpdateDto.authorId())
                    .switchIfEmpty(Mono.error(
                            new NotFoundException("Author with id %d not found".formatted(bookUpdateDto.authorId())))),
            genreRepository.findById(bookUpdateDto.genreId())
                    .switchIfEmpty(Mono.error(
                            new NotFoundException("Genre with id %d not found".formatted(bookUpdateDto.genreId())))))
            .flatMap(tuple -> bookRepository.findById(bookUpdateDto.id())
                .switchIfEmpty(Mono.error(new NotFoundException("Book with id %d not found".formatted(bookId))))
                .flatMap(book -> {
                    return bookRepository.save(
                        new Book(bookUpdateDto.id(), bookUpdateDto.title(), tuple.getT1().id(), tuple.getT2().id()));
                }));
    }

    @PostMapping("book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> saveBook(@RequestBody BookCreateDto bookCreateDto) {
        return Mono.zip(
                authorRepository.findById(bookCreateDto.authorId()).switchIfEmpty(Mono.error(
                new NotFoundException("Author with id %d not found".formatted(bookCreateDto.authorId())))),
                genreRepository.findById(bookCreateDto.genreId()).switchIfEmpty(Mono.error(
                new NotFoundException("Genre with id %d not found".formatted(bookCreateDto.genreId())))),
                (author, genre) -> new Book(bookCreateDto.title(), author.id(), genre.id()))
                .flatMap(bookRepository::save);
    }

    @DeleteMapping("book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable Long bookId) {
        return bookRepository.deleteById(bookId);
    }
}


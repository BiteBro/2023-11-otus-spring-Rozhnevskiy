package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BookRestController {

    private final BookService bookService;

    @GetMapping("book")
    public List<BookDto> listBooks() {
        return bookService.findAll();
    }

    @GetMapping("book/{bookId}")
    public BookDto getBook(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    @PutMapping("book/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto editBook(@RequestBody BookUpdateDto bookUpdateDto, @PathVariable Long bookId) {
        bookUpdateDto.setId(bookId);
        return bookService.update(bookUpdateDto);
    }

    @PostMapping("book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto saveBook(@RequestBody BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @DeleteMapping("book/{bookId}")
    public void delete(@PathVariable Long bookId) {
        bookService.deleteById(bookId);
    }
}

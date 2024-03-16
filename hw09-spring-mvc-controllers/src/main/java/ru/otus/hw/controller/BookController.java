package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookMapper bookMapper;

    @GetMapping("/")
    public String listBooks(Model model) {
        List<BookDto> bookDtoList = bookService.findAll();
        model.addAttribute("bookDtoList", bookDtoList);
        return "books_list";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("bookId") long bookId, Model model) {
        BookDto bookDto = bookService.findById(bookId);
        var authorsDto = authorService.findAll();
        var genresDto = genreService.findAll();
        model.addAttribute("bookUpdateDto", bookMapper.toUpdateDto(bookDto));
        model.addAttribute("authors", authorsDto);
        model.addAttribute("genres", genresDto);
        return "book_edit";
    }

    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("bookUpdateDto") BookUpdateDto bookUpdateDto,
                           BindingResult bindingResult, Model model) {
        bookService.update(bookUpdateDto);
        return "redirect:/";
    }

    @GetMapping("/save")
    public String saveBook(Model model) {
        var authorsDto = authorService.findAll();
        var genresDto = genreService.findAll();
        model.addAttribute("bookCreateDto", new BookCreateDto());
        model.addAttribute("authors", authorsDto);
        model.addAttribute("genres", genresDto);
        return "book_save";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("bookCreateDto") BookCreateDto bookCreateDto,
                           BindingResult bindingResult, Model model) {
        bookService.create(bookCreateDto);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("bookId") long bookId) {
        bookService.deleteById(bookId);
        return "redirect:/";
    }
}

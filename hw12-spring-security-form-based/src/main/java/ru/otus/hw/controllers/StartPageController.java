package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

@Controller
@RequiredArgsConstructor
public class StartPageController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String startPage() {
        return "start_page";
    }

    @GetMapping("/book")
    public String listBooks(Model model) {
        model.addAttribute("bookDtoList", bookService.findAll());
        return "book_list";
    }

    @GetMapping("/author")
    public String listAuthors(Model model) {
        model.addAttribute("authorDtoList", authorService.findAll());
        return "author_list";
    }

    @GetMapping("/genre")
    public String listGenres(Model model) {
        model.addAttribute("genreDtoList", genreService.findAll());
        return "genre_list";
    }
}

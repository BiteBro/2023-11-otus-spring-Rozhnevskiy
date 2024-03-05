package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDTO;
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

    @GetMapping("/")
    public String listBooks(Model model) {
        List<BookDTO> bookDTOList = bookService.findAll();
        model.addAttribute("books", bookDTOList);
        return "books_list";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        var bookDTO = bookService.findById(id);
        var authorsDTO = authorService.findAll();
        var genresDTO = genreService.findAll();
        model.addAttribute("book", bookDTO);
        model.addAttribute("authors", authorsDTO);
        model.addAttribute("genres", genresDTO);
        return "edit";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult, Model model) {
        System.out.println("i call post method editBook");
        bookService.update(bookDTO);
        return "redirect:/";
    }

    @GetMapping("/save")
    public String saveBook(Model model) {
        var authorsDTO = authorService.findAll();
        var genresDTO = genreService.findAll();
        model.addAttribute("book", new BookDTO());
        model.addAttribute("authors", authorsDTO);
        model.addAttribute("genres", genresDTO);
        return "save";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") BookDTO bookDTO, BindingResult bindingResult, Model model) {
        System.out.println("i call post method saveBook");
        bookService.create(bookDTO);
        return "redirect:/";
    }

    @PostMapping("/delete{id}")
    public String delete(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}

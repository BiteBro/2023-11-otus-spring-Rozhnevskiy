package ru.otus.hw.rest;

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

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookMapper bookMapper;

    @GetMapping("/book/edit")
    public String editBook(@RequestParam("bookId") long bookId, Model model) {
        BookDto bookDto = bookService.findById(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookUpdateDto", bookMapper.toUpdateDto(bookDto));
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book_edit";
    }

    @PostMapping("/book/edit")
    public String editBook(@Valid @ModelAttribute("bookUpdateDto") BookUpdateDto bookUpdateDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookId", bookUpdateDto.getId());
            model.addAttribute("bookUpdateDto", bookUpdateDto);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "book_edit";
        }
        bookService.update(bookUpdateDto);
        return "redirect:/book";
    }

    @GetMapping("/book/save")
    public String saveBook(Model model) {
        model.addAttribute("bookCreateDto", new BookCreateDto());
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", genreService.findAll());
        return "book_add";
    }

    @PostMapping("/book/save")
    public String saveBook(@Valid @ModelAttribute("bookCreateDto") BookCreateDto bookCreateDto,
                           BindingResult bindingResult, Model model) {
        System.out.println(bookCreateDto);
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookCreateDto", bookCreateDto);
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("genres", genreService.findAll());
            return "book_add";
        }
        bookService.create(bookCreateDto);
        return "redirect:/book";
    }

    @PostMapping("/book/delete")
    public String delete(@RequestParam("bookId") long bookId) {
        bookService.deleteById(bookId);
        return "redirect:/book";
    }
}

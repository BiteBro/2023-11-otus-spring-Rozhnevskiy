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
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author/edit")
    public String editAuthor(@RequestParam("authorId") long authorId, Model model) {
        var authorDto = authorService.findById(authorId);
        model.addAttribute("authorId", authorId);
        model.addAttribute("authorDto", authorDto);
        return "author_edit";
    }

    @PostMapping("/author/edit")
    public String editAuthor(@Valid @ModelAttribute("authorDto") AuthorDto authorDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authorId", authorDto.getId());
            model.addAttribute("authorDto", authorDto);
            return "author_edit";
        }
        authorService.update(authorDto);
        return "redirect:/author";
    }

    @GetMapping("/author/save")
    public String saveAuthor(Model model) {
        model.addAttribute("authorDto", new AuthorDto(0L, ""));
        return "author_add";
    }

    @PostMapping("/author/save")
    public String saveAuthor(@Valid @ModelAttribute("authorDto") AuthorDto authorDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authorDto", authorDto);
            return "author_add";
        }
        authorService.create(authorDto);
        return "redirect:/author";
    }

}

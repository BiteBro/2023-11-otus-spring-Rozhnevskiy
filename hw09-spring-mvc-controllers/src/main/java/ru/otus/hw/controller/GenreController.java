package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre/edit")
    public String editGenre(@RequestParam("genreId") long genreId, Model model) {
        var genreDto = genreService.findById(genreId);
        model.addAttribute("genreDto", genreDto);
        return "genre_edit";
    }

    @PostMapping("/genre/edit")
    public String editGenre(@Valid @ModelAttribute("genreDto") GenreDto genreDto) {
        genreService.update(genreDto);
        return "redirect:/genre";
    }

    @GetMapping("/genre/save")
    public String saveGenre(Model model) {
        model.addAttribute("genreDto", new GenreDto());
        return "genre_add";
    }

    @PostMapping("/genre/save")
    public String saveGenre(@Valid @ModelAttribute("genreDto") GenreDto genreDto) {
        genreService.create(genreDto);
        return "redirect:/genre";
    }

}

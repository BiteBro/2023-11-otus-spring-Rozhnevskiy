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
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre/edit")
    public String editGenre(@RequestParam("genreId") long genreId, Model model) {
        var genreDto = genreService.findById(genreId);
        model.addAttribute("genreId", genreDto.getId());
        model.addAttribute("genreDto", genreDto);
        return "genre_edit";
    }

    @PostMapping("/genre/edit")
    public String editGenre(@Valid @ModelAttribute("genreDto") GenreDto genreDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genreId", genreDto.getId());
            model.addAttribute("genreDto", genreDto);
            return "genre_edit";
        }
        genreService.update(genreDto);
        return "redirect:/genre";
    }

    @GetMapping("/genre/save")
    public String saveGenre(Model model) {
        model.addAttribute("genreDto", new GenreDto(0L, ""));
        return "genre_add";
    }

    @PostMapping("/genre/save")
    public String saveGenre(@Valid @ModelAttribute("genreDto") GenreDto genreDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(genreDto);
            model.addAttribute("genreDto", genreDto);
            return "genre_add";
        }
        genreService.create(genreDto);
        return "redirect:/genre";
    }

}

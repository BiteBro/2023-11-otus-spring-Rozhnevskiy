package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("author")
    public List<AuthorDto> listAuthors() {
        return authorService.findAll();
    }

    @GetMapping("author/{authorId}")
    public AuthorDto getAuthor(@PathVariable("authorId") Long authorId) {
        return authorService.findById(authorId);
    }

}

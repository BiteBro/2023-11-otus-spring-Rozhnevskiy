package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("api/author")
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> listAuthors() {
        return authorService.findAll();
    }

    @GetMapping("api/author/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto getAuthor(@PathVariable("authorId") Long authorId) {
        return authorService.findById(authorId);
    }

}

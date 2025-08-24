package ru.otus.hw.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;

@Component
public class AuthorMapper {

    public Author toModel(AuthorDto dto) {
        return new Author(dto.id(), dto.fullName());
    }

    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.id(), author.fullName());
    }


}

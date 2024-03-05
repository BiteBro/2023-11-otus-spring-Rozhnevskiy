package ru.otus.hw.mapper;

import ru.otus.hw.dto.AuthorDTO;
import ru.otus.hw.models.Author;

public class AuthorMapper implements Mapper<Author, AuthorDTO> {

    @Override
    public Author toDomainObject(AuthorDTO dto) {
        return new Author(dto.getId(), dto.getFullName());
    }

    @Override
    public AuthorDTO toDto(Author author) {
        return new AuthorDTO(author.getId(), author.getFullName());
    }
}

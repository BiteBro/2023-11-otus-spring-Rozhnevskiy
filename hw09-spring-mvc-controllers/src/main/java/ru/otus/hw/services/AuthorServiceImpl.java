package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> findAll() {
        var authors = authorRepository.findAll();
        return authors.stream().map(new AuthorMapper()::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public AuthorDto findById(long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author with id %d not found".formatted(id)));
        return authorMapper.toDto(author);
    }

    @Transactional
    @Override
    public AuthorDto create(AuthorDto authorDto) {
        var author = authorMapper.toModel(authorDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Transactional
    @Override
    public AuthorDto update(AuthorDto authorDto) {
        var author = authorMapper.toModel(authorDto);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }
}

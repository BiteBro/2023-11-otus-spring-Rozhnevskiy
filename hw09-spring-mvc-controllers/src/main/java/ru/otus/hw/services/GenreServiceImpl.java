package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.GenreMapper;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> findAll() {
        var genres = genreRepository.findAll();
        return genres.stream().map(new GenreMapper()::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public GenreDto findById(long id) {
        var genre = genreRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Genre with id %d not found".formatted(id)));
        return genreMapper.toDto(genre);
    }

    @Transactional
    @Override
    public GenreDto create(GenreDto genreDto) {
        var genre = genreMapper.toModel(genreDto);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Transactional
    @Override
    public GenreDto update(GenreDto genreDto) {
        var genre = genreMapper.toModel(genreDto);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}

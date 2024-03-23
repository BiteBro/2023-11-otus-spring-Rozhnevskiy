package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        return bookMapper.toDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll() {
        var books = bookRepository.findAll();
        return books.stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        var author = authorRepository.findById(bookCreateDto.getAuthorId()).orElseThrow(() ->
                new EntityNotFoundException("Author with id %d not found".formatted(bookCreateDto.getAuthorId())));
        var genre = genreRepository.findById(bookCreateDto.getGenreId()).orElseThrow(() ->
                new EntityNotFoundException("Genre with id %d not found".formatted(bookCreateDto.getGenreId())));
        var book = bookMapper.toCreateModel(bookCreateDto, author, genre);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto bookUpdateDto) {
        var book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("Book with id %d not found".formatted(bookUpdateDto.getId())));
        var author = authorRepository.findById(bookUpdateDto.getAuthorId()).orElseThrow(() ->
                new EntityNotFoundException("Author with id %d not found".formatted(bookUpdateDto.getAuthorId())));
        var genre = genreRepository.findById(bookUpdateDto.getGenreId()).orElseThrow(() ->
                new EntityNotFoundException("Genre with id %d not found".formatted(bookUpdateDto.getGenreId())));
        var updateBook = bookMapper.toUpdateModel(bookUpdateDto, author, genre);
        return bookMapper.toDto(bookRepository.save(updateBook));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

}

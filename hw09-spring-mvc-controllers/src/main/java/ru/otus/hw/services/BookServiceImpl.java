package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.models.Book;
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

    @Transactional(readOnly = true)
    @Override
    public BookDTO findById(long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author with id %d not found".formatted(bookId)));
        return new BookMapper().toDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDTO> findAll() {
        var books = bookRepository.findAll();
        return books.stream().map(new BookMapper()::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDTO create(BookDTO bookDTO) {
        var author = authorRepository.findById(bookDTO.getAuthor().getId()).orElseThrow(() ->
                new EntityNotFoundException("Author with id %d not found".formatted(bookDTO.getAuthor().getId())));
        var genre = genreRepository.findById(bookDTO.getGenre().getId()).orElseThrow(() ->
                new EntityNotFoundException("Genre with id %d not found".formatted(bookDTO.getGenre().getId())));
        var book = new Book(0, bookDTO.getTitle(), author, genre);
        return new BookMapper().toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public BookDTO update(BookDTO bookDTO) {
        Book book = bookRepository.findById(bookDTO.getId()).orElseThrow(() ->
                new EntityNotFoundException("Book with id %d not found".formatted(bookDTO.getId())));
        var author = authorRepository.findById(bookDTO.getAuthor().getId()).orElseThrow(() ->
                new EntityNotFoundException("Author with id %d not found".formatted(bookDTO.getAuthor().getId())));
        var genre = genreRepository.findById(bookDTO.getGenre().getId()).orElseThrow(() ->
                new EntityNotFoundException("Genre with id %d not found".formatted(bookDTO.getGenre().getId())));
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setGenre(genre);
        return new BookMapper().toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

}

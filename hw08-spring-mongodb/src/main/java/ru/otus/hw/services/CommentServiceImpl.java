package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByBookId(String id) {
        return commentRepository.findByBookId(id);
    }

    @Override
    public Comment create(String textContent, String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        Comment comment = new Comment(textContent, book);
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(String id, String textContent) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        comment.setTextContent(textContent);
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteByBookId(String id) {
        commentRepository.deleteByBookId(id);
    }

}

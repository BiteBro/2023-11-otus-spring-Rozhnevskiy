package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(long id) {
        return commentRepository.findByBookId(id);
    }

    @Transactional
    @Override
    public Comment create(String textContent, long bookId) {
        Comment comment = createOrUpdate(0, textContent, bookId);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment update(long id, String textContent, long bookId) {
        Comment comment = createOrUpdate(id, textContent, bookId);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    // Хочется узнать вашего мнения!
    // Насколько правильно делать такой метод, вроде как ушел от дублирования кода, но создал метод с двумя действиями.
    private Comment createOrUpdate(long id, String textContent, long bookId) {
        Comment comment;
        if (id != 0) {
            comment = commentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        } else {
            comment = new Comment();
        }
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        comment.setTextContent(textContent);
        comment.setBook(book);
        return comment;
    }
}

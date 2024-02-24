package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(String id);

    List<Comment> findByBookId(String id);

    Comment create(String textContent, String bookId);

    Comment update(String id, String textContent);

    void deleteById(String id);

    void deleteByBookId(String id);
}

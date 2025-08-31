package ru.otus.hw.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import ru.otus.hw.models.Comment;

public interface CommentRepository extends R2dbcRepository<Comment, Long> {
}

package ru.otus.hw.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public Comment toModel(CommentCreateDto comment, Book book) {
        return new Comment(0L, comment.getTextContent(), book);
    }

     public CommentCreateDto toCreateDto(Comment comment) {
        return new CommentCreateDto(comment.textContent(), comment.id());
    }

    public CommentUpdateDto toUpdateDto(Comment comment) {
        return new CommentUpdateDto(comment.id(), comment.textContent(), comment.book().getId());
    }

    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.id(), comment.textContent(), comment.book().getId());
    }

}

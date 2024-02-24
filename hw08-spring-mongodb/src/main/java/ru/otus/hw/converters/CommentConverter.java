package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@RequiredArgsConstructor
@Component
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Id: %s, book_id %s, comment: %s".formatted(
                comment.getId(), comment.getBook().getId(), comment.getTextContent());
    }

}

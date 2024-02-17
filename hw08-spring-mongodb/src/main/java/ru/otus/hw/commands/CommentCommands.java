package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Get comment by id", key = "cbid")
    public String findCommentById(String id) {
        return commentService.findById(id).map(commentConverter::commentToString)
                .orElse("Comment with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Get all comments to book", key = "cbbid")
    public String findCommentsByBookId(String id) {
        return commentService.findByBookId(id).stream().map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteBook(String id) {
        commentService.deleteById(id);
    }
}

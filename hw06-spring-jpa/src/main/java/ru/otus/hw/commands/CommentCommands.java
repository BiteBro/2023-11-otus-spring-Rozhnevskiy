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
    public String findCommentById(long id) {
        return commentService.findById(id).map(commentConverter::commentToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Get all comments to book", key = "cbbid")
    public String findCommentsByBookId(long id) {
        return commentService.findCommentsByBookId(id).stream().map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}

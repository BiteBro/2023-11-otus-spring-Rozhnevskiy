package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @GetMapping("api/book/{bookId}/comment")
    public List<CommentDto> listComments(@PathVariable Long bookId) {
        return commentService.findByBookId(bookId);
    }

    @GetMapping("api/comment/{commentId}")
    public CommentDto commentById(@PathVariable Long commentId) {
        return commentMapper.toDto(commentService.findById(commentId));
    }

    @PostMapping("api/book/{bookId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentCreateDto saveComment(@RequestBody CommentCreateDto commentCreateDto,
                                        @PathVariable Long bookId) {
        commentCreateDto.setBookId(bookId);
        return commentService.create(commentCreateDto);
    }

    @PutMapping("api/book/{bookId}/comment/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentUpdateDto editComment(@RequestBody CommentUpdateDto commentUpdateDto,
                                               @PathVariable Long bookId,
                                               @PathVariable Long commentId) {
        commentUpdateDto.setId(commentId);
        commentUpdateDto.setBookId(bookId);
        return commentService.update(commentUpdateDto);
    }

    @DeleteMapping("api/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
    }
}

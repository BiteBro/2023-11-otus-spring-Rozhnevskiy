package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    private final CommentMapper commentMapper;

    @GetMapping("/comment")
    public String listComments(@RequestParam("bookId") long bookId, Model model) {
        var comments = commentService.findByBookId(bookId);
        model.addAttribute("commentDtoList", comments);
        model.addAttribute("bookId", bookId);
        return "comments_by_book";
    }

    @PostMapping("/comment_delete")
    public String deleteComment(@Valid @ModelAttribute("commentDto") CommentDto commentDto) {
        commentService.deleteById(commentDto.getId());
        return "redirect:/comment?bookId=" + commentDto.getBook().getId();
    }

    @GetMapping("/comment_edit")
    public String editComment(@RequestParam("commentId") long commentId, Model model) {
        var commentUpdateDto = commentMapper.toUpdateDto(commentService.findById(commentId));
        model.addAttribute("commentUpdateDto", commentUpdateDto);
        return "comment_edit";
    }

    @PostMapping("/comment_edit")
    public String editComment(@Valid @ModelAttribute("commentUpdateDto") CommentUpdateDto commentUpdateDto,
                              BindingResult bindingResult, Model model) {
        var bookId = commentService.update(commentUpdateDto).getBookId();
        return "redirect:/comment?bookId=" + bookId;
    }

    @GetMapping("/comment_add")
    public String saveComment(@RequestParam("bookId") long bookId, Model model) {
        model.addAttribute("commentCreateDto", new CommentCreateDto());
        model.addAttribute("bookId", bookId);
        return "comment_add";
    }

    @PostMapping("/comment_add")
    public String saveComment(@Valid @ModelAttribute("commentCreateDto") CommentCreateDto commentCreateDto,
                              BindingResult bindingResult, Model model) {
        commentService.create(commentCreateDto);
        return "redirect:/comment?bookId=" + commentCreateDto.getBookId();
    }


}

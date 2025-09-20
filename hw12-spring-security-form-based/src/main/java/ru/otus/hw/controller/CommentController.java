package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.services.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @GetMapping("/comment")
    public String listComments(@RequestParam("bookId") Long bookId, Model model) {
        model.addAttribute("commentDtoList", commentService.findByBookId(bookId));
        model.addAttribute("bookId", bookId);
        return "comments_by_book";
    }

    @PostMapping("/comment_delete")
    public String deleteComment(@RequestParam("commentId") Long commentId,
                                @Valid @ModelAttribute("bookId") long bookId) {
        commentService.deleteById(commentId);
        return "redirect:/comment?bookId=" + bookId;
    }

    @GetMapping("/comment_edit")
    public String editComment(@RequestParam("commentId") Long commentId, Model model) {
        var commentUpdateDto = commentMapper.toUpdateDto(commentService.findById(commentId));
        model.addAttribute("commentId", commentUpdateDto.getId());
        model.addAttribute("commentUpdateDto", commentUpdateDto);
        return "comment_edit";
    }

    @PostMapping("/comment_edit")
    public String editComment(@Valid @ModelAttribute("commentUpdateDto") CommentUpdateDto commentUpdateDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("commentId", commentUpdateDto.getId());
            model.addAttribute("commentUpdateDto", commentUpdateDto);
            return "comment_edit";
        }
        commentService.update(commentUpdateDto);
        return "redirect:/comment?bookId=" + commentUpdateDto.getBookId();
    }

    @GetMapping("/comment_add")
    public String saveComment(@RequestParam("bookId") Long bookId, Model model) {
        var commentCreateDto = new CommentCreateDto(0L,"", bookId);
        model.addAttribute("commentCreateDto", commentCreateDto);
        return "comment_add";
    }

    @PostMapping("/comment_add")
    public String saveComment(@RequestParam("bookId") Long bookId,
                              @Valid @ModelAttribute("commentCreateDto") CommentCreateDto commentCreateDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("commentCreateDto", commentCreateDto);
            return "comment_add";
        }
        commentService.create(commentCreateDto);
        return "redirect:/comment?bookId=" + bookId;
    }

}

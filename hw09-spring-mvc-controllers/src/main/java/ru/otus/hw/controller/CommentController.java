package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/comment")
    public String listComments(@RequestParam("bookId") long bookId, Model model) {
        var comments = commentService.findByBookId(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        return "comments_by_book";
    }

    @PostMapping("/comment_delete")
    public String deleteComment(@ModelAttribute("comment") CommentDTO commentDTO) {
        commentService.deleteById(commentDTO.getId());
        return "redirect:/comment?bookId=" + commentDTO.getBook().getId();
    }

    @GetMapping("/comment_edit")
    public String editComment(@RequestParam("commentId") long commentId, Model model) {
        var comment = commentService.findById(commentId);
        model.addAttribute("comment", comment);
        return "comment_edit";
    }

    @PostMapping("/comment_edit")
    public String editComment(@ModelAttribute("comment") CommentDTO commentDTO,
                              BindingResult bindingResult, Model model) {
        var bookId = commentService.update(commentDTO).getBook().getId();
        return "redirect:/comment?bookId=" + bookId;
    }

    @GetMapping("/comment_add")
    public String saveComment(@RequestParam("bookId") long bookId, Model model) {
        model.addAttribute("comment", new CommentDTO());
        model.addAttribute("bookId", bookId);
        return "comment_add";
    }

    @PostMapping("/comment_add")
    public String saveComment(@ModelAttribute("comment") CommentDTO commentDTO,
                              BindingResult bindingResult, Model model) {
        commentService.create(commentDTO);
        return "redirect:/comment?bookId=" + commentDTO.getBook().getId();
    }


}

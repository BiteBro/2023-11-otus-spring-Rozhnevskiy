package ru.otus.hw.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.services.CommentService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentRestController.class)
class CommentRestControllerTest {

    @Autowired
    private MockMvc mock;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentMapper commentMapper;

    @Test
    @DisplayName("Должен возвращать список комментариев по id книги")
    void shouldReturnListCommentsByBookId() throws Exception {
        long bookId = 1L;
        var listComments = List.of(
                new CommentDto(1L, "Comment_1"),
                new CommentDto(4L, "Comment_4"));

        given(commentService.findByBookId(bookId)).willReturn(listComments);

        mock.perform(get("/book/" + bookId + "/comment"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\":1,\"textContent\":\"Comment_1\"}," +
                                    "{\"id\":4,\"textContent\":\"Comment_4\"}]"));
    }

    @Test
    @DisplayName("Должен возвращать комментарий по id")
    void shouldReturnCommentById() throws Exception {
        var commentDto = new CommentDto(1L, "Comment_1");
        var comment = commentService.findById(1L);

        given(commentMapper.toDto(comment)).willReturn(commentDto);

        mock.perform(get("/comment/" + commentDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"textContent\":\"Comment_1\"}"));
    }

    @Test
    @DisplayName("Должен сохранять комментарий")
    void shouldSaveComment() throws Exception{
        var commentCreateDto = new CommentCreateDto( "Comment_1", 1L);
        var expected = "{\"id\":1,\"textContent\":\"Comment_1\",\"bookId\":1}";

        mock.perform(post("/book/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected))
                .andExpect(status().isCreated());

        verify(commentService).create(commentCreateDto);
    }

    @Test
    @DisplayName("Должен изменять комментарий по id")
    void shouldEditCommentById() throws Exception{
        var commentUpdateDto = new CommentUpdateDto( 1L, "Comment_1", 1L);
        var expected = "{\"id\":1,\"textContent\":\"Comment_1\",\"bookId\":1}";

        mock.perform(put("/book/1/comment/" + commentUpdateDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected))
                .andExpect(status().isCreated());

        verify(commentService).update(commentUpdateDto);
    }

    @Test
    @DisplayName("Должен удалять комментарий по id")
    void shouldDeleteCommentById() throws Exception{
        mock.perform(delete("/comment/1"))
                .andExpect(status().isOk());
        verify(commentService).deleteById(1L);
    }
}
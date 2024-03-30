package ru.otus.hw.services;

import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentService {

    Comment findById(Long id);

    List<CommentDto> findByBookId(Long id);

    CommentCreateDto create(CommentCreateDto commentCreateDto);

    CommentUpdateDto update(CommentUpdateDto commentUpdateDTO);

    void deleteById(Long id);
}

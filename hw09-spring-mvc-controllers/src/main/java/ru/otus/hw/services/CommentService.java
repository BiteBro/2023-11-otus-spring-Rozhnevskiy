package ru.otus.hw.services;

import ru.otus.hw.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO findById(long id);

    List<CommentDTO> findByBookId(long id);

    CommentDTO create(CommentDTO commentDTO);

    CommentDTO update(CommentDTO commentDTO);

    void deleteById(long id);
}

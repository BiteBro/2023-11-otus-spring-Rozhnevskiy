package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDTO;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public CommentDTO findById(long id) {
        var comment = commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        return new CommentMapper().toDto(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDTO> findByBookId(long id) {
        var commentsDTO = commentRepository.findByBookId(id).orElseThrow(() ->
                new EntityNotFoundException("Comments with id %d not found".formatted(id)));
        return commentsDTO.stream().map(new CommentMapper()::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentDTO create(CommentDTO commentDTO) {
        Book book = bookRepository.findById(commentDTO.getBook().getId()).orElseThrow(() ->
                new EntityNotFoundException("Book with id %d not found".formatted(commentDTO.getBook().getId())));
        Comment comment = new Comment(0, commentDTO.getTextContent(), book);
        return new CommentMapper().toDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentDTO update(CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Comment with id %d not found".formatted(commentDTO.getId())));
        comment.setTextContent(commentDTO.getTextContent());
        return new CommentMapper().toDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

}

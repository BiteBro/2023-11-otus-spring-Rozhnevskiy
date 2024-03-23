package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
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

    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public CommentDto findById(long id) {
        var comment = commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        return new CommentMapper().toDto(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findByBookId(long id) {
        var commentsDTO = commentRepository.findByBookId(id).orElseThrow(() ->
                new EntityNotFoundException("Comments with id %d not found".formatted(id)));
        return commentsDTO.stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentCreateDto create(CommentCreateDto commentCreateDto) {
        Book book = bookRepository.findById(commentCreateDto.getBookId()).orElseThrow(() ->
                new EntityNotFoundException("Book with id %d not found".formatted(commentCreateDto.getBookId())));
        Comment comment = new Comment(0, commentCreateDto.getTextContent(), book);
        return new CommentMapper().toCreateDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentUpdateDto update(CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(commentUpdateDto.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Comment with id %d not found".formatted(commentUpdateDto.getId())));
        comment.setTextContent(commentUpdateDto.getTextContent());
        return commentMapper.toUpdateDto(commentMapper.toDto(commentRepository.save(comment)));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

}

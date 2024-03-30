package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
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
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Comment with id %d not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findByBookId(Long id) {
        var commentsDTO = commentRepository.findByBookId(id).orElseThrow(() ->
                new NotFoundException("Comments with id %d not found".formatted(id)));
        return commentsDTO.stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentCreateDto create(CommentCreateDto commentCreateDto) {
        Book book = bookRepository.findById(commentCreateDto.getBookId()).orElseThrow(() ->
                new NotFoundException("Book with id %d not found".formatted(commentCreateDto.getBookId())));
        Comment comment = commentMapper.toModel(commentCreateDto, book);
        return commentMapper.toCreateDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentUpdateDto update(CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(commentUpdateDto.getId())
                .orElseThrow(() ->
                        new NotFoundException("Comment with id %d not found".formatted(commentUpdateDto.getId())));
        comment.setTextContent(commentUpdateDto.getTextContent());
        return commentMapper.toUpdateDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

}

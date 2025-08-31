package ru.otus.hw.dto;

import jakarta.validation.constraints.NotNull;

public record CommentDto(Long id, @NotNull String textContent, Long bookId, String bookTitle) {

}

package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreateDto (

    @NotBlank(message = "Comments field should not be blank")
    @Size(min = 1, max = 255, message = "Comments field  should be between 1 and 255 characters")
    String textContent,

    @NotNull
    Long bookId
) {}

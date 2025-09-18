package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookUpdateDto
    (
        @NotNull
        Long id,

        @NotBlank(message = "Field title should not be blank")
        @Size(min = 1, max = 15, message = "Field title should be between 1 and 15 characters")
        String title,

        @NotNull
        Long authorId,

        @NotNull
        Long genreId
    ) {}

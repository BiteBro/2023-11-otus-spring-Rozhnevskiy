package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthorDto(

        @NotNull
        Long id,

        @NotBlank(message = "Field full name should not be blank")
        @Size(min = 1, max = 20, message = "Field full name should be between 1 and 20 characters")
        String fullName
) {}

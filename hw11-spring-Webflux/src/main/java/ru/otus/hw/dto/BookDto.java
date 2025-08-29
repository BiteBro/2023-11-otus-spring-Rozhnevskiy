package ru.otus.hw.dto;


import jakarta.validation.constraints.NotNull;

public record BookDto(Long id, @NotNull String title, Long authorId, Long genreId) {

}
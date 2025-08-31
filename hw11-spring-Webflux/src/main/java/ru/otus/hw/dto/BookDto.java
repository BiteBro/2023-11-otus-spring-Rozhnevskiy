package ru.otus.hw.dto;


import jakarta.validation.constraints.NotNull;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;

public record BookDto(Long id, @NotNull String title, Author author, Genre genre) {

}
package ru.otus.hw.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "books")
public record Book(@Id Long id, String title, Author author, Genre genre) {

}

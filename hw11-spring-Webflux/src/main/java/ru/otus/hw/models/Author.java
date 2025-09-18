package ru.otus.hw.models;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;



@Table(name = "authors")
public record Author (@Id Long id, @NotNull String fullName){

}

package ru.otus.hw.models;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "genres")
public record Genre (@Id Long id, @NotNull String name){

}

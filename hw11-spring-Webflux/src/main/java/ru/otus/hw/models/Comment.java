package ru.otus.hw.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public record Comment (

    @Id
    Long id,

    @NotNull
    String textContent,

    @NotNull
    Long bookId
) {
    public Comment(String textContent, Long bookId) {
       this(null, textContent, bookId);
    }
}

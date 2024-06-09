package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateDto {

    @NotNull
    private Long id;

    @NotBlank(message = "Field title should not be blank")
    @Size(min = 1, max = 15, message = "Field title should be between 1 and 15 characters")
    private String title;

    @NotNull
    private Long authorId;

    @NotNull
    private Long genreId;

}

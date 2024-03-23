package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDto {
    @PositiveOrZero
    private long id;

    @NotBlank(message = "Field full name should not be blank")
    @Size(min = 1, max = 20, message = "Field full name should be between 1 and 20 characters")
    private String fullName;

}
